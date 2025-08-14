package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.TheSilomerNewsDto;
import com.thaipulse.newsapp.model.TheSilomerNews;

public class TheSilomerNewsMapper {

    public static TheSilomerNews toEntity(TheSilomerNewsDto dto) {
        TheSilomerNews news = new TheSilomerNews();
        news.setTitle(dto.getTitle());
        news.setLink(dto.getLink());
        news.setSource(dto.getSource());
        news.setImage(dto.getImage());
        return news;
    }

    public static TheSilomerNewsDto toDto(TheSilomerNews news) {
        TheSilomerNewsDto dto = new TheSilomerNewsDto();
        dto.setTitle(news.getTitle());
        dto.setLink(news.getLink());
        dto.setSource(news.getSource());
        dto.setImage(news.getImage());
        return dto;
    }
}
