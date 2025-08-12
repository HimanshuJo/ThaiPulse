package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.TrailheadThailandNewsDto;
import com.thaipulse.newsapp.model.TrailheadThailandNews;

public class TrailheadThailandNewsMapper {

    public static TrailheadThailandNews toEntity(TrailheadThailandNewsDto dto) {
        TrailheadThailandNews news = new TrailheadThailandNews();
        news.setTitle(dto.getTitle());
        news.setLink(dto.getLink());
        news.setSource(dto.getSource());
        news.setImage(dto.getImage());
        return news;
    }

    public static TrailheadThailandNewsDto toDto(TrailheadThailandNews news) {
        TrailheadThailandNewsDto dto = new TrailheadThailandNewsDto();
        dto.setTitle(news.getTitle());
        dto.setLink(news.getLink());
        dto.setSource(news.getSource());
        dto.setImage(news.getImage());
        return dto;
    }
}
