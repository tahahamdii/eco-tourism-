package com.last.web;

public class DestinationInfo {
    private String uri;
    private String name;
    private double ecoRating;
    private String season;
    private float impactScore;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getImpactScore() {
        return impactScore;
    }

    public void setImpactScore(float impactScore) {
        this.impactScore = impactScore;
    }

    public double getEcoRating() {
        return ecoRating;
    }

    public void setEcoRating(double ecoRating) {
        this.ecoRating = ecoRating;
    }

    public DestinationInfo(String uri, String name, double ecoRating, String season, float impactScore) {
        this.uri = uri;
        this.name = name;
        this.ecoRating = ecoRating;
        this.season = season;
        this.impactScore = impactScore;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }
// Getters and Setters
    // Add getters and setters for the new fields (season and impactScore)
}
