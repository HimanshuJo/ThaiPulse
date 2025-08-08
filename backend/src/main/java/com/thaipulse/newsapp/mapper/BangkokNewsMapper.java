package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.BangkokNewsDto;
import com.thaipulse.newsapp.model.BangkokNews;

public class BangkokNewsMapper {

    public static BangkokNews toEntity(BangkokNewsDto dto) {
        BangkokNews news = new BangkokNews();
        news.setTitle(dto.getTitle());
        news.setDescription(dto.getDescription());
        news.setImageUrl(dto.getImageUrl());
        news.setPublishedDate(dto.getPublishedDate());
        return news;
    }

    public static BangkokNewsDto toDto(BangkokNews bangkokNews) {
        BangkokNewsDto dto = new BangkokNewsDto();
        dto.setTitle(bangkokNews.getTitle());
        dto.setDescription(bangkokNews.getDescription());
        dto.setImageUrl(bangkokNews.getImageUrl());
        dto.setPublishedDate(bangkokNews.getPublishedDate());
        return dto;
    }
}
