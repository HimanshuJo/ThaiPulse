package com.thaipulse.newsapp.service;

import com.rometools.modules.mediarss.MediaEntryModule;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.thaipulse.newsapp.dto.PattayaNewsDto;
import com.thaipulse.newsapp.mapper.PattayaNewsMapper;
import com.thaipulse.newsapp.model.PattayaNews;
import com.thaipulse.newsapp.repository.PattayaNewsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class PattayaNewsRssFeedService {

    private static final Logger logger = LoggerFactory.getLogger(PattayaNewsRssFeedService.class);

    private final PattayaNewsRepository pattayaNewsRepository;

    public PattayaNewsRssFeedService(PattayaNewsRepository pattayaNewsRepository) {
        this.pattayaNewsRepository = pattayaNewsRepository;
    }

    public long countAllPattyaNews() {
        return pattayaNewsRepository.count();
    }

    public boolean newsCheck() {
        return pattayaNewsRepository.count() >= 10;
    }

    @Transactional
    public void fetchAndStoreLatestNews() {

        List<PattayaNews> fetchedNews = new ArrayList<>();
        fetchedNews.addAll(getNewsFromRss("https://rss.app/feeds/oHqaJXd9t6VndNaj.xml"));
        Collections.shuffle(fetchedNews);
        List<PattayaNews> uniqueNews = fetchedNews;
        if (newsCheck()) {
            uniqueNews = fetchedNews.stream()
                    .filter(news -> !pattayaNewsRepository.existsByLink(news.getLink()))
                    .toList();
        }
        long count = pattayaNewsRepository.count();
        if (count < 2000) {
            if (!uniqueNews.isEmpty()) {
                for (PattayaNews news : uniqueNews) {
                    try {
                        pattayaNewsRepository.save(news);
                    } catch (DataIntegrityViolationException dive) {
                        logger.info("Duplicate news skipped: {} " + news.getLink());
                    }
                }
            }
        } else {
            pattayaNewsRepository.deleteAllInBatch();
            for (PattayaNews news : uniqueNews) {
                try {
                    pattayaNewsRepository.save(news);
                } catch (DataIntegrityViolationException dive) {
                    logger.info("Duplicate news skipped: {} " + news.getLink());
                }
            }
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

    private String extractImageFromHtml(String html) {
        if (html == null) return null;
        Matcher matcher = Pattern.compile("<img[^>]+src=[\"']([^\"']+)[\"']").matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public List<PattayaNews> getNewsFromRss(String rssUrl) {
        List<PattayaNews> newsList = new ArrayList<>();
        try {
            URL url = new URL(rssUrl);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));
            for (SyndEntry entry : feed.getEntries()) {
                PattayaNews news = new PattayaNews();
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
                logger.info("Pattaya News Added: " + news.getTitle());
                newsList.add(news);
                if (newsList.size() >= 10) break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsList;
    }

}
