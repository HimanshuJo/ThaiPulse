package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.service.PattayaNewsRssFeedService;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class PattayaNewsScheduler {

    private final PattayaNewsRssFeedService pattayaNewsRssFeedService;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public PattayaNewsScheduler(PattayaNewsRssFeedService pattayaNewsRssFeedService) {
        this.pattayaNewsRssFeedService = pattayaNewsRssFeedService;
    }

    @PostConstruct
    public void scheduleScraping() {
        scheduler.schedule(this::runAndReschedule, 10, TimeUnit.MINUTES);
    }

    private void runAndReschedule() {
        pattayaNewsRssFeedService.fetchAndStoreLatestNews();
    }

}