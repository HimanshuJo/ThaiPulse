package com.thaipulse.newsapp.controller;

import com.thaipulse.newsapp.service.RSSFeedService;
import com.thaipulse.newsapp.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
public class NewsController {

	@Autowired
	private RSSFeedService rssFeedService;

	@GetMapping(value = "/news")
	public List<News> getAllNews(){
		List<News>allNews=new ArrayList<>();
		allNews.addAll(rssFeedService.getNewsFromRss("https://www.bangkokpost.com/rss/data/most-recent.xml"));
		allNews.addAll(rssFeedService.getNewsFromRss("https://www.bangkokpost.com/rss/data/topstories.xml"));
		allNews.addAll(rssFeedService.getNewsFromRss("https://www.bangkokpost.com/rss/data/thailand.xml"));
		allNews.addAll(rssFeedService.getNewsFromRss("https://www.bangkokpost.com/rss/data/world.xml"));
		allNews.addAll(rssFeedService.getNewsFromRss("https://www.bangkokpost.com/rss/data/business.xml"));
		allNews.addAll(rssFeedService.getNewsFromRss("https://www.bangkokpost.com/rss/data/opinion.xml"));
		allNews.addAll(rssFeedService.getNewsFromRss("https://www.bangkokpost.com/rss/data/sports.xml"));
		allNews.addAll(rssFeedService.getNewsFromRss("https://www.bangkokpost.com/rss/data/life.xml"));
		allNews.addAll(rssFeedService.getNewsFromRss("https://www.bangkokpost.com/rss/data/learning.xml"));
		allNews.addAll(rssFeedService.getNewsFromRss("thttps://www.bangkokpost.com/rss/data/video.xml"));
		allNews.addAll(rssFeedService.getNewsFromRss("https://www.bangkokpost.com/rss/data/property.xml"));
		allNews.addAll(rssFeedService.getNewsFromRss("https://www.bangkokpost.com/rss/data/photos.xml"));
		Collections.shuffle(allNews);
		return allNews;
	}
}
