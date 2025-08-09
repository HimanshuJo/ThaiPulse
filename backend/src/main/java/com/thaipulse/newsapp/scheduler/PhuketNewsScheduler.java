package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.service.PhuketNewsScraperService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class PhuketNewsScheduler {

    private final PhuketNewsScraperService scraperService;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public PhuketNewsScheduler(PhuketNewsScraperService scraperService) {
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
        }, 4, 7, TimeUnit.MINUTES);
    }
}
