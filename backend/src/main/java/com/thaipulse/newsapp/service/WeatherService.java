package com.thaipulse.newsapp.service;

import com.thaipulse.newsapp.dto.WeatherDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Map;

@Service
public class WeatherService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${weather.api.base-url}")
    private String baseUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.units:metric}")
    private String units;

    @Cacheable(cacheNames = "weatherByCity", key = "#city.toLowerCase()")
    public WeatherDto getByCity(String city) {
        String url = String.format("%s/weather?q=%s&appid=%s&units=%s", baseUrl, city, apiKey, units);
        return fetchAndMap(url, city);
    }

    @Cacheable(cacheNames = "weatherByCoords", key = "T(java.util.Objects).hash(#lat,#lon)")
    public WeatherDto getByCoords(double lat, double lon) {
        String url = String.format("%s/weather?lat=%s&lon=%s&appid=%s&units=%s", baseUrl, lat, lon, apiKey, units);
        return fetchAndMap(url, null);
    }

    @SuppressWarnings("unchecked")
    private WeatherDto fetchAndMap(String url, String cityOverride) {
        ResponseEntity<Map> res = restTemplate.getForEntity(url, Map.class);
        Map body = res.getBody();
        if (body == null) throw new IllegalStateException("Empty weather response");

        Map main = (Map) body.get("main");
        Map wind = (Map) body.get("wind");
        Map weather0 = (Map) ((java.util.List) body.get("weather")).get(0);

        String city = cityOverride != null ? cityOverride : (String) body.get("name");
        double tempC = ((Number) main.get("temp")).doubleValue();
        String description = ((String) weather0.get("description"));
        String icon = ((String) weather0.get("icon"));
        int humidity = ((Number) main.get("humidity")).intValue();
        double windMs = wind != null && wind.get("speed") != null ? ((Number) wind.get("speed")).doubleValue() : 0.0;
        double windKph = windMs * 3.6;

        WeatherDto dto = new WeatherDto();
        dto.setCity(city);
        dto.setTempC(tempC);
        dto.setDescription(description);
        dto.setIcon(icon);
        dto.setHumidity(humidity);
        dto.setWindKph(windKph);
        dto.setUpdatedAt(Instant.now());
        return dto;
    }

}