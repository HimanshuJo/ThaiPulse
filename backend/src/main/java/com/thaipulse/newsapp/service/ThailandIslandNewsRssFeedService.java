package com.thaipulse.newsapp.service;

import com.rometools.modules.mediarss.MediaEntryModule;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.rometools.rome.feed.module.Module;
import com.thaipulse.newsapp.dto.ThailandIslandNewsDto;
import com.thaipulse.newsapp.mapper.ThailandIslandNewsMapper;
import com.thaipulse.newsapp.model.ThailandIslandNews;
import com.thaipulse.newsapp.repository.ThailandIslandNewsRepository;
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
public class ThailandIslandNewsRssFeedService {

    private static final Logger logger = LoggerFactory.getLogger(ThailandIslandNewsRssFeedService.class);

    private final ThailandIslandNewsRepository thailandIslandNewsRepository;

    public ThailandIslandNewsRssFeedService(ThailandIslandNewsRepository thailandIslandNewsRepository) {
        this.thailandIslandNewsRepository = thailandIslandNewsRepository;
    }

    private String extractImageFromHtml(String html) {
        if (html == null) return null;
        Matcher matcher = Pattern.compile("<img[^>]+src=[\"']([^\"']+)[\"']").matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public List<ThailandIslandNews> getNewsFromRss(String rssUrl) {
        List<ThailandIslandNews> newsList = new ArrayList<>();
        try {
            URL url = new URL(rssUrl);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));
            for (SyndEntry entry : feed.getEntries()) {
                ThailandIslandNews news = new ThailandIslandNews();
                news.setTitle(entry.getTitle());
                news.setSource(entry.getTitle());
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
                logger.info("Added ThailandIslandNews: " + news.getTitle());
            }
        } catch (IOException | FeedException e) {
            throw new RuntimeException(e);
        }
        return newsList;
    }

    public Page<ThailandIslandNewsDto> getPaginatedNews(int page, int size) {
        if (size < 1) size = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<ThailandIslandNews> newsPage = thailandIslandNewsRepository.findAll(pageable);
        List<ThailandIslandNewsDto> newsDtos = newsPage.getContent().stream()
                .map(ThailandIslandNewsMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(newsDtos, pageable, newsPage.getTotalElements());
    }

    public long countAllNews() {
        return thailandIslandNewsRepository.count();
    }

}
