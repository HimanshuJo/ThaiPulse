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

    @Scheduled(fixedRate = 3000000)
    public void refreshNews() {
        List<News> fetchedNews = new ArrayList<>();
        fetchedNews.addAll(rssFeedService.getNewsFromRss("https://thediplomat.com/feed"));
        fetchedNews.addAll(rssFeedService.getNewsFromRss("https://asiatimes.com/feed"));
        /*
        allNews.addAll(rssFeedService.getNewsFromRss("https://e27.co/index_wp.php/feed"));
        allNews.addAll(rssFeedService.getNewsFromRss("https://asianscientist.com/feed/?x=1"));
        allNews.addAll(rssFeedService.getNewsFromRss("https://southeastasiaglobe.com/feed"));
        allNews.addAll(rssFeedService.getNewsFromRss("https://apec.org/feeds/rss"));
        allNews.addAll(rssFeedService.getNewsFromRss("https://indothainews.com/feed"));
        allNews.addAll(rssFeedService.getNewsFromRss("https://scmp.com/rss/91/feed"));
		allNews.addAll(rssFeedService.getNewsFromRss("https://www.bangkokpost.com/rss/data/topstories.xml"));
		allNews.addAll(rssFeedService.getNewsFromRss("https://www.bangkokpost.com/rss/data/thailand.xml"));
		allNews.addAll(rssFeedService.getNewsFromRss("https://www.bangkokpost.com/rss/data/world.xml"));
		allNews.addAll(rssFeedService.getNewsFromRss("https://www.bangkokpost.com/rss/data/business.xml"));
		allNews.addAll(rssFeedService.getNewsFromRss("https://www.bangkokpost.com/rss/data/opinion.xml"));
		allNews.addAll(rssFeedService.getNewsFromRss("https://www.bangkokpost.com/rss/data/sports.xml"));
		allNews.addAll(rssFeedService.getNewsFromRss("https://www.bangkokpost.com/rss/data/life.xml"));
		allNews.addAll(rssFeedService.getNewsFromRss("https://www.bangkokpost.com/rss/data/learning.xml"));
		allNews.addAll(rssFeedService.getNewsFromRss("thttps://www.bangkokpost.com/rss/data/video.xml"));
		allNews.addAll(rssFeedService.getNewsFromRss("https://www.bangkokpost.com/rss/data/property.xml"));
		allNews.addAll(rssFeedService.getNewsFromRss("https://www.bangkokpost.com/rss/data/photos.xml"));
		 */
        Collections.shuffle(fetchedNews);

        long count = newsRepository.count();
        if (count < 1000) {
            newsRepository.saveAll(fetchedNews);
        } else {
            newsRepository.deleteAllInBatch();
            newsRepository.saveAll(fetchedNews);
        }
    }
}

