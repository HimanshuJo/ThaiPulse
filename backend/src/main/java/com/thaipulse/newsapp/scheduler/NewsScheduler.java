package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.model.News;
import com.thaipulse.newsapp.repository.NewsRepository;
import com.thaipulse.newsapp.service.RSSFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NewsScheduler {

    private final RSSFeedService rssFeedService;
    private final NewsRepository newsRepository;

    @Scheduled(fixedRate = 300000)
    public void refreshNews() {
        List<News> fetchedNews = new ArrayList<>();
        fetchedNews.addAll(rssFeedService.getNewsFromRss("https://thediplomat.com/feed"));
        fetchedNews.addAll(rssFeedService.getNewsFromRss("https://asiatimes.com/feed"));
        fetchedNews.addAll(rssFeedService.getNewsFromRss("https://southeastasiaglobe.com/feed"));
        Collections.shuffle(fetchedNews);

        long count = newsRepository.count();
        if (count < 2000) {
            newsRepository.saveAll(fetchedNews);
        } else {
            newsRepository.deleteAllInBatch();
            newsRepository.saveAll(fetchedNews);
        }
    }
}

