package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.NakhonRatchasimaNewsDto;
import com.thaipulse.newsapp.model.NakhonRatchasimaNews;

public class NakhonRatchasimaNewsMapper {

    public static NakhonRatchasimaNews toEntity(NakhonRatchasimaNewsDto dto) {
        NakhonRatchasimaNews news = new NakhonRatchasimaNews();
        news.setTitle(dto.getTitle());
        news.setLink(dto.getLink());
        news.setSource(dto.getSource());
        news.setImage(dto.getImage());
        return news;
    }

    public static NakhonRatchasimaNewsDto toDto(NakhonRatchasimaNews news) {
        NakhonRatchasimaNewsDto dto = new NakhonRatchasimaNewsDto();
        dto.setTitle(news.getTitle());
        dto.setLink(news.getLink());
        dto.setSource(news.getSource());
        dto.setImage(news.getImage());
        return dto;
    }
}
