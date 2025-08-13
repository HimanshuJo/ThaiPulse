package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.KhonKaenNewsDto;
import com.thaipulse.newsapp.model.KhonKaenNews;

public class KhonKaenNewsMapper {

    public static KhonKaenNews toEntity(KhonKaenNewsDto dto) {
        KhonKaenNews news = new KhonKaenNews();
        news.setTitle(dto.getTitle());
        news.setLink(dto.getLink());
        news.setSource(dto.getSource());
        news.setImage(dto.getImage());
        return news;
    }

    public static KhonKaenNewsDto toDto(KhonKaenNews news) {
        KhonKaenNewsDto dto = new KhonKaenNewsDto();
        dto.setTitle(news.getTitle());
        dto.setLink(news.getLink());
        dto.setSource(news.getSource());
        dto.setImage(news.getImage());
        return dto;
    }
}
