package com.thaipulse.newsapp.service;

import com.rometools.modules.mediarss.MediaEntryModule;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.rometools.rome.feed.module.Module;
import com.thaipulse.newsapp.dto.TrailheadThailandNewsDto;
import com.thaipulse.newsapp.mapper.TrailheadThailandNewsMapper;
import com.thaipulse.newsapp.model.TrailheadThailandNews;
import com.thaipulse.newsapp.repository.TrailheadThailandNewsRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TrailheadThailandRssFeedService {

    private static final Logger logger = LoggerFactory.getLogger(TrailheadThailandRssFeedService.class);

    private final TrailheadThailandNewsRepository trailheadThailandNewsRepository;

    public TrailheadThailandRssFeedService(TrailheadThailandNewsRepository trailheadThailandNewsRepository) {
        this.trailheadThailandNewsRepository = trailheadThailandNewsRepository;
    }

    private String extractImageFromHtml(String html) {
        if (html == null) return null;
        Matcher matcher = Pattern.compile("<img[^>]+src=[\"']([^\"']+)[\"']").matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public List<TrailheadThailandNews> getNewsFromRss(String rssUrl) {
        List<TrailheadThailandNews> newsList = new ArrayList<>();
        try {
            URL url = new URL(rssUrl);
            SyndFeedInput input = new SyndFeedInput();

            SyndFeed feed;
            try {
                feed = input.build(new XmlReader(url));
            } catch (FeedException fe) {
                logger.error("Feed parse error for URL {} — skipping feed entirely: {}", rssUrl, fe.getMessage());
                return newsList;
            }

            for (SyndEntry entry : feed.getEntries()) {
                try {
                    TrailheadThailandNews news = new TrailheadThailandNews();
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
                        logger.warn("Skipping article without image: {}", entry.getTitle());
                        continue;
                    }

                    newsList.add(news);
                    logger.info("Added TrailheadThailandNews: {}", news.getTitle());

                } catch (Exception entryEx) {
                    logger.error("Skipping malformed news entry due to parse error: {}", entryEx.getMessage());
                }
            }

        } catch (IOException e) {
            logger.error("I/O error fetching RSS from {}: {}", rssUrl, e.getMessage());
        }
        return newsList;
    }

    public Page<TrailheadThailandNewsDto> getPaginatedNews(int page, int size) {
        if (size < 1) size = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<TrailheadThailandNews> newsPage = trailheadThailandNewsRepository.findAll(pageable);
        List<TrailheadThailandNewsDto> newsDtos = newsPage.getContent().stream()
                .map(TrailheadThailandNewsMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(newsDtos, pageable, newsPage.getTotalElements());
    }

    public long countAllNews() {
        return trailheadThailandNewsRepository.count();
    }

}
