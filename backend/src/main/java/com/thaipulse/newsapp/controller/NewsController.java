package com.thaipulse.newsapp.controller;

import com.thaipulse.newsapp.dto.NewsDto;
import com.thaipulse.newsapp.service.RSSFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewsController {

    private final RSSFeedService rssFeedService;

    public NewsController(RSSFeedService rssFeedService) {
        this.rssFeedService = rssFeedService;
    }

    @GetMapping(value = "/news")
    public ResponseEntity<?> getAllNews(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "20") int size) {
        if (size < 1) {
            size = 20;
        }
        long totalNews = rssFeedService.countAllNews();
        if (totalNews > 1000) {
            Page<NewsDto> pagedResult = rssFeedService.getPaginatedNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<NewsDto> allNews = rssFeedService.getPaginatedNews(0, (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }
}
