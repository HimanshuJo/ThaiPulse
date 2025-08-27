package com.thaipulse.newsapp.service;

import com.rometools.modules.mediarss.MediaEntryModule;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.thaipulse.newsapp.dto.PhuketNewsDto;
import com.thaipulse.newsapp.mapper.PhuketNewsMapper;
import com.thaipulse.newsapp.model.PhuketNews;
import com.thaipulse.newsapp.repository.PhuketNewsRepository;
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
public class PhuketNewsRssFeedService {

    private static final Logger logger = LoggerFactory.getLogger(PhuketNewsRssFeedService.class);

    private final PhuketNewsRepository phuketNewsRepository;

    public PhuketNewsRssFeedService(PhuketNewsRepository phuketNewsRepository) {
        this.phuketNewsRepository = phuketNewsRepository;
    }

    public boolean newsCheck() {
        return phuketNewsRepository.count() >= 10;
    }

    private String extractImageFromHtml(String html) {
        if (html == null) return null;
        Matcher matcher = Pattern.compile("<img[^>]+src=[\"']([^\"']+)[\"']").matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public List<PhuketNews> getNewsFromRss(String rssUrl) {
        List<PhuketNews> newsList = new ArrayList<>();
        try {
            URL url = new URL(rssUrl);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));
            for (SyndEntry entry : feed.getEntries()) {
                PhuketNews news = new PhuketNews();
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
        List<PhuketNews> fetchedNews = new ArrayList<>(getNewsFromRss("https://rss.app/feeds/3ir0uk2neXy0aAZp.xml"));
        Collections.shuffle(fetchedNews);
        List<PhuketNews> uniqueNews = fetchedNews;
        if (newsCheck()) {
            uniqueNews = fetchedNews.stream()
                    .filter(news -> !phuketNewsRepository.existsByLink(news.getLink()))
                    .toList();
        }
        long count = phuketNewsRepository.count();
        if (count < 10000) {
            if (!uniqueNews.isEmpty()) {
                for (PhuketNews news : uniqueNews) {
                    try {
                        phuketNewsRepository.save(news);
                    } catch (DataIntegrityViolationException dive) {
                        logger.info("Duplicate news skipped: {} " + news.getLink());
                    }
                }
            }
        } else {
            phuketNewsRepository.deleteAllInBatch();
            for (PhuketNews news : uniqueNews) {
                try {
                    phuketNewsRepository.save(news);
                } catch (DataIntegrityViolationException dive) {
                    logger.info("Duplicate news skipped: {} " + news.getLink());
                }
            }
        }
    }

    public long countAllPhuketNews() {
        return phuketNewsRepository.count();
    }

    public Page<PhuketNewsDto> getPaginatedPhuketNews(int page, int size) {
        if (size < 1) {
            size = 20;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<PhuketNews> newsPage = phuketNewsRepository.findAll(pageable);
        List<PhuketNewsDto> newsDto = newsPage.getContent().stream()
                .map(PhuketNewsMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(newsDto, pageable, newsPage.getTotalElements());
    }

}