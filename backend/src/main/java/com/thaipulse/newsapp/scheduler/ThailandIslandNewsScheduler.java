package com.thaipulse.newsapp.scheduler;

import com.thaipulse.newsapp.repository.ThailandIslandNewsRepository;
import com.thaipulse.newsapp.service.ThailandIslandNewsRssFeedService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ThailandIslandNewsScheduler {

    private static final Logger logger = LoggerFactory.getLogger(ThailandIslandNewsScheduler.class);

    private final ThailandIslandNewsRssFeedService thailandIslandNewsRssFeedService;
    private final ThailandIslandNewsRepository thailandIslandNewsRepository;

    @Scheduled(fixedRate = 1000000)
    public void refereshNews() {
        return;
    }
}
