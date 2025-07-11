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
		allNews.addAll(rssFeedService.getNewsFromRss("https://thediplomat.com/feed"));
		allNews.addAll(rssFeedService.getNewsFromRss("https://asiatimes.com/feed"));
		//allNews.addAll(rssFeedService.getNewsFromRss("https://e27.co/index_wp.php/feed"));
		//allNews.addAll(rssFeedService.getNewsFromRss("https://asianscientist.com/feed/?x=1"));
		//allNews.addAll(rssFeedService.getNewsFromRss("https://southeastasiaglobe.com/feed"));
		//allNews.addAll(rssFeedService.getNewsFromRss("https://apec.org/feeds/rss"));
		//allNews.addAll(rssFeedService.getNewsFromRss("https://indothainews.com/feed"));
		//allNews.addAll(rssFeedService.getNewsFromRss("https://scmp.com/rss/91/feed"));
		/*allNews.addAll(rssFeedService.getNewsFromRss("https://www.bangkokpost.com/rss/data/topstories.xml"));
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
		 */
		Collections.shuffle(allNews);
		return allNews;
	}
}
