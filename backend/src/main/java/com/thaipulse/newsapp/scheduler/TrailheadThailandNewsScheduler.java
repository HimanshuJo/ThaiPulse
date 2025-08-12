package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.model.TrailheadThailandNews;
import com.thaipulse.newsapp.repository.TrailheadThailandNewsRepository;
import com.thaipulse.newsapp.service.TrailheadThailandRssFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TrailheadThailandNewsScheduler {

    private final TrailheadThailandRssFeedService trailheadThailandRssFeedService;
    private final TrailheadThailandNewsRepository trailheadThailandNewsRepository;

    @Scheduled(fixedRate = 500000)
    public void refereshNews() {
        List<TrailheadThailandNews> fetchedNews = new ArrayList<>(trailheadThailandRssFeedService.getNewsFromRss("https://www.trailhead.co.th/feed/"));
        Collections.shuffle(fetchedNews);
        List<TrailheadThailandNews> uniqueNews = fetchedNews.stream()
                .filter(news -> !trailheadThailandNewsRepository.existsByLink(news.getLink()))
                .toList();

        long count = trailheadThailandNewsRepository.count();
        if (count < 2000) {
            if (!uniqueNews.isEmpty()) {
                trailheadThailandNewsRepository.saveAll(uniqueNews);
            }
        } else {
            trailheadThailandNewsRepository.deleteAllInBatch();
            trailheadThailandNewsRepository.saveAll(uniqueNews);
        }
    }
}
