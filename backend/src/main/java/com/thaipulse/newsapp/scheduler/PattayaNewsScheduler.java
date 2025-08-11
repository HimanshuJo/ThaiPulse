package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.service.PattayaNewsScraperService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class PattayaNewsScheduler {

    private final PattayaNewsScraperService scraperService;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public PattayaNewsScheduler(PattayaNewsScraperService scraperService) {
        this.scraperService = scraperService;
    }

    @PostConstruct
    public void scheduleScraping() {
        scheduler.schedule(this::runAndReschedule, 2, TimeUnit.MINUTES);
    }

    private String getTableName() {
        return "Pattaya News";
    }

    private void runAndReschedule() {
        return;
    }
}
