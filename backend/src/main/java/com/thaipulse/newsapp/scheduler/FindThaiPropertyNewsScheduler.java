package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.service.FindThaiPropertyRssFeedService;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class FindThaiPropertyNewsScheduler {

    private final FindThaiPropertyRssFeedService findThaiPropertyRssFeedService;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public FindThaiPropertyNewsScheduler(FindThaiPropertyRssFeedService findThaiPropertyRssFeedService) {
        this.findThaiPropertyRssFeedService = findThaiPropertyRssFeedService;
    }

    @PostConstruct
    public void scheduleScraping() {
        scheduler.schedule(this::runAndReschedule, 10, TimeUnit.MINUTES);
    }

    private void runAndReschedule() {
        findThaiPropertyRssFeedService.fetchAndStoreLatestNews();
    }

}