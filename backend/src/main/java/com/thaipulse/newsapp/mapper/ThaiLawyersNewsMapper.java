package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.ThaiLawyersNewsDto;
import com.thaipulse.newsapp.model.ThaiLawyersNews;

public class ThaiLawyersNewsMapper {

    public static ThaiLawyersNews toEntity(ThaiLawyersNewsDto dto) {
        ThaiLawyersNews news = new ThaiLawyersNews();
        news.setTitle(dto.getTitle());
        news.setLink(dto.getLink());
        news.setSource(dto.getSource());
        news.setImage(dto.getImage());
        return news;
    }

    public static ThaiLawyersNewsDto toDto(ThaiLawyersNews news) {
        ThaiLawyersNewsDto dto = new ThaiLawyersNewsDto();
        dto.setTitle(news.getTitle());
        dto.setLink(news.getLink());
        dto.setSource(news.getSource());
        dto.setImage(news.getImage());
        return dto;
    }
}
