package com.thaipulse.newsapp.service;

import com.rometools.modules.mediarss.MediaEntryModule;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.thaipulse.newsapp.dto.HatYaiNewsDto;
import com.thaipulse.newsapp.mapper.HatYaiNewsMapper;
import com.thaipulse.newsapp.model.HatYaiNews;
import com.thaipulse.newsapp.repository.HatYaiNewsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Service
public class HatYaiNewsRssFeedService {

    private static final Logger logger = LoggerFactory.getLogger(HatYaiNewsRssFeedService.class);

    private final HatYaiNewsRepository hatYaiNewsRepository;

    public HatYaiNewsRssFeedService(HatYaiNewsRepository hatYaiNewsRepository) {
        this.hatYaiNewsRepository = hatYaiNewsRepository;
    }

    public boolean newsCheck() {
        return hatYaiNewsRepository.count() >= 1;
    }

    private String extractImageFromHtml(String html) {
        if (html == null) return null;
        Matcher matcher = Pattern.compile("<img[^>]+src=[\"']([^\"']+)[\"']").matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public List<HatYaiNews> getNewsFromRss(String rssUrl) {
        List<HatYaiNews> newsList = new ArrayList<>();
        try {
            URL url = new URL(rssUrl);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));
            for (SyndEntry entry : feed.getEntries()) {
                HatYaiNews news = new HatYaiNews();
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

    public void fetchAndStoreLatestNews() {
        List<HatYaiNews> fetchedNews = new ArrayList<>(getNewsFromRss("https://rss.app/feeds/Xqqc5mrSQcXGb139.xml"));
        Collections.shuffle(fetchedNews);
        List<HatYaiNews> uniqueNews = fetchedNews;
        if (newsCheck()) {
            uniqueNews = fetchedNews.stream()
                    .filter(news -> !hatYaiNewsRepository.existsByLink(news.getLink()))
                    .toList();
        }
        long count = hatYaiNewsRepository.count();
        if (count < 10000) {
            if (!uniqueNews.isEmpty()) {
                for (HatYaiNews news : uniqueNews) {
                    try {
                        hatYaiNewsRepository.save(news);
                    } catch (DataIntegrityViolationException dive) {
                        logger.info("Duplicate news skipped: {} " + news.getLink());
                    }
                }
            }
        } else {
            hatYaiNewsRepository.deleteAllInBatch();
            for (HatYaiNews news : uniqueNews) {
                try {
                    hatYaiNewsRepository.save(news);
                } catch (DataIntegrityViolationException dive) {
                    logger.info("Duplicate news skipped: {} " + news.getLink());
                }
            }
        }
    }

    public long countAllHatYaiNews() {
        return hatYaiNewsRepository.count();
    }

    public Page<HatYaiNewsDto> getPaginatedHatYaiNews(int page, int size) {
        if (size < 1) size = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<HatYaiNews> newsPage = hatYaiNewsRepository.findAll(pageable);
        List<HatYaiNewsDto> newsDtos = newsPage.getContent().stream()
                .map(HatYaiNewsMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(newsDtos, pageable, newsPage.getTotalElements());
    }

}