package com.thaipulse.newsapp.controller;

import com.thaipulse.newsapp.dto.CityDto;
import com.thaipulse.newsapp.service.PopularCitiesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CitiesController {

    private final PopularCitiesService service;

    public CitiesController(PopularCitiesService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<CityDto>> list() {
        return ResponseEntity.ok(service.list());
    }
}