package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.ThatBangkokLifeNewsDto;
import com.thaipulse.newsapp.model.ThatBangkokLifeNews;

public class ThatBangkokLifeNewsMapper {

    public static ThatBangkokLifeNews toEntity(ThatBangkokLifeNewsDto dto) {
        ThatBangkokLifeNews news = new ThatBangkokLifeNews();
        news.setTitle(dto.getTitle());
        news.setLink(dto.getLink());
        news.setSource(dto.getSource());
        news.setImage(dto.getImage());
        return news;
    }

    public static ThatBangkokLifeNewsDto toDto(ThatBangkokLifeNews news) {
        ThatBangkokLifeNewsDto dto = new ThatBangkokLifeNewsDto();
        dto.setTitle(news.getTitle());
        dto.setLink(news.getLink());
        dto.setSource(news.getSource());
        dto.setImage(news.getImage());
        return dto;
    }
}
