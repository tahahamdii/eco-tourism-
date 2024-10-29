package com.last.web;
public class ActivityInfo {
    private String uri;
    private String name;
    private String category;
    private double ecoRating;
    private float duration;
    private float impactScore;

    // Constructor
    public ActivityInfo(String uri, String name, String category, double ecoRating, float duration, float impactScore) {
        this.uri = uri;
        this.name = name;
        this.category = category;
        this.ecoRating = ecoRating;
        this.duration = duration;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getEcoRating() {
        return ecoRating;
    }

    public void setEcoRating(double ecoRating) {
        this.ecoRating = ecoRating;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public float getImpactScore() {
        return impactScore;
    }

    public void setImpactScore(float impactScore) {
        this.impactScore = impactScore;
    }
}
