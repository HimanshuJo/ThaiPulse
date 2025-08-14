package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.DaveTheRavesThailandNewsDto;
import com.thaipulse.newsapp.model.DaveTheRavesThailandNews;

public class DaveTheRavesThailandNewsMapper {

    public static DaveTheRavesThailandNews toEntity(DaveTheRavesThailandNewsDto dto) {
        DaveTheRavesThailandNews news = new DaveTheRavesThailandNews();
        news.setTitle(dto.getTitle());
        news.setLink(dto.getLink());
        news.setSource(dto.getSource());
        news.setImage(dto.getImage());
        return news;
    }

    public static DaveTheRavesThailandNewsDto toDto(DaveTheRavesThailandNews news) {
        DaveTheRavesThailandNewsDto dto = new DaveTheRavesThailandNewsDto();
        dto.setTitle(news.getTitle());
        dto.setLink(news.getLink());
        dto.setSource(news.getSource());
        dto.setImage(news.getImage());
        return dto;
    }
}
