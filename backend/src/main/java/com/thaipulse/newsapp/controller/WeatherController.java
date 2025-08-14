package com.thaipulse.newsapp.controller;

import com.thaipulse.newsapp.dto.WeatherDto;
import com.thaipulse.newsapp.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    @GetMapping("/{city}")
    public ResponseEntity<WeatherDto> byCity(@PathVariable String city) {
        if (city == null || city.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        WeatherDto dto = service.getByCity(city.trim());
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<WeatherDto> byCoords(
            @RequestParam double lat,
            @RequestParam double lon
    ) {
        WeatherDto dto = service.getByCoords(lat, lon);
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(dto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleError(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"error\":\"" + e.getMessage().replace("\"", "'") + "\"}");
    }
}
