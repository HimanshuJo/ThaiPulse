package com.thaipulse.newsapp.controller;

import com.thaipulse.newsapp.dto.NewsDto;
import com.thaipulse.newsapp.dto.PattayaNewsDto;
import com.thaipulse.newsapp.dto.BangkokNewsDto;
import com.thaipulse.newsapp.service.PattayaNewsScraperService;
import com.thaipulse.newsapp.service.BangkokNewsScraperService;
import com.thaipulse.newsapp.service.RSSFeedService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewsController {

    private final RSSFeedService rssFeedService;

    private final PattayaNewsScraperService pattayaNewsScraperService;

    private final BangkokNewsScraperService bangkokNewsScraperService;

    public NewsController(RSSFeedService rssFeedService, PattayaNewsScraperService pattayaNewsScraperService,
                          BangkokNewsScraperService bangkokNewsScraperService) {
        this.rssFeedService = rssFeedService;
        this.pattayaNewsScraperService = pattayaNewsScraperService;
        this.bangkokNewsScraperService=bangkokNewsScraperService;
    }

    @GetMapping(value = "/news")
    public ResponseEntity<?> getAllNews(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "20") int size) {
        if (size < 1) {
            size = 20;
        }
        long totalNews = rssFeedService.countAllNews();
        if (totalNews > 1000) {
            Page<NewsDto> pagedResult = rssFeedService.getPaginatedNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<NewsDto> allNews = rssFeedService.getPaginatedNews(0, (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/pattaya-news")
    public ResponseEntity<?> getAllPattayaNews(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "20") int size) {

        if (size < 1) {
            size = 20;
        }

        long totalNews = pattayaNewsScraperService.countAllPattyaNews();
        if (totalNews > 1000) {
            Page<PattayaNewsDto> pagedResult = pattayaNewsScraperService.getPaginatedPattayaNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<PattayaNewsDto> allNews =
                    pattayaNewsScraperService.getPaginatedPattayaNews(0, (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value="/bangkok-news")
    public ResponseEntity<?> getAllBangkokNews(@RequestParam(defaultValue="0") int page,
                                               @RequestParam(defaultValue="20") int size){

        if(size<1){
            size=20;
        }
        long totalNews=bangkokNewsScraperService.countAllBangkokNews();
        if(totalNews>1000){
            Page<BangkokNewsDto>pagedResult=bangkokNewsScraperService.getPaginatedBangkokNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else{
            List<BangkokNewsDto>allNews=bangkokNewsScraperService.getPaginatedBangkokNews(0, (int)totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }
}
