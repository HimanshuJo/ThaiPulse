package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.model.BangkokScoopNews;
import com.thaipulse.newsapp.repository.BangkokScoopNewsRepository;
import com.thaipulse.newsapp.service.BangkokScoopRssFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BangkokScoopNewsScheduler {

    private final BangkokScoopRssFeedService bangkokScoopRssFeedService;
    private final BangkokScoopNewsRepository bangkokScoopNewsRepository;

    @Scheduled(fixedRate = 30000)
    public void refereshNews() {
        List<BangkokScoopNews> fetchedNews = new ArrayList<>(bangkokScoopRssFeedService.getNewsFromRss("https://feeds" +
                ".feedburner" +
                ".com/bangkokscoop/UIwo"));
        Collections.shuffle(fetchedNews);
        List<BangkokScoopNews> uniqueNews = fetchedNews.stream()
                .filter(news -> !bangkokScoopNewsRepository.existsByLink(news.getLink()))
                .toList();

        long count = bangkokScoopNewsRepository.count();
        if (count < 2000) {
            if (!uniqueNews.isEmpty()) {
                bangkokScoopNewsRepository.saveAll(uniqueNews);
            }
        } else {
            bangkokScoopNewsRepository.deleteAllInBatch();
            bangkokScoopNewsRepository.saveAll(uniqueNews);
        }
    }
}
