package com.last.web;

public class EventInfo {
    private String uri;
    private String name;
    private String type;
    private float duration;
    private float impactScore;

    // Constructor
    public EventInfo(String uri, String name, String type, float duration, float impactScore) {
        this.uri = uri;
        this.name = name;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

