package com.thaipulse.newsapp.service;

import com.rometools.modules.mediarss.MediaEntryModule;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.thaipulse.newsapp.model.News;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
					Matcher matcher = Pattern.compile("<img[^>]+src=[\"']([^\"']+)[\"']").matcher(desc);
					if (matcher.find()) {
						news.setImage(matcher.group(1));
					}
				}
				newsList.add(news);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return newsList;
	}

}