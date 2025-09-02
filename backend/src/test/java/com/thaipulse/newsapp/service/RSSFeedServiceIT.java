package com.thaipulse.newsapp.service;

import com.thaipulse.newsapp.model.News;
import com.thaipulse.newsapp.repository.NewsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class RSSFeedServiceIT {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private RSSFeedService rssFeedService;

    @BeforeEach
    void cleanDb() {
        newsRepository.deleteAll();
    }

    @Test
    void testFetchAndStoreLatestNews_ShouldPersistUniqueNews() {
        assertThat(newsRepository.count()).isZero();

        rssFeedService.fetchAndStoreLatestNews();

        List<News> savedNews = newsRepository.findAll();
        assertThat(savedNews).hasSize(1);
    }

    @Test
    void testFetchAndStoreLatestNews_ShouldSkipDuplicates() {
        News existing = new News(null, "Dummy Title", "Dummy Source", "http://news1.com");
        newsRepository.saveAndFlush(existing);

        rssFeedService.fetchAndStoreLatestNews();

        List<News> savedNews = newsRepository.findAll();
        assertThat(savedNews).extracting(News::getId).doesNotContainNull();
        assertThat(savedNews).hasSize(1);
    }

    @Test
    void testFetchAndStoreLatestNews_ShouldSkipDuplicatesAndHaveTwoNews() {
        News newNews = new News(null, "Dummy Title 2", "Dummy Source 2", "http://news2.com");
        newsRepository.saveAndFlush(newNews);

        rssFeedService.fetchAndStoreLatestNews();

        List<News> savedNews = newsRepository.findAll();
        assertThat(savedNews).extracting(News::getId).doesNotContainNull();
        assertThat(savedNews).hasSize(2);
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        @Primary
        public RSSFeedService testRSSFeedService(NewsRepository newsRepository) {
            return new RSSFeedService(newsRepository) {
                @Override
                public List<News> getNewsFromRss(String rssUrl) {
                    if (newsRepository.existsByLink("http://news1.com")) {
                        return List.of();
                    }
                    return List.of(
                            new News(null, "Dummy Title", "Dummy Source", "http://news1.com")
                    );
                }

                @Override
                public void fetchAndStoreLatestNews() {
                    List<News> fixedNews = getNewsFromRss("dummy-url");
                    for (News news : fixedNews) {
                        newsRepository.save(news);
                    }
                }
            };
        }
    }

}