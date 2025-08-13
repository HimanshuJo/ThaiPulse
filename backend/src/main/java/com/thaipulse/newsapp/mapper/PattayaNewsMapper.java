package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.PattayaNewsDto;
import com.thaipulse.newsapp.model.PattayaNews;

public class PattayaNewsMapper {

    public static PattayaNews toEntity(PattayaNewsDto dto) {
        PattayaNews news = new PattayaNews();
        news.setTitle(dto.getTitle());
        news.setSource(dto.getSource());
        news.setLink(dto.getLink());
        news.setImage(dto.getImage());
        news.setPublishedDate(dto.getPublishedDate());
        return news;
    }

    public static PattayaNewsDto toDto(PattayaNews pattayaNews) {
        PattayaNewsDto dto = new PattayaNewsDto();
        dto.setTitle(pattayaNews.getTitle());
        dto.setSource(pattayaNews.getSource());
        dto.setLink(pattayaNews.getLink());
        dto.setImage(pattayaNews.getImage());
        dto.setPublishedDate(pattayaNews.getPublishedDate());
        return dto;
    }

}
