package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.ThinglishLifestyleNewsDto;
import com.thaipulse.newsapp.model.ThinglishLifestyleNews;

public class ThinglishLifestyleNewsMapper {

    public static ThinglishLifestyleNews toEntity(ThinglishLifestyleNewsDto dto) {
        ThinglishLifestyleNews news = new ThinglishLifestyleNews();
        news.setTitle(dto.getTitle());
        news.setLink(dto.getLink());
        news.setSource(dto.getSource());
        news.setImage(dto.getImage());
        return news;
    }

    public static ThinglishLifestyleNewsDto toDto(ThinglishLifestyleNews news) {
        ThinglishLifestyleNewsDto dto = new ThinglishLifestyleNewsDto();
        dto.setTitle(news.getTitle());
        dto.setLink(news.getLink());
        dto.setSource(news.getSource());
        dto.setImage(news.getImage());
        return dto;
    }
}
