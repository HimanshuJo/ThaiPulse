package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.BangkokScoopNewsDto;
import com.thaipulse.newsapp.dto.NewsDto;
import com.thaipulse.newsapp.model.BangkokScoopNews;

public class BangkokScoopNewsMapper {

    public static BangkokScoopNews toEntity(NewsDto dto) {
        BangkokScoopNews news = new BangkokScoopNews();
        news.setTitle(dto.getTitle());
        news.setLink(dto.getLink());
        news.setSource(dto.getSource());
        news.setImage(dto.getImage());
        return news;
    }

    public static BangkokScoopNewsDto toDto(BangkokScoopNews news) {
        BangkokScoopNewsDto dto = new BangkokScoopNewsDto();
        dto.setTitle(news.getTitle());
        dto.setLink(news.getLink());
        dto.setSource(news.getSource());
        dto.setImage(news.getImage());
        return dto;
    }
}
