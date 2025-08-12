package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.model.ThailandIslandNews;
import com.thaipulse.newsapp.repository.ThailandIslandNewsRepository;
import com.thaipulse.newsapp.service.ThailandIslandNewsRssFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class ThailandIslandNewsScheduler {

    private static final Logger logger = LoggerFactory.getLogger(ThailandIslandNewsScheduler.class);

    private final ThailandIslandNewsRssFeedService thailandIslandNewsRssFeedService;
    private final ThailandIslandNewsRepository thailandIslandNewsRepository;

    @Scheduled(fixedRate = 700000)
    public void refereshNews() {
        logger.info("running scheduler in ThailandIslandNewsScheduler");
        List<ThailandIslandNews> fetchedNews = new ArrayList<>(thailandIslandNewsRssFeedService.getNewsFromRss("https" +
                "://www.thailand-island.info/feed/"));
        List<ThailandIslandNews> uniqueNews = fetchedNews.stream()
                .filter(news -> !thailandIslandNewsRepository.existsByLink(news.getLink()))
                .toList();
        long count = thailandIslandNewsRepository.count();
        if (count < 2000) {
            if (!uniqueNews.isEmpty()) {
                thailandIslandNewsRepository.saveAll(uniqueNews);
            }
        } else {
            thailandIslandNewsRepository.deleteAll();
            thailandIslandNewsRepository.saveAll(uniqueNews);
        }
    }
}
