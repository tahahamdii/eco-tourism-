package com.last.web;

public class RestaurantInfo {
    private String uri;
    private String name;
    private String cuisineType;
    private double ecoRating;
    private float impactScore;

    // Constructor
    public RestaurantInfo(String uri, String name, String cuisineType, double ecoRating, float impactScore) {
        this.uri = uri;
        this.name = name;
        this.cuisineType = cuisineType;
        this.ecoRating = ecoRating;
        this.impactScore = impactScore;
    }

    // Getters and Setters
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public double getEcoRating() {
        return ecoRating;
    }

    public void setEcoRating(double ecoRating) {
        this.ecoRating = ecoRating;
    }

    public float getImpactScore() {
        return impactScore;
    }

    public void setImpactScore(float impactScore) {
        this.impactScore = impactScore;
    }
}

