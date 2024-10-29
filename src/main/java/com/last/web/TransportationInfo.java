package com.last.web;

public class TransportationInfo {
    private String uri;
    private String name;
    private String type;
    private double ecoRating;
    private float impactScore;

    // Constructor
    public TransportationInfo(String uri, String name, String type, double ecoRating, float impactScore) {
        this.uri = uri;
        this.name = name;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
