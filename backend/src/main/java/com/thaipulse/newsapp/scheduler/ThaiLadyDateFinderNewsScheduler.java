package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.repository.ThaiLadyDateFinderNewsRepository;
import com.thaipulse.newsapp.service.ThaiLadyDateFinderRssFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ThaiLadyDateFinderNewsScheduler {

    private final ThaiLadyDateFinderRssFeedService thaiLadyDateFinderRssFeedService;
    private final ThaiLadyDateFinderNewsRepository thaiLadyDateFinderNewsRepository;

    @Scheduled(fixedRate = 1000000)
    public void refereshNews() {
        return;
    }
}
