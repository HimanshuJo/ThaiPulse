package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.PhuketNewsDto;
import com.thaipulse.newsapp.model.PhuketNews;

public class PhuketNewsMapper {

    public static PhuketNews toEntity(PhuketNewsDto dto) {
        PhuketNews news = new PhuketNews();
        news.setTitle(dto.getTitle());
        news.setSource(dto.getSource());
        news.setLink(dto.getLink());
        news.setImage(dto.getImage());
        news.setPublishedDate(dto.getPublishedDate());
        return news;
    }

    public static PhuketNewsDto toDto(PhuketNews phuketNews) {
        PhuketNewsDto dto = new PhuketNewsDto();
        dto.setTitle(phuketNews.getTitle());
        dto.setSource(phuketNews.getSource());
        dto.setLink(phuketNews.getLink());
        dto.setImage(phuketNews.getImage());
        dto.setPublishedDate(phuketNews.getPublishedDate());
        return dto;
    }
}
