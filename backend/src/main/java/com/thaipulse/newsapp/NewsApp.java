package com.thaipulse.newsapp;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewsApp{

	public static void main(String[] args) throws Exception{
		SpringApplication.run(NewsApp.class, args);
	}
	
}