package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.LifestyleInThailandNewsDto;
import com.thaipulse.newsapp.model.LifestyleInThailandNews;

public class LifestyleInThailandNewsMapper {

    public static LifestyleInThailandNews toEntity(LifestyleInThailandNewsDto dto) {
        LifestyleInThailandNews news = new LifestyleInThailandNews();
        news.setTitle(dto.getTitle());
        news.setLink(dto.getLink());
        news.setSource(dto.getSource());
        news.setImage(dto.getImage());
        return news;
    }

    public static LifestyleInThailandNewsDto toDto(LifestyleInThailandNews news) {
        LifestyleInThailandNewsDto dto = new LifestyleInThailandNewsDto();
        dto.setTitle(news.getTitle());
        dto.setLink(news.getLink());
        dto.setSource(news.getSource());
        dto.setImage(news.getImage());
        return dto;
    }
}
