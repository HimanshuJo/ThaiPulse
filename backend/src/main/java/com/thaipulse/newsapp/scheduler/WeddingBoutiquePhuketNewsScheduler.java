package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.repository.WeddingBoutiquePhuketNewsRepository;
import com.thaipulse.newsapp.service.WeddingBoutiquePhuketRssFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeddingBoutiquePhuketNewsScheduler {

    private final WeddingBoutiquePhuketRssFeedService weddingBoutiquePhuketRssFeedService;
    private final WeddingBoutiquePhuketNewsRepository weddingBoutiquePhuketNewsRepository;

    @Scheduled(fixedRate = 1000000)
    public void refereshNews() {
        return;
    }
}
