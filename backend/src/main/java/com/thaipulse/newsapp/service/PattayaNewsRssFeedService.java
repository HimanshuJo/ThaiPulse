package com.thaipulse.newsapp.service;

import com.rometools.modules.mediarss.MediaEntryModule;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.thaipulse.newsapp.dto.PattayaNewsDto;
import com.thaipulse.newsapp.mapper.PattayaNewsMapper;
import com.thaipulse.newsapp.model.PattayaNews;
import com.thaipulse.newsapp.repository.PattayaNewsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PattayaNewsRssFeedService {

    private static final Logger logger = LoggerFactory.getLogger(PattayaNewsRssFeedService.class);

    private final PattayaNewsRepository pattayaNewsRepository;

    public PattayaNewsRssFeedService(PattayaNewsRepository pattayaNewsRepository) {
        this.pattayaNewsRepository = pattayaNewsRepository;
    }

    public boolean newsCheck() {
        return pattayaNewsRepository.count() >= 10;
    }

    private String extractImageFromHtml(String html) {
        if (html == null) return null;
        Matcher matcher = Pattern.compile("<img[^>]+src=[\"']([^\"']+)[\"']").matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public List<PattayaNews> getNewsFromRss(String rssUrl) {
        List<PattayaNews> newsList = new ArrayList<>();
        try {
            URL url = new URL(rssUrl);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));
            for (SyndEntry entry : feed.getEntries()) {
                PattayaNews news = new PattayaNews();
                news.setTitle(entry.getTitle());
                news.setSource(feed.getTitle());
                news.setLink(entry.getLink());

                boolean imageSet = false;
                if (entry.getModules() != null) {
                    for (Module module : entry.getModules()) {
                        if (module instanceof MediaEntryModule mediaModule) {
                            if (mediaModule.getMediaContents() != null && mediaModule.getMediaContents().length > 0) {
                                news.setImage(mediaModule.getMediaContents()[0].getReference().toString());
                                imageSet = true;
                                break;
                            }
                        }
                    }
                }

                if (!imageSet && entry.getDescription() != null) {
                    String desc = entry.getDescription().getValue();
                    String imageUrl = extractImageFromHtml(desc);
                    if (imageUrl != null) {
                        news.setImage(imageUrl);
                        imageSet = true;
                    }
                }

                if (!imageSet && entry.getContents() != null) {
                    for (SyndContent content : entry.getContents()) {
                        String html = content.getValue();
                        String imageUrl = extractImageFromHtml(html);
                        if (imageUrl != null) {
                            news.setImage(imageUrl);
                            imageSet = true;
                            break;
                        }
                    }
                }
                if (!imageSet) continue;
                newsList.add(news);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsList;
    }

    @Transactional
    public void fetchAndStoreLatestNews() {
        List<PattayaNews> fetchedNews = new ArrayList<>(getNewsFromRss("https://rss.app/feeds/oHqaJXd9t6VndNaj.xml"));
        Collections.shuffle(fetchedNews);
        List<PattayaNews> uniqueNews = fetchedNews;
        if (newsCheck()) {
            uniqueNews = fetchedNews.stream()
                    .filter(news -> !pattayaNewsRepository.existsByLink(news.getLink()))
                    .toList();
        }
        long count = pattayaNewsRepository.count();
        if (count < 10000) {
            if (!uniqueNews.isEmpty()) {
                for (PattayaNews news : uniqueNews) {
                    try {
                        pattayaNewsRepository.save(news);
                    } catch (DataIntegrityViolationException dive) {
                        logger.info("Duplicate news skipped: {} " + news.getLink());
                    }
                }
            }
        } else {
            pattayaNewsRepository.deleteAllInBatch();
            for (PattayaNews news : uniqueNews) {
                try {
                    pattayaNewsRepository.save(news);
                } catch (DataIntegrityViolationException dive) {
                    logger.info("Duplicate news skipped: {} " + news.getLink());
                }
            }
        }
    }

    public long countAllPattyaNews() {
        return pattayaNewsRepository.count();
    }

    public Page<PattayaNewsDto> getPaginatedPattayaNews(int page, int size) {
        if (size < 1) {
            size = 20;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<PattayaNews> newsPage = pattayaNewsRepository.findAll(pageable);
        List<PattayaNewsDto> newsDtos = newsPage.getContent().stream()
                .map(PattayaNewsMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(newsDtos, pageable, newsPage.getTotalElements());
    }

}