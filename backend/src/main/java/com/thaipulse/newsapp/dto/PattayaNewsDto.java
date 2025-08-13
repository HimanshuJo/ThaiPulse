package com.thaipulse.newsapp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PattayaNewsDto {

    private String title;

    private String source;

    private String link;

    private String image;

    private LocalDateTime publishedDate;
}
