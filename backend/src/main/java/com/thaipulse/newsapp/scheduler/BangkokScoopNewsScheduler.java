package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.service.BangkokScoopRssFeedService;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class BangkokScoopNewsScheduler {

    private final BangkokScoopRssFeedService bangkokScoopRssFeedService;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public BangkokScoopNewsScheduler(BangkokScoopRssFeedService bangkokScoopRssFeedService) {
        this.bangkokScoopRssFeedService = bangkokScoopRssFeedService;
    }

    @PostConstruct
    public void scheduleScraping() {
        scheduler.schedule(this::runAndReschedule, 10, TimeUnit.MINUTES);
    }

    private void runAndReschedule() {
        bangkokScoopRssFeedService.fetchAndStoreLatestNews();
    }

}