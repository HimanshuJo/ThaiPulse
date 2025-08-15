package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.repository.NewsRepository;
import com.thaipulse.newsapp.service.RSSFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsScheduler {

    private final RSSFeedService rssFeedService;
    private final NewsRepository newsRepository;

    @Scheduled(fixedRate = 700000)
    public void refreshNews() {
        return;
    }
}

