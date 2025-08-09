package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.service.BangkokNewsScraperService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class BangkokNewsScheduler {

    private final BangkokNewsScraperService scraperService;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public BangkokNewsScheduler(BangkokNewsScraperService scraperService) {
        this.scraperService = scraperService;
    }

    @PostConstruct
    public void scheduleScraping() {
        scheduler.schedule(this::runAndReschedule, 1, TimeUnit.MINUTES);
    }

    private void runAndReschedule() {
        try {
            scraperService.fetchAndStoreLatestNews();
        } catch (Exception e) {
            e.printStackTrace();
        }

        scheduler.scheduleAtFixedRate(() -> {
            try {
                scraperService.fetchAndStoreLatestNews();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 2, 4, TimeUnit.MINUTES);
    }
}
