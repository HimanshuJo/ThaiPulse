package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.FindThaiPropertyNewsDto;
import com.thaipulse.newsapp.model.FindThaiPropertyNews;

public class FindThaiPropertyNewsMapper {

    public static FindThaiPropertyNews toEntity(FindThaiPropertyNewsDto dto) {
        FindThaiPropertyNews news = new FindThaiPropertyNews();
        news.setTitle(dto.getTitle());
        news.setLink(dto.getLink());
        news.setSource(dto.getSource());
        news.setImage(dto.getImage());
        return news;
    }

    public static FindThaiPropertyNewsDto toDto(FindThaiPropertyNews news) {
        FindThaiPropertyNewsDto dto = new FindThaiPropertyNewsDto();
        dto.setTitle(news.getTitle());
        dto.setLink(news.getLink());
        dto.setSource(news.getSource());
        dto.setImage(news.getImage());
        return dto;
    }
}
