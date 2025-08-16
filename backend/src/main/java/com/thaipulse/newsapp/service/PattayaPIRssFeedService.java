package com.thaipulse.newsapp.service;

import com.rometools.modules.mediarss.MediaEntryModule;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.thaipulse.newsapp.dto.PattayaPINewsDto;
import com.thaipulse.newsapp.mapper.PattayaPINewsMapper;
import com.thaipulse.newsapp.model.PattayaPINews;
import com.thaipulse.newsapp.repository.PattayaPINewsRepository;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PattayaPIRssFeedService {

    private static final Logger logger = LoggerFactory.getLogger(PattayaPIRssFeedService.class);

    private final PattayaPINewsRepository pattayaPINewsRepository;

    public PattayaPIRssFeedService(PattayaPINewsRepository pattayaPINewsRepository) {
        this.pattayaPINewsRepository = pattayaPINewsRepository;
    }

    public boolean newsCheck() {
        return pattayaPINewsRepository.count() >= 1;
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

    private boolean isImageAccessible(String imageUrl) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(imageUrl).openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            return false;
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

    public List<PattayaPINews> getNewsFromRss(String rssUrl) {
        List<PattayaPINews> newsList = new ArrayList<>();
        try {
            URL url = new URL(rssUrl);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));
            for (SyndEntry entry : feed.getEntries()) {
                PattayaPINews news = new PattayaPINews();
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
                String placeholder = generatePlaceholderImageBase64(news.getTitle());
                if (placeholder != null) {
                    news.setImage(placeholder);
                }
                newsList.add(news);
                logger.info("Added PattayaPINews: " + news.getTitle());
            }
        } catch (IOException | FeedException e) {
            throw new RuntimeException(e);
        }
        return newsList;
    }

    public void fetchAndStoreLatestNews() {
        List<PattayaPINews> fetchedNews = new ArrayList<>();
        fetchedNews.addAll(getNewsFromRss("https://pattayapi.com/blog/rss.xml"));
        Collections.shuffle(fetchedNews);
        List<PattayaPINews> uniqueNews = fetchedNews;
        if (newsCheck()) {
            uniqueNews = fetchedNews.stream()
                    .filter(news -> !pattayaPINewsRepository.existsByLink(news.getLink()))
                    .toList();
        }
        long count = pattayaPINewsRepository.count();
        if (count < 10000) {
            if (!uniqueNews.isEmpty()) {
                for (PattayaPINews news : uniqueNews) {
                    try {
                        pattayaPINewsRepository.save(news);
                    } catch (DataIntegrityViolationException dive) {
                        logger.info("Duplicate news skipped: {} " + news.getLink());
                    }
                }
            }
        } else {
            pattayaPINewsRepository.deleteAllInBatch();
            for (PattayaPINews news : uniqueNews) {
                try {
                    pattayaPINewsRepository.save(news);
                } catch (DataIntegrityViolationException dive) {
                    logger.info("Duplicate news skipped: {} " + news.getLink());
                }
            }
        }
    }

    public long countAllNews() {
        return pattayaPINewsRepository.count();
    }

    public Page<PattayaPINewsDto> getPaginatedNews(int page, int size) {
        if (size < 1) size = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<PattayaPINews> newsPage = pattayaPINewsRepository.findAll(pageable);
        List<PattayaPINewsDto> newsDtos = newsPage.getContent().stream()
                .map(PattayaPINewsMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(newsDtos, pageable, newsPage.getTotalElements());
    }

}