package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.WeddingBoutiquePhuketNewsDto;
import com.thaipulse.newsapp.model.WeddingBoutiquePhuketNews;

public class WeddingBoutiquePhuketNewsMapper {

    public static WeddingBoutiquePhuketNews toEntity(WeddingBoutiquePhuketNewsDto dto) {
        WeddingBoutiquePhuketNews news = new WeddingBoutiquePhuketNews();
        news.setTitle(dto.getTitle());
        news.setLink(dto.getLink());
        news.setSource(dto.getSource());
        news.setImage(dto.getImage());
        return news;
    }

    public static WeddingBoutiquePhuketNewsDto toDto(WeddingBoutiquePhuketNews news) {
        WeddingBoutiquePhuketNewsDto dto = new WeddingBoutiquePhuketNewsDto();
        dto.setTitle(news.getTitle());
        dto.setLink(news.getLink());
        dto.setSource(news.getSource());
        dto.setImage(news.getImage());
        return dto;
    }
}
