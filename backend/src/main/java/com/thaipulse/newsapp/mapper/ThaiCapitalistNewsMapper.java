package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.ThaiCapitalistNewsDto;
import com.thaipulse.newsapp.model.ThaiCapitalistNews;

public class ThaiCapitalistNewsMapper {

    public static ThaiCapitalistNews toEntity(ThaiCapitalistNewsDto dto) {
        ThaiCapitalistNews news = new ThaiCapitalistNews();
        news.setTitle(dto.getTitle());
        news.setLink(dto.getLink());
        news.setSource(dto.getSource());
        news.setImage(dto.getImage());
        return news;
    }

    public static ThaiCapitalistNewsDto toDto(ThaiCapitalistNews news) {
        ThaiCapitalistNewsDto dto = new ThaiCapitalistNewsDto();
        dto.setTitle(news.getTitle());
        dto.setLink(news.getLink());
        dto.setSource(news.getSource());
        dto.setImage(news.getImage());
        return dto;
    }
}
