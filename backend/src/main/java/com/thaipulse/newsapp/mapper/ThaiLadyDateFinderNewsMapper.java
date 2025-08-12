package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.ThaiLadyDateFinderNewsDto;
import com.thaipulse.newsapp.model.ThaiLadyDateFinderNews;

public class ThaiLadyDateFinderNewsMapper {

    public static ThaiLadyDateFinderNews toEntity(ThaiLadyDateFinderNewsDto dto) {
        ThaiLadyDateFinderNews news = new ThaiLadyDateFinderNews();
        news.setTitle(dto.getTitle());
        news.setLink(dto.getLink());
        news.setSource(dto.getSource());
        news.setImage(dto.getImage());
        return news;
    }

    public static ThaiLadyDateFinderNewsDto toDto(ThaiLadyDateFinderNews news) {
        ThaiLadyDateFinderNewsDto dto = new ThaiLadyDateFinderNewsDto();
        dto.setTitle(news.getTitle());
        dto.setLink(news.getLink());
        dto.setSource(news.getSource());
        dto.setImage(news.getImage());
        return dto;
    }
}
