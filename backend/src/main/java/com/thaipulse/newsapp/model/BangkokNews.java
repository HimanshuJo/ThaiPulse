package com.thaipulse.newsapp.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bangkok_news")
public class BangkokNews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 5000)
    private String description;

    @Column(unique = true)
    private String link;

    private String imageUrl;

    private LocalDateTime publishedDate;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getPublishedDate() {
        return this.publishedDate;
    }

    public void setPublishedDate(LocalDateTime localDateTime) {
        this.publishedDate = publishedDate;
    }
}
