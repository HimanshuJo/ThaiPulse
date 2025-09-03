package com.thaipulse.newsapp.service;

import com.thaipulse.newsapp.dto.NewsDto;
import com.thaipulse.newsapp.model.News;
import com.thaipulse.newsapp.repository.NewsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class RSSFeedServiceTest {

    @Mock
    private NewsRepository newsRepository;

    @InjectMocks
    private RSSFeedService rssFeedService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testNewsCheck_WhenNewsExists() {
        when(newsRepository.count()).thenReturn(5L);

        boolean result = rssFeedService.newsCheck();

        assertThat(result).isTrue();
        verify(newsRepository, times(1)).count();
    }

    @Test
    void testExtractImageFromHtml_ShouldReturnImageUrl() {
        String html = "<p>Some text <img src='https://example.com/image.jpg'></p>";

        String result = invokeExtractImage(html);

        assertThat(result).isEqualTo("https://example.com/image.jpg");
    }

    @Test
    void testExtractImageFromHtml_ShouldReturnNull_WhenNoImage() {
        String html = "<p>No image here</p>";

        String result = invokeExtractImage(html);

        assertThat(result).isNull();
    }

    @Test
    void testFetchAndStoreLatestNews_WhenUnderLimit() {
        News mockNews = new News();
        mockNews.setLink("http://unique-news.com");

        when(newsRepository.count()).thenReturn(50L);
        when(newsRepository.existsByLink("http://unique-news.com")).thenReturn(false);

        RSSFeedService spyService = spy(rssFeedService);
        doReturn(List.of(mockNews)).when(spyService).getNewsFromRss(anyString());

        spyService.fetchAndStoreLatestNews();

        ArgumentCaptor<News> captor = ArgumentCaptor.forClass(News.class);
        verify(newsRepository, atLeastOnce()).save(captor.capture());
        assertThat(captor.getValue().getLink()).isEqualTo("http://unique-news.com");
    }

    @Test
    void testFetchAndStoreLatestNews_WhenDuplicate_ShouldSkip() {
        News duplicateNews = new News();
        duplicateNews.setLink("http://duplicate.com");

        when(newsRepository.count()).thenReturn(100L);
        when(newsRepository.existsByLink("http://duplicate.com")).thenReturn(true);

        RSSFeedService spyService = spy(rssFeedService);
        doReturn(List.of(duplicateNews)).when(spyService).getNewsFromRss(anyString());

        spyService.fetchAndStoreLatestNews();

        verify(newsRepository, never()).save(any(News.class));
    }

    @Test
    void testGetPaginatedNews_ShouldReturnDtos() {
        News news1 = new News();
        news1.setId(1L);
        news1.setTitle("Test News");

        Pageable pageable = PageRequest.of(0, 20, Sort.by("id").descending());
        Page<News> page = new PageImpl<>(Collections.singletonList(news1), pageable, 1);

        when(newsRepository.findAll(pageable)).thenReturn(page);

        Page<NewsDto> result = rssFeedService.getPaginatedNews(0, 20);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Test News");
    }

    private String invokeExtractImage(String html) {
        try {
            var method = RSSFeedService.class.getDeclaredMethod("extractImageFromHtml", String.class);
            method.setAccessible(true);
            return (String) method.invoke(rssFeedService, html);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}