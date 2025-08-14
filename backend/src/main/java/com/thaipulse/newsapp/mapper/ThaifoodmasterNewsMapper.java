package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.ThaifoodmasterNewsDto;
import com.thaipulse.newsapp.model.ThaifoodmasterNews;

public class ThaifoodmasterNewsMapper {

    public static ThaifoodmasterNews toEntity(ThaifoodmasterNewsDto dto) {
        ThaifoodmasterNews news = new ThaifoodmasterNews();
        news.setTitle(dto.getTitle());
        news.setLink(dto.getLink());
        news.setSource(dto.getSource());
        news.setImage(dto.getImage());
        return news;
    }

    public static ThaifoodmasterNewsDto toDto(ThaifoodmasterNews news) {
        ThaifoodmasterNewsDto dto = new ThaifoodmasterNewsDto();
        dto.setTitle(news.getTitle());
        dto.setLink(news.getLink());
        dto.setSource(news.getSource());
        dto.setImage(news.getImage());
        return dto;
    }
}
