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

    private final WeddingBoutiquePhuketRssFeedService weddingBoutiquePhuketRssFeedService;

    private final BicycleThailandRssFeedService bicycleThailandRssFeedService;

    private final ThaiCapitalistRssFeedService thaiCapitalistRssFeedService;

    private final AboutThailandLivingRssFeedService aboutThailandLivingRssFeedService;

    private final DaveTheRavesThailandRssFeedService daveTheRavesThailandRssFeedService;

    private final ThaifoodmasterRssFeedService thaifoodmasterRssFeedService;

    private final ThailandBailRssFeedService thailandBailRssFeedService;

    private final TheSilomerRssFeedService theSilomerRssFeedService;

    private final PattayaPIRssFeedService pattayaPIRssFeedService;

    private final BudgetCatcherRssFeedService budgetCatcherRssFeedService;

    private final ThaiLawyersRssFeedService thaiLawyersRssFeedService;

    private final MeanderingTalesRssFeedService meanderingTalesRssFeedService;

    private final LifestyleInThailandRssFeedService lifestyleInThailandRssFeedService;

    private final ThatBangkokLifeRssFeedService thatBangkokLifeRssFeedService;

    private final ThinglishLifestyleRssFeedService thinglishLifestyleRssFeedService;

    private final FashionGalleriaRssFeedService fashionGalleriaRssFeedService;

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
                          WeddingBoutiquePhuketRssFeedService weddingBoutiquePhuketRssFeedService,
                          BicycleThailandRssFeedService bicycleThailandRssFeedService,
                          ThaiCapitalistRssFeedService thaiCapitalistRssFeedService,
                          AboutThailandLivingRssFeedService aboutThailandLivingRssFeedService,
                          DaveTheRavesThailandRssFeedService daveTheRavesThailandRssFeedService,
                          ThaifoodmasterRssFeedService thaifoodmasterRssFeedService,
                          ThailandBailRssFeedService thailandBailRssFeedService,
                          TheSilomerRssFeedService theSilomerRssFeedService,
                          PattayaPIRssFeedService pattayaPIRssFeedService,
                          BudgetCatcherRssFeedService budgetCatcherRssFeedService,
                          ThaiLawyersRssFeedService thaiLawyersRssFeedService,
                          MeanderingTalesRssFeedService meanderingTalesRssFeedService,
                          LifestyleInThailandRssFeedService lifestyleInThailandRssFeedService,
                          ThatBangkokLifeRssFeedService thatBangkokLifeRssFeedService,
                          ThinglishLifestyleRssFeedService thinglishLifestyleRssFeedService,
                          FashionGalleriaRssFeedService fashionGalleriaRssFeedService,
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
        this.bicycleThailandRssFeedService = bicycleThailandRssFeedService;
        this.thaiCapitalistRssFeedService = thaiCapitalistRssFeedService;
        this.aboutThailandLivingRssFeedService = aboutThailandLivingRssFeedService;
        this.daveTheRavesThailandRssFeedService = daveTheRavesThailandRssFeedService;
        this.thaifoodmasterRssFeedService = thaifoodmasterRssFeedService;
        this.thailandBailRssFeedService = thailandBailRssFeedService;
        this.theSilomerRssFeedService = theSilomerRssFeedService;
        this.pattayaPIRssFeedService = pattayaPIRssFeedService;
        this.budgetCatcherRssFeedService = budgetCatcherRssFeedService;
        this.thaiLawyersRssFeedService = thaiLawyersRssFeedService;
        this.meanderingTalesRssFeedService = meanderingTalesRssFeedService;
        this.lifestyleInThailandRssFeedService = lifestyleInThailandRssFeedService;
        this.thatBangkokLifeRssFeedService = thatBangkokLifeRssFeedService;
        this.thinglishLifestyleRssFeedService = thinglishLifestyleRssFeedService;
        this.fashionGalleriaRssFeedService = fashionGalleriaRssFeedService;
        this.weddingBoutiquePhuketRssFeedService = weddingBoutiquePhuketRssFeedService;
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
        boolean areNewsAvailable = rssFeedService.newsCheck();
        if (!areNewsAvailable) {
            rssFeedService.fetchAndStoreLatestNews();
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
        boolean areNewsAvailable = bangkokScoopRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            bangkokScoopRssFeedService.fetchAndStoreLatestNews();
        }
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
        boolean areNewsAvailable = thailandIslandNewsRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            thailandIslandNewsRssFeedService.fetchAndStoreLatestNews();
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
        boolean areNewsAvailable = findThaiPropertyRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            findThaiPropertyRssFeedService.fetchAndStoreLatestNews();
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
        boolean areNewsAvailable = legallyMarriedInThailandRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            legallyMarriedInThailandRssFeedService.fetchAndStoreLatestNews();
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
        boolean areNewsAvailable = thaiLadyDateFinderRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            thaiLadyDateFinderRssFeedService.fetchAndStoreLatestNews();
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

    @GetMapping(value = "/weddingBoutiquePhuket")
    public ResponseEntity<?> getWeddingBoutiquePhuketNews(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "20") int size) {
        if (size < 1) {
            size = 20;
        }
        boolean areNewsAvailable = weddingBoutiquePhuketRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            weddingBoutiquePhuketRssFeedService.fetchAndStoreLatestNews();
        }
        long totalNews = weddingBoutiquePhuketRssFeedService.countAllNews();
        if (totalNews > 1000) {
            Page<WeddingBoutiquePhuketNewsDto> pagedResult =
                    weddingBoutiquePhuketRssFeedService.getPaginatedNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<WeddingBoutiquePhuketNewsDto> allNews = weddingBoutiquePhuketRssFeedService.getPaginatedNews(0,
                    (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/bicycleThailand")
    public ResponseEntity<?> getBicycleThailandNews(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "20") int size) {
        if (size < 1) {
            size = 20;
        }
        boolean areNewsAvailable = bicycleThailandRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            bicycleThailandRssFeedService.fetchAndStoreLatestNews();
        }
        long totalNews = bicycleThailandRssFeedService.countAllNews();
        if (totalNews > 1000) {
            Page<BicycleThailandNewsDto> pagedResult = bicycleThailandRssFeedService.getPaginatedNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<BicycleThailandNewsDto> allNews =
                    bicycleThailandRssFeedService.getPaginatedNews(0, (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/thaiCapitalist")
    public ResponseEntity<?> getThaiCapitalistNews(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "20") int size) {
        if (size < 1) {
            size = 20;
        }
        boolean areNewsAvailable = thaiCapitalistRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            thaiCapitalistRssFeedService.fetchAndStoreLatestNews();
        }
        long totalNews = thaiCapitalistRssFeedService.countAllNews();
        if (totalNews > 1000) {
            Page<ThaiCapitalistNewsDto> pagedResult = thaiCapitalistRssFeedService.getPaginatedNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<ThaiCapitalistNewsDto> allNews =
                    thaiCapitalistRssFeedService.getPaginatedNews(0, (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/aboutThailandLiving")
    public ResponseEntity<?> getAboutThailandLivingNews(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "20") int size) {
        if (size < 1) {
            size = 20;
        }
        boolean areNewsAvailable = aboutThailandLivingRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            aboutThailandLivingRssFeedService.fetchAndStoreLatestNews();
        }
        long totalNews = aboutThailandLivingRssFeedService.countAllNews();
        if (totalNews > 1000) {
            Page<AboutThailandLivingNewsDto> pagedResult = aboutThailandLivingRssFeedService.getPaginatedNews(page,
                    size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<AboutThailandLivingNewsDto> allNews = aboutThailandLivingRssFeedService.getPaginatedNews(0,
                    (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/daveTheRavesThailand")
    public ResponseEntity<?> getDaveTheRavesThailandNews(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "20") int size) {
        if (size < 1) {
            size = 20;
        }
        boolean areNewsAvailable = daveTheRavesThailandRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            daveTheRavesThailandRssFeedService.fetchAndStoreLatestNews();
        }
        long totalNews = daveTheRavesThailandRssFeedService.countAllNews();
        if (totalNews > 1000) {
            Page<DaveTheRavesThailandNewsDto> pagedResult = daveTheRavesThailandRssFeedService.getPaginatedNews(page,
                    size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<DaveTheRavesThailandNewsDto> allNews = daveTheRavesThailandRssFeedService.getPaginatedNews(0,
                    (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/thaiFoodMaster")
    public ResponseEntity<?> getThaifoodmasterNews(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "20") int size) {
        if (size < 1) {
            size = 20;
        }
        boolean areNewsAvailable = thaifoodmasterRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            thaifoodmasterRssFeedService.fetchAndStoreLatestNews();
        }
        long totalNews = thaifoodmasterRssFeedService.countAllNews();
        if (totalNews > 1000) {
            Page<ThaifoodmasterNewsDto> pagedResult = thaifoodmasterRssFeedService.getPaginatedNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<ThaifoodmasterNewsDto> allNews =
                    thaifoodmasterRssFeedService.getPaginatedNews(0, (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/thailandBail")
    public ResponseEntity<?> getThailandBailNews(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "20") int size) {
        if (size < 1) {
            size = 20;
        }
        boolean areNewsAvailable = thailandBailRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            thailandBailRssFeedService.fetchAndStoreLatestNews();
        }
        long totalNews = thailandBailRssFeedService.countAllNews();
        if (totalNews > 1000) {
            Page<ThailandBailNewsDto> pagedResult = thailandBailRssFeedService.getPaginatedNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<ThailandBailNewsDto> allNews =
                    thailandBailRssFeedService.getPaginatedNews(0, (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/theSilomer")
    public ResponseEntity<?> getTheSilomerNews(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "20") int size) {
        if (size < 1) {
            size = 20;
        }
        boolean areNewsAvailable = theSilomerRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            theSilomerRssFeedService.fetchAndStoreLatestNews();
        }
        long totalNews = theSilomerRssFeedService.countAllNews();
        if (totalNews > 1000) {
            Page<TheSilomerNewsDto> pagedResult = theSilomerRssFeedService.getPaginatedNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<TheSilomerNewsDto> allNews =
                    theSilomerRssFeedService.getPaginatedNews(0, (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/pattayaPI")
    public ResponseEntity<?> getPattayaPINews(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "20") int size) {
        if (size < 1) {
            size = 20;
        }
        boolean areNewsAvailable = pattayaPIRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            pattayaPIRssFeedService.fetchAndStoreLatestNews();
        }
        long totalNews = pattayaPIRssFeedService.countAllNews();
        if (totalNews > 1000) {
            Page<PattayaPINewsDto> pagedResult = pattayaPIRssFeedService.getPaginatedNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<PattayaPINewsDto> allNews = pattayaPIRssFeedService.getPaginatedNews(0, (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/budgetCatcher")
    public ResponseEntity<?> getBudgetCatcherNews(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "20") int size) {
        if (size < 1) {
            size = 20;
        }
        boolean areNewsAvailable = budgetCatcherRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            budgetCatcherRssFeedService.fetchAndStoreLatestNews();
        }
        long totalNews = budgetCatcherRssFeedService.countAllNews();
        if (totalNews > 1000) {
            Page<BudgetCatcherNewsDto> pagedResult = budgetCatcherRssFeedService.getPaginatedNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<BudgetCatcherNewsDto> allNews =
                    budgetCatcherRssFeedService.getPaginatedNews(0, (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/thaiLawyers")
    public ResponseEntity<?> getThaiLawyersNews(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "20") int size) {
        if (size < 1) {
            size = 20;
        }
        boolean areNewsAvailable = thaiLawyersRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            thaiLawyersRssFeedService.fetchAndStoreLatestNews();
        }
        long totalNews = thaiLawyersRssFeedService.countAllNews();
        if (totalNews > 1000) {
            Page<ThaiLawyersNewsDto> pagedResult = thaiLawyersRssFeedService.getPaginatedNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<ThaiLawyersNewsDto> allNews =
                    thaiLawyersRssFeedService.getPaginatedNews(0, (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/meanderingTales")
    public ResponseEntity<?> getMeanderingTalesNews(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "20") int size) {
        if (size < 1) {
            size = 20;
        }
        boolean areNewsAvailable = meanderingTalesRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            meanderingTalesRssFeedService.fetchAndStoreLatestNews();
        }
        long totalNews = meanderingTalesRssFeedService.countAllNews();
        if (totalNews > 1000) {
            Page<MeanderingTalesNewsDto> pagedResult = meanderingTalesRssFeedService.getPaginatedNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<MeanderingTalesNewsDto> allNews =
                    meanderingTalesRssFeedService.getPaginatedNews(0, (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/lifestyleInThailand")
    public ResponseEntity<?> getLifestyleInThailandNews(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "20") int size) {
        if (size < 1) {
            size = 20;
        }
        boolean areNewsAvailable = lifestyleInThailandRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            lifestyleInThailandRssFeedService.fetchAndStoreLatestNews();
        }
        long totalNews = lifestyleInThailandRssFeedService.countAllNews();
        if (totalNews > 1000) {
            Page<LifestyleInThailandNewsDto> pagedResult = lifestyleInThailandRssFeedService.getPaginatedNews(page,
                    size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<LifestyleInThailandNewsDto> allNews = lifestyleInThailandRssFeedService.getPaginatedNews(0,
                    (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/thatBangkokLife")
    public ResponseEntity<?> getThatBangkokLifeNews(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "20") int size) {
        if (size < 1) {
            size = 20;
        }
        boolean areNewsAvailable = thatBangkokLifeRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            thatBangkokLifeRssFeedService.fetchAndStoreLatestNews();
        }
        long totalNews = thatBangkokLifeRssFeedService.countAllNews();
        if (totalNews > 1000) {
            Page<ThatBangkokLifeNewsDto> pagedResult = thatBangkokLifeRssFeedService.getPaginatedNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<ThatBangkokLifeNewsDto> allNews =
                    thatBangkokLifeRssFeedService.getPaginatedNews(0, (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/thinglishLifestyle")
    public ResponseEntity<?> getThinglishLifestyleNews(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "20") int size) {
        if (size < 1) {
            size = 20;
        }
        boolean areNewsAvailable = thinglishLifestyleRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            thinglishLifestyleRssFeedService.fetchAndStoreLatestNews();
        }
        long totalNews = thinglishLifestyleRssFeedService.countAllNews();
        if (totalNews > 1000) {
            Page<ThinglishLifestyleNewsDto> pagedResult = thinglishLifestyleRssFeedService.getPaginatedNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<ThinglishLifestyleNewsDto> allNews = thinglishLifestyleRssFeedService.getPaginatedNews(0,
                    (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }
    }

    @GetMapping(value = "/fashionGalleria")
    public ResponseEntity<?> getFashionGalleriaNews(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "20") int size) {
        if (size < 1) {
            size = 20;
        }
        boolean areNewsAvailable = fashionGalleriaRssFeedService.newsCheck();
        if (!areNewsAvailable) {
            fashionGalleriaRssFeedService.fetchAndStoreLatestNews();
        }
        long totalNews = fashionGalleriaRssFeedService.countAllNews();
        if (totalNews > 1000) {
            Page<FashionGalleriaNewsDto> pagedResult = fashionGalleriaRssFeedService.getPaginatedNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        } else {
            List<FashionGalleriaNewsDto> allNews =
                    fashionGalleriaRssFeedService.getPaginatedNews(0, (int) totalNews).getContent();
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