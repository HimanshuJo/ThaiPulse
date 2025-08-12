package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.LegallyMarriedInThailandNewsDto;
import com.thaipulse.newsapp.model.LegallyMarriedInThailandNews;

public class LegallyMarriedInThailandNewsMapper {

    public static LegallyMarriedInThailandNews toEntity(LegallyMarriedInThailandNewsDto dto) {
        LegallyMarriedInThailandNews news = new LegallyMarriedInThailandNews();
        news.setTitle(dto.getTitle());
        news.setLink(dto.getLink());
        news.setSource(dto.getSource());
        news.setImage(dto.getImage());
        return news;
    }

    public static LegallyMarriedInThailandNewsDto toDto(LegallyMarriedInThailandNews news) {
        LegallyMarriedInThailandNewsDto dto = new LegallyMarriedInThailandNewsDto();
        dto.setTitle(news.getTitle());
        dto.setLink(news.getLink());
        dto.setSource(news.getSource());
        dto.setImage(news.getImage());
        return dto;
    }
}
