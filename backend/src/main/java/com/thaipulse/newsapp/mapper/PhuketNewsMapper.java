package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.PhuketNewsDto;
import com.thaipulse.newsapp.model.PhuketNews;

public class PhuketNewsMapper {

    public static PhuketNews toEntity(PhuketNewsDto dto) {
        PhuketNews news = new PhuketNews();
        news.setTitle(dto.getTitle());
        news.setDescription(dto.getDescription());
        news.setImageUrl(dto.getImageUrl());
        news.setPublishedDate(dto.getPublishedDate());
        return news;
    }

    public static PhuketNewsDto toDto(PhuketNews phuketNews) {
        PhuketNewsDto dto = new PhuketNewsDto();
        dto.setTitle(phuketNews.getTitle());
        dto.setDescription(phuketNews.getDescription());
        dto.setImageUrl(phuketNews.getImageUrl());
        dto.setPublishedDate(phuketNews.getPublishedDate());
        return dto;
    }
}
