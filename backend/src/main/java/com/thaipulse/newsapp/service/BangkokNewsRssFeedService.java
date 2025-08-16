package com.thaipulse.newsapp.service;

import com.rometools.modules.mediarss.MediaEntryModule;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.SyndFeedInput;
import com.thaipulse.newsapp.dto.BangkokNewsDto;
import com.thaipulse.newsapp.mapper.BangkokNewsMapper;
import com.thaipulse.newsapp.model.BangkokNews;
import com.thaipulse.newsapp.repository.BangkokNewsRepository;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class BangkokNewsRssFeedService {

    private static final Logger logger = LoggerFactory.getLogger(BangkokNewsRssFeedService.class);

    private final BangkokNewsRepository bangkokNewsRepository;

    public BangkokNewsRssFeedService(BangkokNewsRepository bangkokNewsRepository) {
        this.bangkokNewsRepository = bangkokNewsRepository;
    }

    public boolean newsCheck() {
        return bangkokNewsRepository.count() >= 1;
    }

    private String extractImageFromHtml(String html) {
        if (html == null) return null;
        Matcher matcher = Pattern.compile("<img[^>]+src=[\"']([^\"']+)[\"']").matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private SyndFeed readFeed(URL url) {
        SyndFeedInput input = new SyndFeedInput();
        try (InputStream in = url.openStream()) {
            SAXBuilder saxBuilder = new SAXBuilder();

            saxBuilder.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            saxBuilder.setFeature("http://xml.org/sax/features/external-general-entities", false);
            saxBuilder.setFeature("http://xml.org/sax/features/external-parameter-entities", false);

            Document doc = saxBuilder.build(in);
            return input.build(doc);
        } catch (Exception e) {
            logger.error("Failed to read RSS feed from URL: {}", url, e);
            SyndFeed emptyFeed = new SyndFeedImpl();
            emptyFeed.setFeedType("rss_2.0");
            emptyFeed.setTitle("Empty Feed");
            emptyFeed.setLink(url.toString());
            emptyFeed.setDescription("No items found due to parsing error.");
            emptyFeed.setEntries(Collections.emptyList());
            return emptyFeed;
        }
    }

    public List<BangkokNews> getNewsFromRss(String rssUrl) {
        List<BangkokNews> newsList = new ArrayList<>();
        try {
            URL url = new URL(rssUrl);
            SyndFeed feed = readFeed(url);
            if(feed.getTitle().equals("Empty Feed")) return newsList;
            for (SyndEntry entry : feed.getEntries()) {
                BangkokNews news = new BangkokNews();
                news.setTitle(entry.getTitle());
                try {
                    URL articleUrl = new URL(entry.getLink());
                    String host = articleUrl.getHost();
                    if (host.startsWith("www.")) {
                        host = host.substring(4);
                    }
                    String mainPart = host.split("\\.")[0];
                    String formattedSource = Arrays.stream(mainPart.split("(?=[A-Z])|(?<=\\D)(?=\\d)|(?<=\\D)(?=News)" +
                                    "|(?<=news)(?=[A-Z])|(?<=\\p{Lower})(?=\\p{Upper})|(?<=\\p{Lower})(?=\\d)|" +
                                    "(?<=\\p{Lower})(?=\\p{Upper})"))
                            .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                            .collect(Collectors.joining(" "));

                    if (formattedSource.equals(mainPart)) {
                        formattedSource = Arrays.stream(mainPart.split("(?<=news)|(?<=daily)|(?<=post)|(?<=times)|" +
                                        "(?<=review)|(?<=mail)"))
                                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                                .collect(Collectors.joining(" "));
                    }

                    news.setSource(formattedSource.trim());
                } catch (MalformedURLException e) {
                    news.setSource("Unknown");
                }
                news.setLink(entry.getLink());

                boolean imageSet = false;
                if (entry.getModules() != null) {
                    for (Module module : entry.getModules()) {
                        if (module instanceof MediaEntryModule mediaModule) {
                            if (mediaModule.getMediaContents() != null && mediaModule.getMediaContents().length > 0) {
                                news.setImage(mediaModule.getMediaContents()[0].getReference().toString());
                                imageSet = true;
                                break;
                            }
                        }
                    }
                }

                if (!imageSet && entry.getDescription() != null) {
                    String desc = entry.getDescription().getValue();
                    String imageUrl = extractImageFromHtml(desc);
                    if (imageUrl != null) {
                        news.setImage(imageUrl);
                        imageSet = true;
                    }
                }

                if (!imageSet && entry.getContents() != null) {
                    for (SyndContent content : entry.getContents()) {
                        String html = content.getValue();
                        String imageUrl = extractImageFromHtml(html);
                        if (imageUrl != null) {
                            news.setImage(imageUrl);
                            imageSet = true;
                            break;
                        }
                    }
                }
                if (!imageSet) {
                    continue;
                }
                logger.info("Bangkok News Added: " + news.getTitle());
                newsList.add(news);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsList;
    }

    public void fetchAndStoreLatestNews() {
        List<BangkokNews> fetchedNews = new ArrayList<>();
        fetchedNews.addAll(getNewsFromRss("https://rss.app/feeds/tIKepB4ZE1Wx3DF3.xml"));
        Collections.shuffle(fetchedNews);
        List<BangkokNews> uniqueNews = fetchedNews;
        if (newsCheck()) {
            uniqueNews = fetchedNews.stream()
                    .filter(news -> !bangkokNewsRepository.existsByLink(news.getLink()))
                    .toList();
        }
        long count = bangkokNewsRepository.count();
        if (count < 10000) {
            if (!uniqueNews.isEmpty()) {
                for (BangkokNews news : uniqueNews) {
                    try {
                        bangkokNewsRepository.save(news);
                    } catch (DataIntegrityViolationException dive) {
                        logger.info("Duplicate news skipped: {} " + news.getLink());
                    }
                }
            }
        } else {
            bangkokNewsRepository.deleteAllInBatch();
            for (BangkokNews news : uniqueNews) {
                try {
                    bangkokNewsRepository.save(news);
                } catch (DataIntegrityViolationException dive) {
                    logger.info("Duplicate news skipped: {} " + news.getLink());
                }
            }
        }
    }

    public long countAllBangkokNews() {
        return bangkokNewsRepository.count();
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

}