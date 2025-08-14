package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.BudgetCatcherNewsDto;
import com.thaipulse.newsapp.model.BudgetCatcherNews;

public class BudgetCatcherNewsMapper {

    public static BudgetCatcherNews toEntity(BudgetCatcherNewsDto dto) {
        BudgetCatcherNews news = new BudgetCatcherNews();
        news.setTitle(dto.getTitle());
        news.setLink(dto.getLink());
        news.setSource(dto.getSource());
        news.setImage(dto.getImage());
        return news;
    }

    public static BudgetCatcherNewsDto toDto(BudgetCatcherNews news) {
        BudgetCatcherNewsDto dto = new BudgetCatcherNewsDto();
        dto.setTitle(news.getTitle());
        dto.setLink(news.getLink());
        dto.setSource(news.getSource());
        dto.setImage(news.getImage());
        return dto;
    }
}
