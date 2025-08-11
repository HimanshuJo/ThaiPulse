package com.thaipulse.newsapp.service;

import com.thaipulse.newsapp.dto.BangkokNewsDto;
import com.thaipulse.newsapp.mapper.BangkokNewsMapper;
import com.thaipulse.newsapp.model.BangkokNews;
import com.thaipulse.newsapp.repository.BangkokNewsRepository;
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
public class BangkokNewsScraperService {

    private static final String BASE_URL = "https://www.independent.co.uk/topic/bangkok";
    private final BangkokNewsRepository bangkokNewsRepository;

    public BangkokNewsScraperService(BangkokNewsRepository bangkokNewsRepository) {
        this.bangkokNewsRepository = bangkokNewsRepository;
    }

    public boolean newsCheck() {
        return bangkokNewsRepository.count() >= 10;
    }

    @Transactional
    public void fetchAndStoreLatestNews() {
        try {
            bangkokNewsRepository.deleteAll();
            Document doc = Jsoup.connect(BASE_URL).userAgent("Mozilla").get();
            Elements articles = doc.select("div.td-module-thumb a");
            for (Element articleLink : articles) {
                String link = articleLink.absUrl("href");
                String title = articleLink.attr("title");
                Optional<BangkokNews> existing = bangkokNewsRepository.findByLink(link);
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
                } catch (Exception innerEx) {
                    System.err.println("Failed to scrape article page: " + link);
                }
                BangkokNews news = new BangkokNews();
                news.setTitle(title);
                news.setDescription(description);
                news.setLink(link);
                news.setImageUrl(imageUrl);
                news.setPublishedDate(pubDate);
                bangkokNewsRepository.save(news);
                System.out.println("Added Bangkok news " + news.getTitle());
                if (bangkokNewsRepository.count() >= 10) break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Page<BangkokNewsDto> getPaginatedBangkokNews(int page, int size) {
        if (size < 1) {
            size = 20;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<BangkokNews> newsPage = bangkokNewsRepository.findAll(pageable);
        List<BangkokNewsDto> newsDto = newsPage.getContent().stream()
                .map(BangkokNewsMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(newsDto, pageable, newsPage.getTotalElements());
    }

    public long countAllBangkokNews() {
        return bangkokNewsRepository.count();
    }

}
