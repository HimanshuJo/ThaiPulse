package com.thaipulse.newsapp.service;

import com.thaipulse.newsapp.dto.PhuketNewsDto;
import com.thaipulse.newsapp.mapper.PhuketNewsMapper;
import com.thaipulse.newsapp.model.PhuketNews;
import com.thaipulse.newsapp.repository.PhuketNewsRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhuketNewsScraperService {

    private static final String BASE_URL = "https://thethaiger.com/news/phuket";
    private final PhuketNewsRepository phuketNewsRepository;

    public PhuketNewsScraperService(PhuketNewsRepository phuketNewsRepository) {
        this.phuketNewsRepository = phuketNewsRepository;
    }

    @Transactional
    public void fetchAndStoreLatestNews() {
        try {
            phuketNewsRepository.deleteAll();
            System.out.println("Cleared table: " + "Phuket Repo");
            Thread.sleep(2000);
            Document doc = Jsoup.connect(BASE_URL).userAgent("Mozilla").get();
            Elements articles = doc.select("div.td-module-thumb a");
            for (Element articleLink : articles) {
                String link = articleLink.absUrl("href");
                String title = articleLink.attr("title");
                Optional<PhuketNews> existing = phuketNewsRepository.findByLink(link);
                if (existing.isPresent()) continue;
                String imageUrl = "";
                Element img = articleLink.selectFirst("img");
                if (img != null) {
                    imageUrl = img.hasAttr("data-img-url") ? img.attr("data-img-url") : img.attr("src");
                }
                String description = "";
                LocalDateTime pubDate = LocalDateTime.now();
                try {
                    Document articlePage = Jsoup.connect(link).userAgent("Mozilla").get();
                    Element p = articlePage.selectFirst("div.td-post-content p");
                    if (p != null) {
                        description = p.text();
                    }
                    Element time = articlePage.selectFirst("time.entry-date");
                    if (time != null && time.hasAttr("datetime")) {
                        ZonedDateTime zdt = ZonedDateTime.parse(time.attr("datetime"));
                        pubDate = zdt.toLocalDateTime();
                    }
                } catch (Exception e) {
                    System.err.println("Failed to scrape article page: " + link);
                }
                PhuketNews news = new PhuketNews();
                news.setTitle(title);
                news.setDescription(description);
                news.setLink(link);
                news.setImageUrl(imageUrl);
                news.setPublishedDate(pubDate);
                phuketNewsRepository.save(news);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Page<PhuketNewsDto> getPaginatedPhuketNews(int page, int size) {
        if (size < 1) {
            size = 20;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<PhuketNews> newsPage = phuketNewsRepository.findAll(pageable);
        List<PhuketNewsDto> newsDto = newsPage.getContent().stream()
                .map(PhuketNewsMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(newsDto, pageable, newsPage.getTotalElements());
    }

    public long countAllPhuketNews() {
        return phuketNewsRepository.count();
    }
}
