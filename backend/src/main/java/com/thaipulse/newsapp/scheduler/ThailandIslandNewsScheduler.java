package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.service.ThailandIslandNewsRssFeedService;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ThailandIslandNewsScheduler {

    private final ThailandIslandNewsRssFeedService thailandIslandNewsRssFeedService;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public ThailandIslandNewsScheduler(ThailandIslandNewsRssFeedService thailandIslandNewsRssFeedService) {
        this.thailandIslandNewsRssFeedService = thailandIslandNewsRssFeedService;
    }

    @PostConstruct
    public void scheduleScraping() {
        scheduler.schedule(this::runAndReschedule, 10, TimeUnit.MINUTES);
    }

    private void runAndReschedule() {
        thailandIslandNewsRssFeedService.fetchAndStoreLatestNews();
    }

}