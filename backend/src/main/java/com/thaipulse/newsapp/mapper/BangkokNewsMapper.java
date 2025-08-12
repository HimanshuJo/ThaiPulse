package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.BangkokNewsDto;
import com.thaipulse.newsapp.model.BangkokNews;

public class BangkokNewsMapper {

    public static BangkokNews toEntity(BangkokNewsDto dto) {
        BangkokNews news = new BangkokNews();
        news.setTitle(dto.getTitle());
        news.setSource(dto.getSource());
        news.setLink(dto.getLink());
        news.setImage(dto.getImage());
        news.setPublishedDate(dto.getPublishedDate());
        return news;
    }

    public static BangkokNewsDto toDto(BangkokNews bangkokNews) {
        BangkokNewsDto dto = new BangkokNewsDto();
        dto.setTitle(bangkokNews.getTitle());
        dto.setSource(bangkokNews.getSource());
        dto.setLink(bangkokNews.getLink());
        dto.setImage(bangkokNews.getImage());
        dto.setPublishedDate(bangkokNews.getPublishedDate());
        return dto;
    }
}
