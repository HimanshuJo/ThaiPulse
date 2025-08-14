package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.FashionGalleriaNewsDto;
import com.thaipulse.newsapp.model.FashionGalleriaNews;

public class FashionGalleriaNewsMapper {

    public static FashionGalleriaNews toEntity(FashionGalleriaNewsDto dto) {
        FashionGalleriaNews news = new FashionGalleriaNews();
        news.setTitle(dto.getTitle());
        news.setLink(dto.getLink());
        news.setSource(dto.getSource());
        news.setImage(dto.getImage());
        return news;
    }

    public static FashionGalleriaNewsDto toDto(FashionGalleriaNews news) {
        FashionGalleriaNewsDto dto = new FashionGalleriaNewsDto();
        dto.setTitle(news.getTitle());
        dto.setLink(news.getLink());
        dto.setSource(news.getSource());
        dto.setImage(news.getImage());
        return dto;
    }
}
