package com.last.web;

public class UserInfo {
    private String uri;
    private String name;
    private String location;
    private double budget;
    private String ecoAwarenessLevel;
    private String preferences;

    // Constructor
    public UserInfo(String uri, String name, String location, double budget, String ecoAwarenessLevel, String preferences) {
        this.uri = uri;
        this.name = name;
        this.location = location;
        this.budget = budget;
        this.ecoAwarenessLevel = ecoAwarenessLevel;
        this.preferences = preferences;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getEcoAwarenessLevel() {
        return ecoAwarenessLevel;
    }

    public void setEcoAwarenessLevel(String ecoAwarenessLevel) {
        this.ecoAwarenessLevel = ecoAwarenessLevel;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    // toString method for easy debugging
    @Override
    public String toString() {
        return "UserInfo{" +
                "uri='" + uri + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", budget=" + budget +
                ", ecoAwarenessLevel='" + ecoAwarenessLevel + '\'' +
                ", preferences='" + preferences + '\'' +
                '}';
    }
}
