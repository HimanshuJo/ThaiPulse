package com.thaipulse.newsapp.dto;

import lombok.Data;

@Data
public class NewsDto {
    private String title;
    private String link;
    private String source;
    private String image;
}
