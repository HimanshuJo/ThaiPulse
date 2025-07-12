package com.thaipulse.newsapp.controller;

import com.thaipulse.newsapp.dto.NewsDto;
import com.thaipulse.newsapp.mapper.NewsMapper;
import com.thaipulse.newsapp.repository.NewsRepository;
import com.thaipulse.newsapp.service.RSSFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewsController {

    @Autowired
    private RSSFeedService rssFeedService;

    @Autowired
    NewsRepository newsRepository;

    @GetMapping(value = "/news")
    public List<NewsDto> getAllNews() {
        return newsRepository.findAll()
                .stream()
                .map(NewsMapper::toDto)
                .toList();
    }
}
