package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.service.PhuketNewsRssFeedService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class PhuketNewsScheduler {

    private final PhuketNewsRssFeedService scraperService;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public PhuketNewsScheduler(PhuketNewsRssFeedService scraperService) {
        this.scraperService = scraperService;
    }

    @PostConstruct
    public void scheduleScraping() {
        scheduler.schedule(this::runAndReschedule, 3, TimeUnit.MINUTES);
    }

    private String getTableName() {
        return "Phuket News";
    }

    private void runAndReschedule() {
        return;
    }
}
