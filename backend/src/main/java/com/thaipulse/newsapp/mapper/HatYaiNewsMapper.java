package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.HatYaiNewsDto;
import com.thaipulse.newsapp.model.HatYaiNews;

public class HatYaiNewsMapper {

    public static HatYaiNews toEntity(HatYaiNewsDto dto) {
        HatYaiNews news = new HatYaiNews();
        news.setTitle(dto.getTitle());
        news.setLink(dto.getLink());
        news.setSource(dto.getSource());
        news.setImage(dto.getImage());
        return news;
    }

    public static HatYaiNewsDto toDto(HatYaiNews news) {
        HatYaiNewsDto dto = new HatYaiNewsDto();
        dto.setTitle(news.getTitle());
        dto.setLink(news.getLink());
        dto.setSource(news.getSource());
        dto.setImage(news.getImage());
        return dto;
    }
}
