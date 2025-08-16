package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.service.RSSFeedService;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class NewsScheduler {

    private final RSSFeedService rssFeedService;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public NewsScheduler(RSSFeedService rssFeedService) {
        this.rssFeedService = rssFeedService;
    }

    @PostConstruct
    public void scheduleScraping() {
        scheduler.schedule(this::runAndReschedule, 10, TimeUnit.MINUTES);
    }

    private void runAndReschedule() {
        rssFeedService.fetchAndStoreLatestNews();
    }

}