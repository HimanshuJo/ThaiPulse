package com.thaipulse.newsapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cities")
public class CitiesController {

    @GetMapping
    public List<Map<String, Object>> getPopularCities() {
        List<Map<String, Object>> cities = new ArrayList<>();

        cities.add(Map.of("name", "Bangkok", "lat", 13.7563, "lng", 100.5018));
        cities.add(Map.of("name", "Pattaya", "lat", 12.9236, "lng", 100.8825));
        cities.add(Map.of("name", "Phuket", "lat", 7.8804, "lng", 98.3923));
        cities.add(Map.of("name", "Chiang Mai", "lat", 18.7883, "lng", 98.9853));
        cities.add(Map.of("name", "Hat Yai", "lat", 7.0083, "lng", 100.4767));
        cities.add(Map.of("name", "Khon Kaen", "lat", 16.4419, "lng", 102.835));
        cities.add(Map.of("name", "Nakhon Ratchasima", "lat", 14.9799, "lng", 102.0977));

        return cities;
    }
}
