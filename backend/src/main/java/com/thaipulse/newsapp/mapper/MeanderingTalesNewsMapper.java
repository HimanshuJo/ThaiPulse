package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.MeanderingTalesNewsDto;
import com.thaipulse.newsapp.model.MeanderingTalesNews;

public class MeanderingTalesNewsMapper {

    public static MeanderingTalesNews toEntity(MeanderingTalesNewsDto dto) {
        MeanderingTalesNews news = new MeanderingTalesNews();
        news.setTitle(dto.getTitle());
        news.setLink(dto.getLink());
        news.setSource(dto.getSource());
        news.setImage(dto.getImage());
        return news;
    }

    public static MeanderingTalesNewsDto toDto(MeanderingTalesNews news) {
        MeanderingTalesNewsDto dto = new MeanderingTalesNewsDto();
        dto.setTitle(news.getTitle());
        dto.setLink(news.getLink());
        dto.setSource(news.getSource());
        dto.setImage(news.getImage());
        return dto;
    }
}
