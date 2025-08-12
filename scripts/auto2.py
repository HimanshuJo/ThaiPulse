import os

# Directory where the generated scheduler Java files will be stored
output_dir = "./generated_schedulers"
os.makedirs(output_dir, exist_ok=True)

# Scheduler class template
template = """package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.model.{MODEL_NAME};
import com.thaipulse.newsapp.repository.{REPO_NAME};
import com.thaipulse.newsapp.service.{SERVICE_NAME};
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class {SCHEDULER_NAME} {{

    private final {SERVICE_NAME} {SERVICE_VAR};
    private final {REPO_NAME} {REPO_VAR};

    @Scheduled(fixedRate = 500000)
    public void refereshNews() {{
        List<{MODEL_NAME}> fetchedNews = new ArrayList<>({SERVICE_VAR}.getNewsFromRss("{RSS_URL}"));
        Collections.shuffle(fetchedNews);
        List<{MODEL_NAME}> uniqueNews = fetchedNews.stream()
                .filter(news -> !{REPO_VAR}.existsByLink(news.getLink()))
                .toList();

        long count = {REPO_VAR}.count();
        if (count < 2000) {{
            if (!uniqueNews.isEmpty()) {{
                {REPO_VAR}.saveAll(uniqueNews);
            }}
        }} else {{
            {REPO_VAR}.deleteAllInBatch();
            {REPO_VAR}.saveAll(uniqueNews);
        }}
    }}
}}
"""

# RSS feed URLs for each service
rss_feeds = {
    "ThaiLadyDateFinder": "https://example.com/thailadydates/rss",
    "FindThaiProperty": "https://example.com/findthaiproperty/rss",
    "LegallyMarriedInThailand": "https://example.com/legallymarried/rss",
    "WeddingBoutiquePhuket": "https://example.com/weddingboutiquephuket/rss",
    "TrailheadThailand": "https://example.com/trailheadthailand/rss"
}

# Generate scheduler classes
for base_name, rss_url in rss_feeds.items():
    scheduler_name = f"{base_name}NewsScheduler"
    model_name = f"{base_name}News"
    service_name = f"{base_name}RssFeedService"
    repo_name = f"{base_name}NewsRepository"

    service_var = service_name[0].lower() + service_name[1:]
    repo_var = repo_name[0].lower() + repo_name[1:]

    java_code = template.replace("{SCHEDULER_NAME}", scheduler_name) \
        .replace("{MODEL_NAME}", model_name) \
        .replace("{SERVICE_NAME}", service_name) \
        .replace("{REPO_NAME}", repo_name) \
        .replace("{SERVICE_VAR}", service_var) \
        .replace("{REPO_VAR}", repo_var) \
        .replace("{RSS_URL}", rss_url)

    file_path = os.path.join(output_dir, f"{scheduler_name}.java")
    with open(file_path, "w", encoding="utf-8") as f:
        f.write(java_code)

    print(f"Generated scheduler: {file_path}")

print("\nAll scheduler classes generated successfully.")
