package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.PattayaNewsDto;
import com.thaipulse.newsapp.model.PattayaNews;

public class PattayaNewsMapper {

    public static PattayaNews toEntity(PattayaNewsDto dto) {
        PattayaNews news = new PattayaNews();
        news.setTitle(dto.getTitle());
        news.setDescription(dto.getDescription());
        news.setImageUrl(dto.getImageUrl());
        news.setPublishedDate(dto.getPublishedDate());
        return news;
    }

    public static PattayaNewsDto toDto(PattayaNews pattayaNews) {
        PattayaNewsDto dto = new PattayaNewsDto();
        dto.setTitle(pattayaNews.getTitle());
        dto.setDescription(pattayaNews.getDescription());
        dto.setImageUrl(pattayaNews.getImageUrl());
        dto.setPublishedDate(pattayaNews.getPublishedDate());
        return dto;
    }

}
