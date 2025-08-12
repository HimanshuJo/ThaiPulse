package com.thaipulse.newsapp.service;

import com.rometools.modules.mediarss.MediaEntryModule;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.rometools.rome.feed.module.Module;
import com.thaipulse.newsapp.dto.ThaiLadyDateFinderNewsDto;
import com.thaipulse.newsapp.mapper.ThaiLadyDateFinderNewsMapper;
import com.thaipulse.newsapp.model.ThaiLadyDateFinderNews;
import com.thaipulse.newsapp.repository.ThaiLadyDateFinderNewsRepository;
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
public class ThaiLadyDateFinderRssFeedService {

    private static final Logger logger = LoggerFactory.getLogger(ThaiLadyDateFinderRssFeedService.class);

    private final ThaiLadyDateFinderNewsRepository thaiLadyDateFinderNewsRepository;

    public ThaiLadyDateFinderRssFeedService(ThaiLadyDateFinderNewsRepository thaiLadyDateFinderNewsRepository) {
        this.thaiLadyDateFinderNewsRepository = thaiLadyDateFinderNewsRepository;
    }

    private String extractImageFromHtml(String html) {
        if (html == null) return null;
        Matcher matcher = Pattern.compile("<img[^>]+src=[\"']([^\"']+)[\"']").matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public List<ThaiLadyDateFinderNews> getNewsFromRss(String rssUrl) {
        List<ThaiLadyDateFinderNews> newsList = new ArrayList<>();
        try {
            URL url = new URL(rssUrl);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));
            for (SyndEntry entry : feed.getEntries()) {
                ThaiLadyDateFinderNews news = new ThaiLadyDateFinderNews();
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
                logger.info("Added ThaiLadyDateFinderNews: " + news.getTitle());
            }
        } catch (IOException | FeedException e) {
            throw new RuntimeException(e);
        }
        return newsList;
    }

    public Page<ThaiLadyDateFinderNewsDto> getPaginatedNews(int page, int size) {
        if (size < 1) size = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<ThaiLadyDateFinderNews> newsPage = thaiLadyDateFinderNewsRepository.findAll(pageable);
        List<ThaiLadyDateFinderNewsDto> newsDtos = newsPage.getContent().stream()
                .map(ThaiLadyDateFinderNewsMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(newsDtos, pageable, newsPage.getTotalElements());
    }

    public long countAllNews() {
        return thaiLadyDateFinderNewsRepository.count();
    }

}
