package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.repository.LegallyMarriedInThailandNewsRepository;
import com.thaipulse.newsapp.service.LegallyMarriedInThailandRssFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LegallyMarriedInThailandNewsScheduler {

    private final LegallyMarriedInThailandRssFeedService legallyMarriedInThailandRssFeedService;
    private final LegallyMarriedInThailandNewsRepository legallyMarriedInThailandNewsRepository;

    @Scheduled(fixedRate = 1000000)
    public void refereshNews() {

    }
}
