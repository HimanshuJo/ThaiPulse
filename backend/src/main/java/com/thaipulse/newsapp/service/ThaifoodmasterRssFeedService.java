package com.thaipulse.newsapp.service;

import com.rometools.modules.mediarss.MediaEntryModule;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.rometools.rome.feed.module.Module;
import com.thaipulse.newsapp.dto.ThaifoodmasterNewsDto;
import com.thaipulse.newsapp.mapper.ThaifoodmasterNewsMapper;
import com.thaipulse.newsapp.model.ThaifoodmasterNews;
import com.thaipulse.newsapp.repository.ThaifoodmasterNewsRepository;

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
public class ThaifoodmasterRssFeedService {

    private static final Logger logger = LoggerFactory.getLogger(ThaifoodmasterRssFeedService.class);

    private final ThaifoodmasterNewsRepository thaifoodmasterNewsRepository;

    public ThaifoodmasterRssFeedService(ThaifoodmasterNewsRepository thaifoodmasterNewsRepository) {
        this.thaifoodmasterNewsRepository = thaifoodmasterNewsRepository;
    }

    public boolean newsCheck() {
        return thaifoodmasterNewsRepository.count() >= 1;
    }

    private String extractImageFromHtml(String html) {
        if (html == null) return null;
        Matcher matcher = Pattern.compile("<img[^>]+src=[\"']([^\"']+)[\"']").matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public List<ThaifoodmasterNews> getNewsFromRss(String rssUrl) {
        List<ThaifoodmasterNews> newsList = new ArrayList<>();
        try {
            URL url = new URL(rssUrl);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));
            for (SyndEntry entry : feed.getEntries()) {
                ThaifoodmasterNews news = new ThaifoodmasterNews();
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
                logger.info("Added ThaifoodmasterNews: " + news.getTitle());
                if (newsList.size() >= 10) break;
            }
        } catch (IOException | FeedException e) {
            throw new RuntimeException(e);
        }
        return newsList;
    }

    public void fetchAndStoreLatestNews() {
        List<ThaifoodmasterNews> fetchedNews = new ArrayList<>();
        fetchedNews.addAll(getNewsFromRss("https://feeds.feedburner.com/Thaifoodmaster"));
        Collections.shuffle(fetchedNews);
        List<ThaifoodmasterNews> uniqueNews = fetchedNews;
        if (newsCheck()) {
            uniqueNews = fetchedNews.stream()
                    .filter(news -> !thaifoodmasterNewsRepository.existsByLink(news.getLink()))
                    .toList();
        }
        long count = thaifoodmasterNewsRepository.count();
        if (count < 10000) {
            if (!uniqueNews.isEmpty()) {
                for (ThaifoodmasterNews news : uniqueNews) {
                    try {
                        thaifoodmasterNewsRepository.save(news);
                    } catch (DataIntegrityViolationException dive) {
                        logger.info("Duplicate news skipped: {} " + news.getLink());
                    }
                }
            }
        } else {
            thaifoodmasterNewsRepository.deleteAllInBatch();
            for (ThaifoodmasterNews news : uniqueNews) {
                try {
                    thaifoodmasterNewsRepository.save(news);
                } catch (DataIntegrityViolationException dive) {
                    logger.info("Duplicate news skipped: {} " + news.getLink());
                }
            }
        }
    }

    public long countAllNews() {
        return thaifoodmasterNewsRepository.count();
    }

    public Page<ThaifoodmasterNewsDto> getPaginatedNews(int page, int size) {
        if (size < 1) size = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<ThaifoodmasterNews> newsPage = thaifoodmasterNewsRepository.findAll(pageable);
        List<ThaifoodmasterNewsDto> newsDtos = newsPage.getContent().stream()
                .map(ThaifoodmasterNewsMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(newsDtos, pageable, newsPage.getTotalElements());
    }

}