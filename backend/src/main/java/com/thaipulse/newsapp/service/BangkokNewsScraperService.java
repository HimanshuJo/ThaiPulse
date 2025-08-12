package com.thaipulse.newsapp.service;

import com.rometools.modules.mediarss.MediaEntryModule;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.thaipulse.newsapp.dto.BangkokNewsDto;
import com.thaipulse.newsapp.mapper.BangkokNewsMapper;
import com.thaipulse.newsapp.model.BangkokNews;
import com.thaipulse.newsapp.repository.BangkokNewsRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BangkokNewsScraperService {

    private static final Logger logger = LoggerFactory.getLogger(BangkokNewsScraperService.class);

    private final BangkokNewsRepository bangkokNewsRepository;

    public BangkokNewsScraperService(BangkokNewsRepository bangkokNewsRepository) {
        this.bangkokNewsRepository = bangkokNewsRepository;
    }

    public long countAllBangkokNews() {
        return bangkokNewsRepository.count();
    }

    public boolean newsCheck() {
        return bangkokNewsRepository.count() >= 1;
    }

    public void fetchAndStoreLatestNews() {
        List<BangkokNews> fetchedNews = new ArrayList<>();
        fetchedNews.addAll(getNewsFromRss("https://www.indothainews.com/feed/"));
        Collections.shuffle(fetchedNews);
        List<BangkokNews> uniqueNews = fetchedNews;
        if (newsCheck()) {
            uniqueNews = fetchedNews.stream()
                    .filter(news -> !bangkokNewsRepository.existsByLink(news.getLink()))
                    .toList();
        }
        long count = bangkokNewsRepository.count();
        if (count < 2000) {
            if (!uniqueNews.isEmpty()) {
                for (BangkokNews news : uniqueNews) {
                    try {
                        bangkokNewsRepository.save(news);
                    } catch (DataIntegrityViolationException dive) {
                        logger.info("Duplicate news skipped: {} " + news.getLink());
                    }
                }
            }
        } else {
            bangkokNewsRepository.deleteAllInBatch();
            for (BangkokNews news : uniqueNews) {
                try {
                    bangkokNewsRepository.save(news);
                } catch (DataIntegrityViolationException dive) {
                    logger.info("Duplicate news skipped: {} " + news.getLink());
                }
            }
        }
    }

    public Page<BangkokNewsDto> getPaginatedBangkokNews(int page, int size) {
        if (size < 1) {
            size = 20;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<BangkokNews> newsPage = bangkokNewsRepository.findAll(pageable);
        List<BangkokNewsDto> newsDto = newsPage.getContent().stream()
                .map(BangkokNewsMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(newsDto, pageable, newsPage.getTotalElements());
    }

    private String extractImageFromHtml(String html) {
        if (html == null) return null;
        Matcher matcher = Pattern.compile("<img[^>]+src=[\"']([^\"']+)[\"']").matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public List<BangkokNews> getNewsFromRss(String rssUrl) {
        List<BangkokNews> newsList = new ArrayList<>();
        try {
            URL url = new URL(rssUrl);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));
            for (SyndEntry entry : feed.getEntries()) {
                BangkokNews news = new BangkokNews();
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
                if (!imageSet) {
                    continue;
                }
                logger.info("Bangkok News Added: " + news.getTitle());
                newsList.add(news);
                if (newsList.size() >= 10) break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsList;
    }

}
