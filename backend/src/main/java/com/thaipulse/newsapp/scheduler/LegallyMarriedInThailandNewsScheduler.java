package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.model.LegallyMarriedInThailandNews;
import com.thaipulse.newsapp.repository.LegallyMarriedInThailandNewsRepository;
import com.thaipulse.newsapp.service.LegallyMarriedInThailandRssFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LegallyMarriedInThailandNewsScheduler {

    private final LegallyMarriedInThailandRssFeedService legallyMarriedInThailandRssFeedService;
    private final LegallyMarriedInThailandNewsRepository legallyMarriedInThailandNewsRepository;

    @Scheduled(fixedRate = 500000)
    public void refereshNews() {
        List<LegallyMarriedInThailandNews> fetchedNews = new ArrayList<>(legallyMarriedInThailandRssFeedService.getNewsFromRss("https://www.legallymarriedinthailand.com/feed/"));
        Collections.shuffle(fetchedNews);
        List<LegallyMarriedInThailandNews> uniqueNews = fetchedNews.stream()
                .filter(news -> !legallyMarriedInThailandNewsRepository.existsByLink(news.getLink()))
                .toList();

        long count = legallyMarriedInThailandNewsRepository.count();
        if (count < 2000) {
            if (!uniqueNews.isEmpty()) {
                legallyMarriedInThailandNewsRepository.saveAll(uniqueNews);
            }
        } else {
            legallyMarriedInThailandNewsRepository.deleteAllInBatch();
            legallyMarriedInThailandNewsRepository.saveAll(uniqueNews);
        }
    }
}
