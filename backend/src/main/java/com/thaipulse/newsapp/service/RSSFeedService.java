package com.thaipulse.newsapp.service;

import com.rometools.modules.mediarss.MediaEntryModule;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.thaipulse.newsapp.dto.NewsDto;
import com.thaipulse.newsapp.mapper.NewsMapper;
import com.thaipulse.newsapp.model.News;
import com.thaipulse.newsapp.repository.NewsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class RSSFeedService {

    private static final Logger logger = LoggerFactory.getLogger(RSSFeedService.class);

    private final NewsRepository newsRepository;

    public RSSFeedService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public boolean newsCheck() {
        return newsRepository.count() >= 1;
    }

    private String extractImageFromHtml(String html) {
        if (html == null) return null;
        Matcher matcher = Pattern.compile("<img[^>]+src=[\"']([^\"']+)[\"']").matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public List<News> getNewsFromRss(String rssUrl) {
        List<News> newsList = new ArrayList<>();
        try {
            URL url = new URL(rssUrl);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));
            for (SyndEntry entry : feed.getEntries()) {
                News news = new News();
                news.setTitle(entry.getTitle());
                news.setSource(feed.getTitle());
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
                if (!imageSet) continue;
                newsList.add(news);
                if (newsList.size() >= 50) break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsList;
    }

    public void fetchAndStoreLatestNews() {
        List<News> fetchedNews = new ArrayList<>();
        fetchedNews.addAll(getNewsFromRss("https://thediplomat.com/feed"));
        fetchedNews.addAll(getNewsFromRss("https://asiatimes.com/feed"));
        fetchedNews.addAll(getNewsFromRss("https://southeastasiaglobe.com/feed"));
        fetchedNews.addAll(getNewsFromRss("https://www.asianscientist.com/feed/?x=1"));
        fetchedNews.addAll(getNewsFromRss("https://www.newmandala.org/feed/"));
        fetchedNews.addAll(getNewsFromRss("https://www.asiasentinel.com/feed"));
        fetchedNews.addAll(getNewsFromRss("https://www.scmp.com/rss/91/feed/"));
        fetchedNews.addAll(getNewsFromRss("https://newsnblogs.com/feed/"));
        fetchedNews.addAll(getNewsFromRss("https://hrmasia.com/feed/"));
        fetchedNews.addAll(getNewsFromRss("https://nwasianweekly.com/feed/"));
        fetchedNews.addAll(getNewsFromRss("https://asiasamachar.com/feed/"));
        fetchedNews.addAll(getNewsFromRss("https://www.iaasiaonline.com/feed/"));
        fetchedNews.addAll(getNewsFromRss("https://asia361.com/feed/"));
        fetchedNews.addAll(getNewsFromRss("https://www.retailnews.asia/feed/"));
        fetchedNews.addAll(getNewsFromRss("https://www.asianexpress.co.uk/feed/"));
        fetchedNews.addAll(getNewsFromRss("https://southeastasiaglobe.com/feed/"));
        fetchedNews.addAll(getNewsFromRss("https://tyre-asia.com/feed"));
        fetchedNews.addAll(getNewsFromRss("https://jamestown.org/feed/"));
        Collections.shuffle(fetchedNews);
        List<News> uniqueNews = fetchedNews;
        if (newsCheck()) {
            uniqueNews = fetchedNews.stream()
                    .filter(news -> !newsRepository.existsByLink(news.getLink()))
                    .toList();
        }
        long count = newsRepository.count();
        if (count < 10000) {
            if (!uniqueNews.isEmpty()) {
                for (News news : uniqueNews) {
                    try {
                        newsRepository.save(news);
                    } catch (DataIntegrityViolationException dive) {
                        logger.info("Duplicate news skipped: {} " + news.getLink());
                    }
                }
            }
        } else {
            newsRepository.deleteAllInBatch();
            for (News news : uniqueNews) {
                try {
                    newsRepository.save(news);
                } catch (DataIntegrityViolationException dive) {
                    logger.info("Duplicate news skipped: {} " + news.getLink());
                }
            }
        }
    }

    public long countAllNews() {
        return newsRepository.count();
    }

    public Page<NewsDto> getPaginatedNews(int page, int size) {
        if (size < 1) {
            size = 20;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<News> newsPage = newsRepository.findAll(pageable);
        List<NewsDto> newsDtos = newsPage.getContent().stream()
                .map(NewsMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(newsDtos, pageable, newsPage.getTotalElements());
    }

}