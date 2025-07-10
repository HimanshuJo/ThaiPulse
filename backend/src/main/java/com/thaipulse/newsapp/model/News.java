package com.thaipulse.newsapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class News{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String source;
	private String link;

	public News(){}

	public News(Long id, String title, String source, String link){
		this.id=id;
		this.title=title;
		this.source=source;
		this.link=link;
	}

	public Long getId(){
		return id;
	}

	public void setId(Long id){
		this.id=id;
	}

	public String getTitle(){
		return title;
	}

	public void setTitle(String title){
		this.title=title;
	}

	public String getSource(){
		return source;
	}

	public void setSource(String source){
		this.source=source;
	}

	public String getLink(){
		return link;
	}

	public void setLink(String link){
		this.link=link;
	}
}