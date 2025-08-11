package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.ThailandIslandNewsDto;
import com.thaipulse.newsapp.model.ThailandIslandNews;

public class ThailandIslandNewsMapper {

    public static ThailandIslandNews toEntity(ThailandIslandNewsDto dto) {
        ThailandIslandNews news = new ThailandIslandNews();
        news.setTitle(dto.getTitle());
        news.setLink(dto.getLink());
        news.setSource(dto.getSource());
        news.setImage(dto.getImage());
        return news;
    }

    public static ThailandIslandNewsDto toDto(ThailandIslandNews news) {
        ThailandIslandNewsDto dto = new ThailandIslandNewsDto();
        dto.setTitle(news.getTitle());
        dto.setLink(news.getLink());
        dto.setSource(news.getSource());
        dto.setImage(news.getImage());
        return dto;
    }
}
