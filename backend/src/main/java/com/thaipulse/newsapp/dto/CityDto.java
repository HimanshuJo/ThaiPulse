package com.thaipulse.newsapp.dto;

public class CityDto {
    private String id;

    private String name;

    private double lat;

    private double lon;

    private String path;

    public CityDto(String id, String name, double lat, double lon, String path) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
