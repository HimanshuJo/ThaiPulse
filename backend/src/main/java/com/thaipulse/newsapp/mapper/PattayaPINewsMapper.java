package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.PattayaPINewsDto;
import com.thaipulse.newsapp.model.PattayaPINews;

public class PattayaPINewsMapper {

    public static PattayaPINews toEntity(PattayaPINewsDto dto) {
        PattayaPINews news = new PattayaPINews();
        news.setTitle(dto.getTitle());
        news.setLink(dto.getLink());
        news.setSource(dto.getSource());
        news.setImage(dto.getImage());
        return news;
    }

    public static PattayaPINewsDto toDto(PattayaPINews news) {
        PattayaPINewsDto dto = new PattayaPINewsDto();
        dto.setTitle(news.getTitle());
        dto.setLink(news.getLink());
        dto.setSource(news.getSource());
        dto.setImage(news.getImage());
        return dto;
    }
}
