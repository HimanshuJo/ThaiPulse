package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.service.ThaiLadyDateFinderRssFeedService;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ThaiLadyDateFinderNewsScheduler {

    private final ThaiLadyDateFinderRssFeedService thaiLadyDateFinderRssFeedService;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public ThaiLadyDateFinderNewsScheduler(ThaiLadyDateFinderRssFeedService thaiLadyDateFinderRssFeedService) {
        this.thaiLadyDateFinderRssFeedService = thaiLadyDateFinderRssFeedService;
    }

    @PostConstruct
    public void scheduleScraping() {
        scheduler.schedule(this::runAndReschedule, 10, TimeUnit.MINUTES);
    }

    private void runAndReschedule() {
        thaiLadyDateFinderRssFeedService.fetchAndStoreLatestNews();
    }

}