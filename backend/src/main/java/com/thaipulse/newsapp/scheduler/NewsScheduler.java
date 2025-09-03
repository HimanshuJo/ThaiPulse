package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.repository.NewsRepository;
import com.thaipulse.newsapp.service.RSSFeedService;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class NewsScheduler {

    private final RSSFeedService rssFeedService;

    private final NewsRepository newsRepository;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public NewsScheduler(RSSFeedService rssFeedService, NewsRepository newsRepository) {
        this.rssFeedService = rssFeedService;
        this.newsRepository = newsRepository;
    }

    private long getCurrentNewsCount() {
        return newsRepository.count();
    }

    @PostConstruct
    public void scheduleScraping() {
        long countNews = getCurrentNewsCount();
        if (countNews >= 1) {
            scheduler.schedule(this::runAndReschedule, 12, TimeUnit.HOURS);
        } else {
            scheduler.schedule(this::runAndReschedule, 10, TimeUnit.SECONDS);
        }
    }

    private void runAndReschedule() {
        rssFeedService.fetchAndStoreLatestNews();
    }

}