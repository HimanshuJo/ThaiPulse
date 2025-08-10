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

    //@PostConstruct
    public void scheduleScraping() {
        scheduler.schedule(this::runAndReschedule, 1, TimeUnit.MINUTES);
    }

    private String getTableName() {
        return "Phuket News";
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
            }, 7, 3, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
