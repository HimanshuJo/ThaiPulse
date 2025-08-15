package com.thaipulse.newsapp.service;

import com.rometools.modules.mediarss.MediaEntryModule;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.thaipulse.newsapp.dto.LegallyMarriedInThailandNewsDto;
import com.thaipulse.newsapp.mapper.LegallyMarriedInThailandNewsMapper;
import com.thaipulse.newsapp.model.LegallyMarriedInThailandNews;
import com.thaipulse.newsapp.repository.LegallyMarriedInThailandNewsRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class LegallyMarriedInThailandRssFeedService {

    private static final Logger logger = LoggerFactory.getLogger(LegallyMarriedInThailandRssFeedService.class);

    private final LegallyMarriedInThailandNewsRepository legallyMarriedInThailandNewsRepository;

    public LegallyMarriedInThailandRssFeedService(LegallyMarriedInThailandNewsRepository legallyMarriedInThailandNewsRepository) {
        this.legallyMarriedInThailandNewsRepository = legallyMarriedInThailandNewsRepository;
    }

    public boolean newsCheck() {
        return legallyMarriedInThailandNewsRepository.count() >= 1;
    }

    private String extractImageFromHtml(String html) {
        if (html == null) return null;
        Matcher matcher = Pattern.compile("<img[^>]+src=[\"']([^\"']+)[\"']").matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public List<LegallyMarriedInThailandNews> getNewsFromRss(String rssUrl) {
        List<LegallyMarriedInThailandNews> newsList = new ArrayList<>();
        try {
            URL url = new URL(rssUrl);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));
            for (SyndEntry entry : feed.getEntries()) {
                LegallyMarriedInThailandNews news = new LegallyMarriedInThailandNews();
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
                logger.info("Added LegallyMarriedInThailandNews: " + news.getTitle());
            }
        } catch (IOException | FeedException e) {
            throw new RuntimeException(e);
        }
        return newsList;
    }

    public void fetchAndStoreLatestNews() {
        List<LegallyMarriedInThailandNews> fetchedNews = new ArrayList<>(getNewsFromRss("https://www" +
                ".legallymarriedinthailand.com/feed/"));
        Collections.shuffle(fetchedNews);
        List<LegallyMarriedInThailandNews> uniqueNews = fetchedNews;
        if (newsCheck()) {
            fetchedNews.stream()
                    .filter(news -> !legallyMarriedInThailandNewsRepository.existsByLink(news.getLink()))
                    .toList();
        }

        long count = legallyMarriedInThailandNewsRepository.count();
        if (count < 10000) {
            if (!uniqueNews.isEmpty()) {
                for (LegallyMarriedInThailandNews news : uniqueNews) {
                    try {
                        legallyMarriedInThailandNewsRepository.saveAll(uniqueNews);
                    } catch (DataIntegrityViolationException dive) {
                        logger.info("Duplicate news skipped: {} " + news.getLink());
                    }
                }
            }
        } else {
            legallyMarriedInThailandNewsRepository.deleteAllInBatch();
            for (LegallyMarriedInThailandNews news : uniqueNews) {
                try {
                    legallyMarriedInThailandNewsRepository.saveAll(uniqueNews);
                } catch (DataIntegrityViolationException dive) {
                    logger.info("Duplicate news skipped: {} " + news.getLink());
                }
            }
        }
    }

    public long countAllNews() {
        return legallyMarriedInThailandNewsRepository.count();
    }

    public Page<LegallyMarriedInThailandNewsDto> getPaginatedNews(int page, int size) {
        if (size < 1) size = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<LegallyMarriedInThailandNews> newsPage = legallyMarriedInThailandNewsRepository.findAll(pageable);
        List<LegallyMarriedInThailandNewsDto> newsDtos = newsPage.getContent().stream()
                .map(LegallyMarriedInThailandNewsMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(newsDtos, pageable, newsPage.getTotalElements());
    }

}