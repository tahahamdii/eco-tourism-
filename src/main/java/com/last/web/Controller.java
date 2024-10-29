package com.last.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController()
public class Controller {

    private final RdfService rdfService;

    public Controller(RdfService rdfService) {
        this.rdfService = rdfService;
    }

    @GetMapping("/destinations")
    public List<DestinationInfo> getDestinations() {
        return rdfService.getAllDestinations();
    }
    // Get destinations by season
    @GetMapping("/destinations/bySeason")
    public List<DestinationInfo> getDestinationsBySeason(@RequestParam String season) {
        return rdfService.getDestinationsBySeason(season);
    }

    // Get destinations by eco rating range
    @GetMapping("/destinations/byEcoRatingRange")
    public List<DestinationInfo> getDestinationsByEcoRatingRange(@RequestParam double minRating, @RequestParam double maxRating) {
        return rdfService.getDestinationsByEcoRatingRange(minRating, maxRating);
    }

    // Get destinations by impact score threshold
    @GetMapping("/destinations/byImpactScore")
    public List<DestinationInfo> getDestinationsByImpactScore(@RequestParam double maxImpactScore) {
        return rdfService.getDestinationsByImpactScore(maxImpactScore);
    }

    // Get users by eco-awareness level
    @GetMapping("/users/ecoAwarenessLevel/{level}")
    public List<UserInfo> getUsersByEcoAwarenessLevel(@PathVariable String level) {
        return rdfService.getUsersByEcoAwareness(level);
    }

    // Get users by budget range
    @GetMapping("/users/budgetRange")
    public List<UserInfo> getUsersByBudgetRange(@RequestParam double minBudget, @RequestParam double maxBudget) {
        return rdfService.getUsersByBudgetRange(minBudget, maxBudget);
    }

    // Get users by preferences
    @GetMapping("/users/preferences")
    public List<UserInfo> getUsersByPreferences(@RequestParam String preference) {
        return rdfService.getUsersByPreferences(preference);
    }

    // Get events by type
    @GetMapping("/events/byType")
    public List<EventInfo> getEventsByType(@RequestParam String type) {
        return rdfService.getEventsByType(type);
    }

    // Get events by duration range
    @GetMapping("/events/byDurationRange")
    public List<EventInfo> getEventsByDurationRange(@RequestParam float minDuration, @RequestParam float maxDuration) {
        return rdfService.getEventsByDurationRange(minDuration, maxDuration);
    }

    // Get events by impact score threshold
    @GetMapping("/events/byImpactScore")
    public List<EventInfo> getEventsByImpactScore(@RequestParam double maxImpactScore) {
        return rdfService.getEventsByImpactScore(maxImpactScore);
    }

    // Get activities by category
    @GetMapping("activities/byCategory")
    public List<ActivityInfo> getActivitiesByCategory(@RequestParam String category) {
        return rdfService.getActivitiesByCategory(category);
    }

    // Get activities by eco rating range
    @GetMapping("activities/byEcoRatingRange")
    public List<ActivityInfo> getActivitiesByEcoRatingRange(@RequestParam double minRating, @RequestParam double maxRating) {
        return rdfService.getActivitiesByEcoRatingRange(minRating, maxRating);
    }

    // Get activities by duration range
    @GetMapping("activities/byDurationRange")
    public List<ActivityInfo> getActivitiesByDuration(@RequestParam float minDuration, @RequestParam float maxDuration) {
        return rdfService.getActivitiesByDuration(minDuration, maxDuration);
    }
    // Get restaurants by cuisine type
    @GetMapping("/restaurants/byCuisineType")
    public List<RestaurantInfo> getRestaurantsByCuisineType(@RequestParam String cuisineType) {
        return rdfService.getRestaurantsByCuisineType(cuisineType);
    }

    // Get restaurants by eco rating range
    @GetMapping("/restaurants/byEcoRatingRange")
    public List<RestaurantInfo> getRestaurantsByEcoRatingRange(@RequestParam double minRating, @RequestParam double maxRating) {
        return rdfService.getRestaurantsByEcoRatingRange(minRating, maxRating);
    }

    // Get restaurants by impact score
    @GetMapping("/restaurants/byImpactScore")
    public List<RestaurantInfo> getRestaurantsByImpactScore(@RequestParam double maxImpactScore) {
        return rdfService.getRestaurantsByImpactScore(maxImpactScore);
    }
    @GetMapping("/transportation/byType")
    public List<TransportationInfo> getTransportationByType(@RequestParam String type) {
        return rdfService.getTransportationByType(type);
    }

    // Get transportation by eco rating range
    @GetMapping("/transportation/byEcoRatingRange")
    public List<TransportationInfo> getTransportationByEcoRatingRange(@RequestParam double minRating, @RequestParam double maxRating) {
        return rdfService.getTransportationByEcoRatingRange(minRating, maxRating);
    }

    // Get transportation by impact score
    @GetMapping("/transportation/byImpactScore")
    public List<TransportationInfo> getTransportationByImpactScore(@RequestParam double maxImpactScore) {
        return rdfService.getTransportationByImpactScore(maxImpactScore);
    }

    @GetMapping("/destinations/byEcoRatingAndSeason")
    public List<DestinationInfo> getDestinationsByEcoRatingAndSeason(
            @RequestParam double ecoRating,
            @RequestParam String season) {
        return rdfService.getDestinationsByEcoRatingAndSeason(ecoRating, season);
    }
    @GetMapping("/destinations/details")
    public Map<String, Object> getDestinationDetails(@RequestParam String uri) {
        return rdfService.getDestinationDetails(uri);
    }
    @GetMapping("/restaurants/location")
    public Map<String, String> getRestaurantLocation(@RequestParam String uri) {
        return rdfService.getRestaurantLocation(uri);
    }

    // Endpoint to get the location and participants of an event
    @GetMapping("/events/details")
    public Map<String, Object> getEventDetails(@RequestParam String uri) {
        return rdfService.getEventDetails(uri);
    }
}