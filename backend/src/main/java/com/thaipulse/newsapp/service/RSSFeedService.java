package com.thaipulse.newsapp.service;

import com.thaipulse.newsapp.model.News;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.*;

@Service
public class RSSFeedService{

	public List<News>getNewsFromRss(String rssUrl){
		List<News>newsList=new ArrayList<>();
		try{
			URL url=new URL(rssUrl);
			SyndFeedInput input=new SyndFeedInput();
			SyndFeed feed=input.build(new XmlReader(url));
			for(SyndEntry entry: feed.getEntries()){
				News news=new News();
				news.setTitle(entry.getTitle());
				news.setSource(feed.getTitle());
				news.setLink(entry.getLink());
				newsList.add(news);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return newsList;
	}

}