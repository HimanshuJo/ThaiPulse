package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.ChiangMaiNewsDto;
import com.thaipulse.newsapp.model.ChiangMaiNews;

public class ChiangMaiNewsMapper {

    public static ChiangMaiNews toEntity(ChiangMaiNewsDto dto) {
        ChiangMaiNews news = new ChiangMaiNews();
        news.setTitle(dto.getTitle());
        news.setLink(dto.getLink());
        news.setSource(dto.getSource());
        news.setImage(dto.getImage());
        return news;
    }

    public static ChiangMaiNewsDto toDto(ChiangMaiNews news) {
        ChiangMaiNewsDto dto = new ChiangMaiNewsDto();
        dto.setTitle(news.getTitle());
        dto.setLink(news.getLink());
        dto.setSource(news.getSource());
        dto.setImage(news.getImage());
        return dto;
    }
}
