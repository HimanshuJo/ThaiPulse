package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.BicycleThailandNewsDto;
import com.thaipulse.newsapp.model.BicycleThailandNews;

public class BicycleThailandNewsMapper {

    public static BicycleThailandNews toEntity(BicycleThailandNewsDto dto) {
        BicycleThailandNews news = new BicycleThailandNews();
        news.setTitle(dto.getTitle());
        news.setLink(dto.getLink());
        news.setSource(dto.getSource());
        news.setImage(dto.getImage());
        return news;
    }

    public static BicycleThailandNewsDto toDto(BicycleThailandNews news) {
        BicycleThailandNewsDto dto = new BicycleThailandNewsDto();
        dto.setTitle(news.getTitle());
        dto.setLink(news.getLink());
        dto.setSource(news.getSource());
        dto.setImage(news.getImage());
        return dto;
    }
}
