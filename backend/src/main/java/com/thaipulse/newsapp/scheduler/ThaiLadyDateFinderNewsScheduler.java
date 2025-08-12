package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.model.ThaiLadyDateFinderNews;
import com.thaipulse.newsapp.repository.ThaiLadyDateFinderNewsRepository;
import com.thaipulse.newsapp.service.ThaiLadyDateFinderRssFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ThaiLadyDateFinderNewsScheduler {

    private final ThaiLadyDateFinderRssFeedService thaiLadyDateFinderRssFeedService;
    private final ThaiLadyDateFinderNewsRepository thaiLadyDateFinderNewsRepository;

    @Scheduled(fixedRate = 500000)
    public void refereshNews() {
        List<ThaiLadyDateFinderNews> fetchedNews = new ArrayList<>(thaiLadyDateFinderRssFeedService.getNewsFromRss("https://blog.thailadydatefinder.com/feed/"));
        Collections.shuffle(fetchedNews);
        List<ThaiLadyDateFinderNews> uniqueNews = fetchedNews.stream()
                .filter(news -> !thaiLadyDateFinderNewsRepository.existsByLink(news.getLink()))
                .toList();

        long count = thaiLadyDateFinderNewsRepository.count();
        if (count < 2000) {
            if (!uniqueNews.isEmpty()) {
                thaiLadyDateFinderNewsRepository.saveAll(uniqueNews);
            }
        } else {
            thaiLadyDateFinderNewsRepository.deleteAllInBatch();
            thaiLadyDateFinderNewsRepository.saveAll(uniqueNews);
        }
    }
}
