package com.thaipulse.newsapp.controller;

import com.thaipulse.newsapp.dto.*;
import com.thaipulse.newsapp.service.*;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewsController {

    private final RSSFeedService rssFeedService;

    private final BangkokScoopRssFeedService bangkokScoopRssFeedService;

    private final ThailandIslandNewsRssFeedService thailandIslandNewsRssFeedService;

    private final BangkokNewsScraperService bangkokNewsScraperService;

    private final PattayaNewsScraperService pattayaNewsScraperService;

    private final PhuketNewsScraperService phuketNewsScraperService;

    public NewsController(RSSFeedService rssFeedService,
                          BangkokScoopRssFeedService bangkokScoopRssFeedService,
                          ThailandIslandNewsRssFeedService thailandIslandNewsRssFeedService,
                          BangkokNewsScraperService bangkokNewsScraperService,
                          PattayaNewsScraperService pattayaNewsScraperService,
                          PhuketNewsScraperService phuketNewsScraperService) {
        this.rssFeedService = rssFeedService;
        this.bangkokScoopRssFeedService = bangkokScoopRssFeedService;
        this.thailandIslandNewsRssFeedService = thailandIslandNewsRssFeedService;
        this.bangkokNewsScraperService = bangkokNewsScraperService;
        this.pattayaNewsScraperService = pattayaNewsScraperService;
        this.phuketNewsScraperService = phuketNewsScraperService;
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

    @GetMapping(value = "/bangkokScoopNews")
    public ResponseEntity<?> getAllBangkokScoopNews(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "20") int size) {
        if (size < 1) size = 20;
        long totalNews = bangkokScoopRssFeedService.countAllNews();
        if (totalNews > 1000) {
            Page<BangkokScoopNewsDto> pagedResult = bangkokScoopRssFeedService.getPaginatedNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<BangkokScoopNewsDto> allNews =
                    bangkokScoopRssFeedService.getPaginatedNews(0, (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/thailandIslandNews")
    public ResponseEntity<?> getAllThailandIslandNews(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "20") int size) {
        if (size < 1) {
            size = 20;
        }
        long totalNews = thailandIslandNewsRssFeedService.countAllNews();
        if (totalNews > 1000) {
            Page<ThailandIslandNewsDto> pagedResult = thailandIslandNewsRssFeedService.getPaginatedNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<ThailandIslandNewsDto> allNews = thailandIslandNewsRssFeedService.getPaginatedNews(0,
                    (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/bangkok-news")
    public ResponseEntity<?> getAllBangkokNews(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "20") int size) throws InterruptedException {

        if (size < 1) {
            size = 20;
        }
        boolean areNewsAvailable=bangkokNewsScraperService.newsCheck();
        if(!areNewsAvailable){
            bangkokNewsScraperService.fetchAndStoreLatestNews();
        }
        long totalNews = bangkokNewsScraperService.countAllBangkokNews();
        if (totalNews > 1000) {
            Page<BangkokNewsDto> pagedResult = bangkokNewsScraperService.getPaginatedBangkokNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<BangkokNewsDto> allNews =
                    bangkokNewsScraperService.getPaginatedBangkokNews(0, (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/pattaya-news")
    public ResponseEntity<?> getAllPattayaNews(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "20") int size) throws InterruptedException {

        if (size < 1) {
            size = 20;
        }
        boolean areNewsAvailable=pattayaNewsScraperService.newsCheck();
        if(!areNewsAvailable){
            pattayaNewsScraperService.fetchAndStoreLatestNews();
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

    @GetMapping(value = "/phuket-news")
    public ResponseEntity<?> getAllPhuketNews(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "20") int size) throws InterruptedException {
        if (size < 1) {
            size = 20;
        }
        boolean areNewsAvailable=phuketNewsScraperService.newsCheck();
        if(!areNewsAvailable){
            phuketNewsScraperService.fetchAndStoreLatestNews();
        }
        long totalNews = phuketNewsScraperService.countAllPhuketNews();
        if (totalNews > 1000) {
            Page<PhuketNewsDto> pagedResult = phuketNewsScraperService.getPaginatedPhuketNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<PhuketNewsDto> allNews =
                    phuketNewsScraperService.getPaginatedPhuketNews(0, (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }
}
