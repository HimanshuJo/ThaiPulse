import os

# Directory where the generated Java files will be stored
output_dir = "./generated_services"

# Ensure output directory exists
os.makedirs(output_dir, exist_ok=True)

# Base template for service class
template = """package com.thaipulse.newsapp.service;

import com.rometools.modules.mediarss.MediaEntryModule;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.rometools.rome.feed.module.Module;
import com.thaipulse.newsapp.dto.{DTO_NAME};
import com.thaipulse.newsapp.mapper.{MAPPER_NAME};
import com.thaipulse.newsapp.model.{MODEL_NAME};
import com.thaipulse.newsapp.repository.{REPO_NAME};
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class {CLASS_NAME} {

    private static final Logger logger = LoggerFactory.getLogger({CLASS_NAME}.class);

    private final {REPO_NAME} {REPO_VAR};

    public {CLASS_NAME}({REPO_NAME} {REPO_VAR}) {
        this.{REPO_VAR} = {REPO_VAR};
    }

    private String extractImageFromHtml(String html) {
        if (html == null) return null;
        Matcher matcher = Pattern.compile("<img[^>]+src=[\\"']([^\\\"']+)[\\\"']").matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public List<{MODEL_NAME}> getNewsFromRss(String rssUrl) {
        List<{MODEL_NAME}> newsList = new ArrayList<>();
        try {
            URL url = new URL(rssUrl);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));
            for (SyndEntry entry : feed.getEntries()) {
                {MODEL_NAME} news = new {MODEL_NAME}();
                news.setTitle(entry.getTitle());
                news.setSource(entry.getTitle());
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
                logger.info("Added {MODEL_NAME}: " + news.getTitle());
            }
        } catch (IOException | FeedException e) {
            throw new RuntimeException(e);
        }
        return newsList;
    }

    public Page<{DTO_NAME}> getPaginatedNews(int page, int size) {
        if (size < 1) size = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<{MODEL_NAME}> newsPage = {REPO_VAR}.findAll(pageable);
        List<{DTO_NAME}> newsDtos = newsPage.getContent().stream()
                .map({MAPPER_NAME}::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(newsDtos, pageable, newsPage.getTotalElements());
    }

    public long countAllNews() {
        return {REPO_VAR}.count();
    }

}
"""

# Services to generate
services = [
    "ThaiLadyDateFinder",
    "FindThaiProperty",
    "LegallyMarriedInThailand",
    "WeddingBoutiquePhuket",
    "TrailheadThailand"
]

# Generate each file
for service in services:
    class_name = f"{service}RssFeedService"
    model_name = f"{service}News"
    dto_name = f"{service}NewsDto"
    mapper_name = f"{service}NewsMapper"
    repo_name = f"{service}NewsRepository"
    repo_var = service[0].lower() + service[1:] + "NewsRepository"

    java_code = template.replace("{CLASS_NAME}", class_name) \
        .replace("{MODEL_NAME}", model_name) \
        .replace("{DTO_NAME}", dto_name) \
        .replace("{MAPPER_NAME}", mapper_name) \
        .replace("{REPO_NAME}", repo_name) \
        .replace("{REPO_VAR}", repo_var)

    file_path = os.path.join(output_dir, f"{class_name}.java")
    with open(file_path, "w", encoding="utf-8") as f:
        f.write(java_code)

    print(f"Generated: {file_path}")

print("\nAll service classes generated successfully.")
