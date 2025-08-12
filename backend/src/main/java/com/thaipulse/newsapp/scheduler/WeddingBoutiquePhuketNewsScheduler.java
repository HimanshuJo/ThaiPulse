package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.model.WeddingBoutiquePhuketNews;
import com.thaipulse.newsapp.repository.WeddingBoutiquePhuketNewsRepository;
import com.thaipulse.newsapp.service.WeddingBoutiquePhuketRssFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WeddingBoutiquePhuketNewsScheduler {

    private final WeddingBoutiquePhuketRssFeedService weddingBoutiquePhuketRssFeedService;
    private final WeddingBoutiquePhuketNewsRepository weddingBoutiquePhuketNewsRepository;

    @Scheduled(fixedRate = 500000)
    public void refereshNews() {
        List<WeddingBoutiquePhuketNews> fetchedNews = new ArrayList<>(weddingBoutiquePhuketRssFeedService.getNewsFromRss("https://weddingboutiquephuket.com/feed/"));
        Collections.shuffle(fetchedNews);
        List<WeddingBoutiquePhuketNews> uniqueNews = fetchedNews.stream()
                .filter(news -> !weddingBoutiquePhuketNewsRepository.existsByLink(news.getLink()))
                .toList();

        long count = weddingBoutiquePhuketNewsRepository.count();
        if (count < 2000) {
            if (!uniqueNews.isEmpty()) {
                weddingBoutiquePhuketNewsRepository.saveAll(uniqueNews);
            }
        } else {
            weddingBoutiquePhuketNewsRepository.deleteAllInBatch();
            weddingBoutiquePhuketNewsRepository.saveAll(uniqueNews);
        }
    }
}
