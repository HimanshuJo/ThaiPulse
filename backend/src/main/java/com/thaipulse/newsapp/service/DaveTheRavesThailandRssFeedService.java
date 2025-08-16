package com.thaipulse.newsapp.service;

import com.rometools.modules.mediarss.MediaEntryModule;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.thaipulse.newsapp.dto.DaveTheRavesThailandNewsDto;
import com.thaipulse.newsapp.mapper.DaveTheRavesThailandNewsMapper;
import com.thaipulse.newsapp.model.DaveTheRavesThailandNews;
import com.thaipulse.newsapp.repository.DaveTheRavesThailandNewsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class DaveTheRavesThailandRssFeedService {

    private static final Logger logger = LoggerFactory.getLogger(DaveTheRavesThailandRssFeedService.class);

    private final DaveTheRavesThailandNewsRepository daveTheRavesThailandNewsRepository;

    public DaveTheRavesThailandRssFeedService(DaveTheRavesThailandNewsRepository daveTheRavesThailandNewsRepository) {
        this.daveTheRavesThailandNewsRepository = daveTheRavesThailandNewsRepository;
    }

    public boolean newsCheck() {
        return daveTheRavesThailandNewsRepository.count() >= 1;
    }

    private String generatePlaceholderImageBase64(String title) {
        try {
            int width = 600, height = 300;
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bufferedImage.createGraphics();

            g2d.setColor(new Color(50, 50, 50));
            g2d.fillRect(0, 0, width, height);

            g2d.setFont(new Font("Arial", Font.BOLD, 28));
            g2d.setColor(Color.WHITE);

            String text = title.length() > 40 ? title.substring(0, 40) + "..." : title;
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(text);

            g2d.drawString(text, (width - textWidth) / 2, height / 2);
            g2d.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            byte[] bytes = baos.toByteArray();

            String base64 = Base64.getEncoder().encodeToString(bytes);
            String uniqueId = UUID.randomUUID().toString().substring(0, 8);

            return "data:image/png;base64," + base64 + "#id=" + uniqueId;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String extractImageFromHtml(String html) {
        if (html == null) return null;
        Matcher matcher = Pattern.compile("<img[^>]+src=[\"']([^\"']+)[\"']").matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public List<DaveTheRavesThailandNews> getNewsFromRss(String rssUrl) {
        List<DaveTheRavesThailandNews> newsList = new ArrayList<>();
        try {
            URL url = new URL(rssUrl);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));
            for (SyndEntry entry : feed.getEntries()) {
                DaveTheRavesThailandNews news = new DaveTheRavesThailandNews();
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
                    String placeholder = generatePlaceholderImageBase64(news.getTitle());
                    if (placeholder != null) {
                        news.setImage(placeholder);
                    }
                }
                newsList.add(news);
                logger.info("Added DaveTheRavesThailandNews: " + news.getTitle());
            }
        } catch (IOException | FeedException e) {
            throw new RuntimeException(e);
        }
        return newsList;
    }

    public void fetchAndStoreLatestNews() {
        List<DaveTheRavesThailandNews> fetchedNews = new ArrayList<>();
        fetchedNews.addAll(getNewsFromRss("https://davetheravebangkok.com/feed/"));
        Collections.shuffle(fetchedNews);
        List<DaveTheRavesThailandNews> uniqueNews = fetchedNews;
        if (newsCheck()) {
            uniqueNews = fetchedNews.stream()
                    .filter(news -> !daveTheRavesThailandNewsRepository.existsByLink(news.getLink()))
                    .toList();
        }
        long count = daveTheRavesThailandNewsRepository.count();
        if (count < 10000) {
            if (!uniqueNews.isEmpty()) {
                for (DaveTheRavesThailandNews news : uniqueNews) {
                    try {
                        daveTheRavesThailandNewsRepository.save(news);
                    } catch (DataIntegrityViolationException dive) {
                        logger.info("Duplicate news skipped: {} " + news.getLink());
                    }
                }
            }
        } else {
            daveTheRavesThailandNewsRepository.deleteAllInBatch();
            for (DaveTheRavesThailandNews news : uniqueNews) {
                try {
                    daveTheRavesThailandNewsRepository.save(news);
                } catch (DataIntegrityViolationException dive) {
                    logger.info("Duplicate news skipped: {} " + news.getLink());
                }
            }
        }
    }

    public long countAllNews() {
        return daveTheRavesThailandNewsRepository.count();
    }

    public Page<DaveTheRavesThailandNewsDto> getPaginatedNews(int page, int size) {
        if (size < 1) size = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<DaveTheRavesThailandNews> newsPage = daveTheRavesThailandNewsRepository.findAll(pageable);
        List<DaveTheRavesThailandNewsDto> newsDtos = newsPage.getContent().stream()
                .map(DaveTheRavesThailandNewsMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(newsDtos, pageable, newsPage.getTotalElements());
    }

}