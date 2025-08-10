package com.thaipulse.newsapp.service;

import com.thaipulse.newsapp.dto.PattayaNewsDto;
import com.thaipulse.newsapp.mapper.PattayaNewsMapper;
import com.thaipulse.newsapp.model.PattayaNews;
import com.thaipulse.newsapp.repository.PattayaNewsRepository;
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
public class PattayaNewsScraperService {

    private static final String BASE_URL = "https://thepattayanews.com/";
    private final PattayaNewsRepository pattayaNewsRepository;

    public PattayaNewsScraperService(PattayaNewsRepository pattayaNewsRepository) {
        this.pattayaNewsRepository = pattayaNewsRepository;
    }

    @Transactional
    public void fetchAndStoreLatestNews() {
        try {
            pattayaNewsRepository.deleteAll();
            System.out.println("Cleared table: " + "Pattaya repo");
            Thread.sleep(2000);
            Document doc = Jsoup.connect(BASE_URL).userAgent("Mozilla").get();
            Elements articles = doc.select("div.td-module-thumb a");

            for (Element articleLink : articles) {
                String link = articleLink.absUrl("href");
                String title = articleLink.attr("title");

                Optional<PattayaNews> existing = pattayaNewsRepository.findByLink(link);
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
                    if (p != null) description = p.text();

                    Element time = articlePage.selectFirst("time.entry-date");
                    if (time != null && time.hasAttr("datetime")) {
                        ZonedDateTime zdt = ZonedDateTime.parse(time.attr("datetime"));
                        pubDate = zdt.toLocalDateTime();
                    }
                } catch (Exception innerEx) {
                    System.err.println("Failed to scrape article page: " + link);
                }

                PattayaNews news = new PattayaNews();
                news.setTitle(title);
                news.setDescription(description);
                news.setLink(link);
                news.setImageUrl(imageUrl);
                news.setPublishedDate(pubDate);
                pattayaNewsRepository.save(news);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Scraping failed: " + e.getMessage());
        }
    }

    public Page<PattayaNewsDto> getPaginatedPattayaNews(int page, int size) {
        if (size < 1) {
            size = 20;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<PattayaNews> newsPage = pattayaNewsRepository.findAll(pageable);
        List<PattayaNewsDto> newsDtos = newsPage.getContent().stream()
                .map(PattayaNewsMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(newsDtos, pageable, newsPage.getTotalElements());
    }

    public long countAllPattyaNews() {
        return pattayaNewsRepository.count();
    }
}
