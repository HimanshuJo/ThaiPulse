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

    //@PostConstruct
    public void scheduleScraping() {
        scheduler.schedule(this::runAndReschedule, 1, TimeUnit.MINUTES);
    }

    private String getTableName() {
        return "Pattaya News";
    }

    private void runAndReschedule() {
        try {
            scraperService.fetchAndStoreLatestNews();
            System.out.println("Initial fetch completed for: " + getTableName());

            scheduler.scheduleAtFixedRate(() -> {
                try {
                    scraperService.fetchAndStoreLatestNews();
                    System.out.println("Scheduled fetch completed for: " + getTableName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 3, 3, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
