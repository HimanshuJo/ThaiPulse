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

    private final FindThaiPropertyRssFeedService findThaiPropertyRssFeedService;

    private final LegallyMarriedInThailandRssFeedService legallyMarriedInThailandRssFeedService;

    private final ThaiLadyDateFinderRssFeedService thaiLadyDateFinderRssFeedService;

    private final BangkokNewsRssFeedService bangkokNewsRssFeedService;

    private final PattayaNewsRssFeedService pattayaNewsRssFeedService;

    private final PhuketNewsRssFeedService phuketNewsRssFeedService;

    private final ChiangMaiNewsRssFeedService chiangMaiNewsRssFeedService;

    private final HatYaiNewsRssFeedService hatYaiNewsRssFeedService;

    private final KhonKaenNewsRssFeedService khonKaenNewsRssFeedService;

    private final NakhonRatchasimaNewsRssFeedService nakhonRatchasimaNewsRssFeedService;

    public NewsController(RSSFeedService rssFeedService,
                          BangkokScoopRssFeedService bangkokScoopRssFeedService,
                          ThailandIslandNewsRssFeedService thailandIslandNewsRssFeedService,
                          FindThaiPropertyRssFeedService findThaiPropertyRssFeedService,
                          LegallyMarriedInThailandRssFeedService legallyMarriedInThailandRssFeedService,
                          ThaiLadyDateFinderRssFeedService thaiLadyDateFinderRssFeedService,
                          BangkokNewsRssFeedService bangkokNewsRssFeedService,
                          PattayaNewsRssFeedService pattayaNewsRssFeedService,
                          PhuketNewsRssFeedService phuketNewsRssFeedService,
                          ChiangMaiNewsRssFeedService chiangMaiNewsRssFeedService,
                          HatYaiNewsRssFeedService hatYaiNewsRssFeedService,
                          KhonKaenNewsRssFeedService khonKaenNewsRssFeedService,
                          NakhonRatchasimaNewsRssFeedService nakhonRatchasimaNewsRssFeedService) {
        this.rssFeedService = rssFeedService;
        this.bangkokScoopRssFeedService = bangkokScoopRssFeedService;
        this.thailandIslandNewsRssFeedService = thailandIslandNewsRssFeedService;
        this.findThaiPropertyRssFeedService = findThaiPropertyRssFeedService;
        this.legallyMarriedInThailandRssFeedService = legallyMarriedInThailandRssFeedService;
        this.thaiLadyDateFinderRssFeedService = thaiLadyDateFinderRssFeedService;
        this.bangkokNewsRssFeedService = bangkokNewsRssFeedService;
        this.pattayaNewsRssFeedService = pattayaNewsRssFeedService;
        this.phuketNewsRssFeedService = phuketNewsRssFeedService;
        this.chiangMaiNewsRssFeedService = chiangMaiNewsRssFeedService;
        this.hatYaiNewsRssFeedService = hatYaiNewsRssFeedService;
        this.khonKaenNewsRssFeedService = khonKaenNewsRssFeedService;
        this.nakhonRatchasimaNewsRssFeedService = nakhonRatchasimaNewsRssFeedService;
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

    @GetMapping(value = "/findThaiProperty")
    public ResponseEntity<?> getAllThailandPropertyNews(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "20") int size) {
        if (size < 1) {
            size = 20;
        }
        long totalNews = findThaiPropertyRssFeedService.countAllNews();
        if (totalNews > 1000) {
            Page<FindThaiPropertyNewsDto> pagedResult = findThaiPropertyRssFeedService.getPaginatedNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<FindThaiPropertyNewsDto> allNews = findThaiPropertyRssFeedService.getPaginatedNews(0,
                    (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/legallyMarriedInThailand")
    public ResponseEntity<?> getLegallyMarriedInThailandNews(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "20") int size) {
        if (size < 1) {
            size = 20;
        }
        long totalNews = legallyMarriedInThailandRssFeedService.countAllNews();
        if (totalNews > 1000) {
            Page<LegallyMarriedInThailandNewsDto> pagedResult =
                    legallyMarriedInThailandRssFeedService.getPaginatedNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<LegallyMarriedInThailandNewsDto> allNews = legallyMarriedInThailandRssFeedService.getPaginatedNews(0,
                    (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/thaiLadyDateFinder")
    public ResponseEntity<?> getThaiLadyDateFinderNews(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "20") int size) {
        if (size < 1) {
            size = 20;
        }
        long totalNews = thaiLadyDateFinderRssFeedService.countAllNews();
        if (totalNews > 1000) {
            Page<ThaiLadyDateFinderNewsDto> pagedResult = thaiLadyDateFinderRssFeedService.getPaginatedNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<ThaiLadyDateFinderNewsDto> allNews = thaiLadyDateFinderRssFeedService.getPaginatedNews(0,
                    (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/bangkok-news")
    public ResponseEntity<?> getAllBangkokNews(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "20") int size) {

        if (size < 1) {
            size = 20;
        }
        boolean areNewsAvailable = bangkokNewsRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            bangkokNewsRssFeedService.fetchAndStoreLatestNews();
        }
        long totalNews = bangkokNewsRssFeedService.countAllBangkokNews();
        if (totalNews > 1000) {
            Page<BangkokNewsDto> pagedResult = bangkokNewsRssFeedService.getPaginatedBangkokNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<BangkokNewsDto> allNews =
                    bangkokNewsRssFeedService.getPaginatedBangkokNews(0, (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/pattaya-news")
    public ResponseEntity<?> getAllPattayaNews(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "20") int size) {

        if (size < 1) {
            size = 20;
        }
        boolean areNewsAvailable = pattayaNewsRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            pattayaNewsRssFeedService.fetchAndStoreLatestNews();
        }
        long totalNews = pattayaNewsRssFeedService.countAllPattyaNews();
        if (totalNews > 1000) {
            Page<PattayaNewsDto> pagedResult = pattayaNewsRssFeedService.getPaginatedPattayaNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<PattayaNewsDto> allNews =
                    pattayaNewsRssFeedService.getPaginatedPattayaNews(0, (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/phuket-news")
    public ResponseEntity<?> getAllPhuketNews(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "20") int size) {
        if (size < 1) {
            size = 20;
        }
        boolean areNewsAvailable = phuketNewsRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            phuketNewsRssFeedService.fetchAndStoreLatestNews();
        }
        long totalNews = phuketNewsRssFeedService.countAllPhuketNews();
        if (totalNews > 1000) {
            Page<PhuketNewsDto> pagedResult = phuketNewsRssFeedService.getPaginatedPhuketNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<PhuketNewsDto> allNews =
                    phuketNewsRssFeedService.getPaginatedPhuketNews(0, (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/chiangMai-news")
    public ResponseEntity<?> getAllChiangMaiNews(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "20") int size) {
        if (size < 1) {
            size = 20;
        }
        System.out.println("received the call");
        boolean areNewsAvailable = chiangMaiNewsRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            chiangMaiNewsRssFeedService.fetchAndStoreLatestNews();
        }
        long totalNews = chiangMaiNewsRssFeedService.countAllChiangMaiNews();
        if (totalNews > 1000) {
            Page<ChiangMaiNewsDto> pagedResult = chiangMaiNewsRssFeedService.getPaginatedChiangMaiNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<ChiangMaiNewsDto> allNews =
                    chiangMaiNewsRssFeedService.getPaginatedChiangMaiNews(0, (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/hatYai-news")
    public ResponseEntity<?> getAllHatYaiNews(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "20") int size) {
        if (size < 1) {
            size = 20;
        }
        boolean areNewsAvailable = hatYaiNewsRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            hatYaiNewsRssFeedService.fetchAndStoreLatestNews();
        }
        long totalNews = hatYaiNewsRssFeedService.countAllHatYaiNews();
        if (totalNews > 1000) {
            Page<HatYaiNewsDto> pagedResult = hatYaiNewsRssFeedService.getPaginatedHatYaiNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<HatYaiNewsDto> allNews =
                    hatYaiNewsRssFeedService.getPaginatedHatYaiNews(0, (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/khonKaen-news")
    public ResponseEntity<?> getAllKhonKaenNews(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "20") int size) {
        if (size < 1) {
            size = 20;
        }
        boolean areNewsAvailable = khonKaenNewsRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            khonKaenNewsRssFeedService.fetchAndStoreLatestNews();
        }
        long totalNews = khonKaenNewsRssFeedService.countAllKhonKaenNews();
        if (totalNews > 1000) {
            Page<KhonKaenNewsDto> pagedResult = khonKaenNewsRssFeedService.getPaginatedKhonKaenNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<KhonKaenNewsDto> allNews =
                    khonKaenNewsRssFeedService.getPaginatedKhonKaenNews(0, (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/nakhonRatchasima-news")
    public ResponseEntity<?> getAllNakhonRatchasimaNews(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "20") int size) {
        if (size < 1) {
            size = 20;
        }
        boolean areNewsAvailable = nakhonRatchasimaNewsRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            nakhonRatchasimaNewsRssFeedService.fetchAndStoreLatestNews();
        }
        long totalNews = nakhonRatchasimaNewsRssFeedService.countAllNakhonRatchasimaNews();
        if (totalNews > 1000) {
            Page<NakhonRatchasimaNewsDto> pagedResult =
                    nakhonRatchasimaNewsRssFeedService.getPaginatedNakhonRatchasimaNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<NakhonRatchasimaNewsDto> allNews =
                    nakhonRatchasimaNewsRssFeedService.getPaginatedNakhonRatchasimaNews(0, (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }
}
