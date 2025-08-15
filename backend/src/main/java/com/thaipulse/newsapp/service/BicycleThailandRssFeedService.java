package com.thaipulse.newsapp.service;

import com.rometools.modules.mediarss.MediaEntryModule;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.rometools.rome.feed.module.Module;
import com.thaipulse.newsapp.dto.BicycleThailandNewsDto;
import com.thaipulse.newsapp.mapper.BicycleThailandNewsMapper;
import com.thaipulse.newsapp.model.BicycleThailandNews;
import com.thaipulse.newsapp.repository.BicycleThailandNewsRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BicycleThailandRssFeedService {

    private static final Logger logger = LoggerFactory.getLogger(BicycleThailandRssFeedService.class);

    private final BicycleThailandNewsRepository bicycleThailandNewsRepository;

    public BicycleThailandRssFeedService(BicycleThailandNewsRepository bicycleThailandNewsRepository) {
        this.bicycleThailandNewsRepository = bicycleThailandNewsRepository;
    }

    public boolean newsCheck() {
        return bicycleThailandNewsRepository.count() >= 1;
    }

    private String extractImageFromHtml(String html) {
        if (html == null) return null;
        Matcher matcher = Pattern.compile("<img[^>]+src=[\"']([^\"']+)[\"']").matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public List<BicycleThailandNews> getNewsFromRss(String rssUrl) {
        List<BicycleThailandNews> newsList = new ArrayList<>();
        try {
            URL url = new URL(rssUrl);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));
            for (SyndEntry entry : feed.getEntries()) {
                BicycleThailandNews news = new BicycleThailandNews();
                news.setTitle(entry.getTitle());
                try {
                    URL articleUrl = new URL(entry.getLink());
                    String host = articleUrl.getHost();
                    if (host.startsWith("www.")) {
                        host = host.substring(4);
                    }
                    String mainPart = host.split("\\.")[0];
                    String formattedSource = Arrays.stream(mainPart.split("(?=[A-Z])|(?<=\\D)(?=\\d)|(?<=\\D)(?=News)" +
                                    "|(?<=news)(?=[A-Z])|(?<=\\p{Lower})(?=\\p{Upper})|(?<=\\p{Lower})(?=\\d)|" +
                                    "(?<=\\p{Lower})(?=\\p{Upper})"))
                            .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                            .collect(Collectors.joining(" "));

                    if (formattedSource.equals(mainPart)) {
                        formattedSource = Arrays.stream(mainPart.split("(?<=news)|(?<=daily)|(?<=post)|(?<=times)|" +
                                        "(?<=review)|(?<=mail)"))
                                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                                .collect(Collectors.joining(" "));
                    }

                    news.setSource(formattedSource.trim());
                } catch (MalformedURLException e) {
                    news.setSource("Unknown");
                }
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
                logger.info("Added BicycleThailandNews: " + news.getTitle());
                if (newsList.size() >= 10) break;
            }
        } catch (IOException | FeedException e) {
            throw new RuntimeException(e);
        }
        return newsList;
    }

    public void fetchAndStoreLatestNews() {
        List<BicycleThailandNews> fetchedNews = new ArrayList<>();
        fetchedNews.addAll(getNewsFromRss("https://bicyclethailand.com/feed/"));
        Collections.shuffle(fetchedNews);
        List<BicycleThailandNews> uniqueNews = fetchedNews;
        if (newsCheck()) {
            uniqueNews = fetchedNews.stream()
                    .filter(news -> !bicycleThailandNewsRepository.existsByLink(news.getLink()))
                    .toList();
        }
        long count = bicycleThailandNewsRepository.count();
        if (count < 10000) {
            if (!uniqueNews.isEmpty()) {
                for (BicycleThailandNews news : uniqueNews) {
                    try {
                        bicycleThailandNewsRepository.save(news);
                    } catch (DataIntegrityViolationException dive) {
                        logger.info("Duplicate news skipped: {} " + news.getLink());
                    }
                }
            }
        } else {
            bicycleThailandNewsRepository.deleteAllInBatch();
            for (BicycleThailandNews news : uniqueNews) {
                try {
                    bicycleThailandNewsRepository.save(news);
                } catch (DataIntegrityViolationException dive) {
                    logger.info("Duplicate news skipped: {} " + news.getLink());
                }
            }
        }
    }

    public long countAllNews() {
        return bicycleThailandNewsRepository.count();
    }

    public Page<BicycleThailandNewsDto> getPaginatedNews(int page, int size) {
        if (size < 1) size = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<BicycleThailandNews> newsPage = bicycleThailandNewsRepository.findAll(pageable);
        List<BicycleThailandNewsDto> newsDtos = newsPage.getContent().stream()
                .map(BicycleThailandNewsMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(newsDtos, pageable, newsPage.getTotalElements());
    }

}