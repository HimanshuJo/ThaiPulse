package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.NewsDto;
import com.thaipulse.newsapp.model.News;

public class NewsMapper {

    public static News toEntity(NewsDto dto) {
        News news = new News();
        news.setTitle(dto.getTitle());
        news.setSource(dto.getSource());
        news.setLink(dto.getLink());
        news.setImage(dto.getImage());
        return news;
    }

    public static NewsDto toDto(News news) {
        NewsDto dto = new NewsDto();
        dto.setTitle(news.getTitle());
        dto.setSource(news.getSource());
        dto.setLink(news.getLink());
        dto.setImage(news.getImage());
        return dto;
    }
}

