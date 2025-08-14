package com.thaipulse.newsapp.service;

import com.thaipulse.newsapp.dto.CityDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PopularCitiesService {

    public List<CityDto> list() {
        return List.of(
                new CityDto("bangkok", "Bangkok", 13.7563, 100.5018, "/city/bangkok"),
                new CityDto("phuket", "Phuket", 7.8804, 98.3923, "/city/phuket"),
                new CityDto("pattaya", "Pattaya", 12.9236, 100.8825, "/city/pattaya"),
                new CityDto("chiangmai", "Chiang Mai", 18.7883, 98.9853, "/city/chiang-mai"),
                new CityDto("hatyai", "Hat Yai", 7.0086, 100.4747, "/city/hat-yai"),
                new CityDto("khonkaen", "Khon Kaen", 16.4419, 102.8350, "/city/khon-kaen"),
                new CityDto("nakhonratchasima", "Nakhon Ratchasima", 14.9799, 102.0977, "/city/nakhon-ratchasima")
        );
    }
}