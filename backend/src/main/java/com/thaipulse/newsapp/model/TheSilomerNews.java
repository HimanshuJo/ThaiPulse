package com.thaipulse.newsapp.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "thesilomer_news")
public class TheSilomerNews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String source;

    private String link;

    private String image;

    private LocalDateTime publishedDate;

    public TheSilomerNews() {
    }

    public TheSilomerNews(Long id, String title, String source, String link) {
        this.id = id;
        this.title = title;
        this.source = source;
        this.link = link;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

}