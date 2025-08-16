package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.service.PhuketNewsRssFeedService;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class PhuketNewsScheduler {

    private final PhuketNewsRssFeedService phuketNewsRssFeedService;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public PhuketNewsScheduler(PhuketNewsRssFeedService phuketNewsRssFeedService) {
        this.phuketNewsRssFeedService = phuketNewsRssFeedService;
    }

    @PostConstruct
    public void scheduleScraping() {
        scheduler.schedule(this::runAndReschedule, 10, TimeUnit.MINUTES);
    }

    private void runAndReschedule() {
        phuketNewsRssFeedService.fetchAndStoreLatestNews();
    }

}