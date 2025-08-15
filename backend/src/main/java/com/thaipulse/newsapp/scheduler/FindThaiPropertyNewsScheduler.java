package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.repository.FindThaiPropertyNewsRepository;
import com.thaipulse.newsapp.service.FindThaiPropertyRssFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindThaiPropertyNewsScheduler {

    private final FindThaiPropertyRssFeedService findThaiPropertyRssFeedService;
    private final FindThaiPropertyNewsRepository findThaiPropertyNewsRepository;

    @Scheduled(fixedRate = 1000000)
    public void refereshNews() {
        return;
    }
}
