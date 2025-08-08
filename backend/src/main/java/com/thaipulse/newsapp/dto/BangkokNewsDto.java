package com.thaipulse.newsapp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BangkokNewsDto {

    private String title;

    private String description;

    private String imageUrl;

    private LocalDateTime publishedDate;
}
