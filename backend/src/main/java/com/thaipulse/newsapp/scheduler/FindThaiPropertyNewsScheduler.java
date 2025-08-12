package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.model.FindThaiPropertyNews;
import com.thaipulse.newsapp.repository.FindThaiPropertyNewsRepository;
import com.thaipulse.newsapp.service.FindThaiPropertyRssFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FindThaiPropertyNewsScheduler {

    private final FindThaiPropertyRssFeedService findThaiPropertyRssFeedService;
    private final FindThaiPropertyNewsRepository findThaiPropertyNewsRepository;

    @Scheduled(fixedRate = 500000)
    public void refereshNews() {
        List<FindThaiPropertyNews> fetchedNews = new ArrayList<>(findThaiPropertyRssFeedService.getNewsFromRss("https://www.findthaiproperty.com/blog-homepage/feed/"));
        Collections.shuffle(fetchedNews);
        List<FindThaiPropertyNews> uniqueNews = fetchedNews.stream()
                .filter(news -> !findThaiPropertyNewsRepository.existsByLink(news.getLink()))
                .toList();

        long count = findThaiPropertyNewsRepository.count();
        if (count < 2000) {
            if (!uniqueNews.isEmpty()) {
                findThaiPropertyNewsRepository.saveAll(uniqueNews);
            }
        } else {
            findThaiPropertyNewsRepository.deleteAllInBatch();
            findThaiPropertyNewsRepository.saveAll(uniqueNews);
        }
    }
}
