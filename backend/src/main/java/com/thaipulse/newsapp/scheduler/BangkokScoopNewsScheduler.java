package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.repository.BangkokScoopNewsRepository;
import com.thaipulse.newsapp.service.BangkokScoopRssFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BangkokScoopNewsScheduler {

    private final BangkokScoopRssFeedService bangkokScoopRssFeedService;
    private final BangkokScoopNewsRepository bangkokScoopNewsRepository;

    @Scheduled(fixedRate = 1000000)
    public void refereshNews() {
        return;
    }
}
