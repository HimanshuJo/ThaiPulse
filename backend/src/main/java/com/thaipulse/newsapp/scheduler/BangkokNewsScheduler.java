package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.service.BangkokNewsRssFeedService;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class BangkokNewsScheduler {

    private final BangkokNewsRssFeedService bangkokNewsRssFeedService;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public BangkokNewsScheduler(BangkokNewsRssFeedService bangkokNewsRssFeedService) {
        this.bangkokNewsRssFeedService = bangkokNewsRssFeedService;
    }

    @PostConstruct
    public void scheduleScraping() {
        scheduler.schedule(this::runAndReschedule, 10, TimeUnit.MINUTES);
    }

    private void runAndReschedule() {
        bangkokNewsRssFeedService.fetchAndStoreLatestNews();
    }

}