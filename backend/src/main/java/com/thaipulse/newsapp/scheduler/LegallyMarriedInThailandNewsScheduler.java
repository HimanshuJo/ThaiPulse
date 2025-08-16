package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.service.LegallyMarriedInThailandRssFeedService;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class LegallyMarriedInThailandNewsScheduler {

    private final LegallyMarriedInThailandRssFeedService legallyMarriedInThailandRssFeedService;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public LegallyMarriedInThailandNewsScheduler(LegallyMarriedInThailandRssFeedService legallyMarriedInThailandRssFeedService) {
        this.legallyMarriedInThailandRssFeedService = legallyMarriedInThailandRssFeedService;
    }

    @PostConstruct
    public void scheduleScraping() {
        scheduler.schedule(this::runAndReschedule, 10, TimeUnit.MINUTES);
    }

    private void runAndReschedule() {
        legallyMarriedInThailandRssFeedService.fetchAndStoreLatestNews();
    }

}