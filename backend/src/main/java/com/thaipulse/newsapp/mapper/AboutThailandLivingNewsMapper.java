package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.AboutThailandLivingNewsDto;
import com.thaipulse.newsapp.model.AboutThailandLivingNews;

public class AboutThailandLivingNewsMapper {

    public static AboutThailandLivingNews toEntity(AboutThailandLivingNewsDto dto) {
        AboutThailandLivingNews news = new AboutThailandLivingNews();
        news.setTitle(dto.getTitle());
        news.setLink(dto.getLink());
        news.setSource(dto.getSource());
        news.setImage(dto.getImage());
        return news;
    }

    public static AboutThailandLivingNewsDto toDto(AboutThailandLivingNews news) {
        AboutThailandLivingNewsDto dto = new AboutThailandLivingNewsDto();
        dto.setTitle(news.getTitle());
        dto.setLink(news.getLink());
        dto.setSource(news.getSource());
        dto.setImage(news.getImage());
        return dto;
    }
}
