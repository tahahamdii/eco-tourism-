package com.last.web;

import com.last.web.DestinationInfo;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RdfService {

    private static final String RDF_FILE_PATH = "data/web.rdf"; // Update with your RDF file path

    public List<DestinationInfo> getAllDestinations() {
        List<DestinationInfo> destinations = new ArrayList<>();
        Model model = ModelFactory.createDefaultModel();

        try (InputStream inputStream = new FileInputStream(RDF_FILE_PATH)) {
            model.read(inputStream, null);
            String queryString = "PREFIX ont: <http://www.semanticweb.org/lenovo/ontologies/2024/9/untitled-ontology-4#> " +
                    "SELECT ?destination ?name ?ecoRating ?season ?impactScore WHERE { " +
                    "?destination a ont:Destination . " +
                    "?destination ont:name ?name . " +
                    "?destination ont:ecoRating ?ecoRating . " +
                    "?destination ont:season ?season . " +
                    "?destination ont:impactScore ?impactScore . " +
                    "}";

            Query query = QueryFactory.create(queryString);
            try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
                ResultSet results = qexec.execSelect();
                while (results.hasNext()) {
                    QuerySolution soln = results.nextSolution();
                    String uri = soln.getResource("destination").getURI();
                    String name = soln.getLiteral("name").getString();
                    double ecoRating = soln.getLiteral("ecoRating").getDouble();
                    String season = soln.getLiteral("season").getString();
                    float impactScore = soln.getLiteral("impactScore").getFloat();
                    destinations.add(new DestinationInfo(uri, name, ecoRating,season,impactScore));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return destinations;
    }

    // Fetch destinations by season
    public List<DestinationInfo> getDestinationsBySeason(String season) {
        return executeQuery("PREFIX ont: <http://www.semanticweb.org/lenovo/ontologies/2024/9/untitled-ontology-4#> " +
                "SELECT ?destination ?name ?ecoRating ?impactScore WHERE { " +
                "?destination a ont:Destination . " +
                "?destination ont:name ?name . " +
                "?destination ont:ecoRating ?ecoRating . " +
                "?destination ont:season ?season . " +
                "?destination ont:impactScore ?impactScore . " +
                "FILTER (?season = \"" + season + "\")" +
                "}");
    }

    // Fetch destinations by ecoRating range
    public List<DestinationInfo> getDestinationsByEcoRatingRange(double minRating, double maxRating) {
        return executeQuery("PREFIX ont: <http://www.semanticweb.org/lenovo/ontologies/2024/9/untitled-ontology-4#> " +
                "SELECT ?destination ?name ?season ?impactScore WHERE { " +
                "?destination a ont:Destination . " +
                "?destination ont:name ?name . " +
                "?destination ont:ecoRating ?ecoRating . " +
                "?destination ont:season ?season . " +
                "?destination ont:impactScore ?impactScore . " +
                "FILTER (?ecoRating >= " + minRating + " && ?ecoRating <= " + maxRating + ")" +
                "}");
    }

    // Fetch destinations by impact score threshold
    public List<DestinationInfo> getDestinationsByImpactScore(double maxImpactScore) {
        return executeQuery("PREFIX ont: <http://www.semanticweb.org/lenovo/ontologies/2024/9/untitled-ontology-4#> " +
                "SELECT ?destination ?name ?ecoRating ?season WHERE { " +
                "?destination a ont:Destination . " +
                "?destination ont:name ?name . " +
                "?destination ont:ecoRating ?ecoRating . " +
                "?destination ont:season ?season . " +
                "?destination ont:impactScore ?impactScore . " +
                "FILTER (?impactScore <= " + maxImpactScore + ")" +
                "}");
    }

    // Method to fetch destinations by ecoRating and season
    public List<DestinationInfo> getDestinationsByEcoRatingAndSeason(double ecoRating, String season) {
        List<DestinationInfo> destinations = new ArrayList<>();
        Model model = ModelFactory.createDefaultModel();

        try (InputStream inputStream = new FileInputStream(RDF_FILE_PATH)) {
            model.read(inputStream, null);
            String queryString = "PREFIX ont: <http://www.semanticweb.org/lenovo/ontologies/2024/9/untitled-ontology-4#> " +
                    "SELECT ?destination ?name ?ecoRating ?season ?impactScore WHERE { " +
                    "?destination a ont:Destination . " +
                    "?destination ont:name ?name . " +
                    "?destination ont:ecoRating ?ecoRating . " +
                    "?destination ont:season ?season . " +
                    "?destination ont:impactScore ?impactScore . " +
                    "FILTER (?ecoRating >= " + ecoRating + " && ?season = \"" + season + "\")" +
                    "}";

            Query query = QueryFactory.create(queryString);
            try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
                ResultSet results = qexec.execSelect();
                while (results.hasNext()) {
                    QuerySolution soln = results.nextSolution();
                    String uri = soln.getResource("destination").getURI();
                    String name = soln.getLiteral("name").getString();
                    double destinationEcoRating = soln.getLiteral("ecoRating").getDouble();
                    String destinationSeason = soln.getLiteral("season").getString();
                    float impactScore = soln.getLiteral("impactScore").getFloat();
                    destinations.add(new DestinationInfo(uri, name, destinationEcoRating, destinationSeason, impactScore));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return destinations;
    }

    // General query execution helper
    private List<DestinationInfo> executeQuery(String queryString) {
        List<DestinationInfo> destinations = new ArrayList<>();
        Model model = ModelFactory.createDefaultModel();

        try (InputStream inputStream = new FileInputStream(RDF_FILE_PATH)) {
            model.read(inputStream, null);

            Query query = QueryFactory.create(queryString);
            try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
                ResultSet results = qexec.execSelect();
                while (results.hasNext()) {
                    QuerySolution soln = results.nextSolution();
                    String uri = soln.getResource("destination").getURI();
                    String name = soln.getLiteral("name").getString();
                    double ecoRating = soln.contains("ecoRating") ? soln.getLiteral("ecoRating").getDouble() : 0.0;
                    String season = soln.contains("season") ? soln.getLiteral("season").getString() : "";
                    float impactScore = soln.contains("impactScore") ? soln.getLiteral("impactScore").getFloat() : 0.0f;
                    destinations.add(new DestinationInfo(uri, name, ecoRating, season, impactScore));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return destinations;
    }



    public List<UserInfo> getUsersByEcoAwareness(String ecoAwarenessLevel) {
        String queryString = "PREFIX ont: <http://www.semanticweb.org/lenovo/ontologies/2024/9/untitled-ontology-4#> " +
                "SELECT ?user ?name ?location ?budget WHERE { " +
                "?user a ont:User . " +
                "?user ont:name ?name . " +
                "?user ont:location ?location . " +
                "?user ont:budget ?budget . " +
                "?user ont:ecoAwarenessLevel ?ecoAwarenessLevel . " +
                "FILTER (?ecoAwarenessLevel = \"" + ecoAwarenessLevel + "\")" +
                "}";
        return executeUserQuery(queryString);
    }
    public List<UserInfo> getUsersByBudgetRange(double minBudget, double maxBudget) {
        String queryString = "PREFIX ont: <http://www.semanticweb.org/lenovo/ontologies/2024/9/untitled-ontology-4#> " +
                "SELECT ?user ?name ?ecoAwarenessLevel ?location WHERE { " +
                "?user a ont:User . " +
                "?user ont:name ?name . " +
                "?user ont:ecoAwarenessLevel ?ecoAwarenessLevel . " +
                "?user ont:location ?location . " +
                "?user ont:budget ?budget . " +
                "FILTER (?budget >= " + minBudget + " && ?budget <= " + maxBudget + ")" +
                "}";
        return executeUserQuery(queryString);
    }
    public List<UserInfo> getUsersByPreferences(String preference) {
        String queryString = "PREFIX ont: <http://www.semanticweb.org/lenovo/ontologies/2024/9/untitled-ontology-4#> " +
                "SELECT ?user ?name ?budget ?ecoAwarenessLevel WHERE { " +
                "?user a ont:User . " +
                "?user ont:name ?name . " +
                "?user ont:budget ?budget . " +
                "?user ont:ecoAwarenessLevel ?ecoAwarenessLevel . " +
                "?user ont:preferences ?preferences . " +
                "FILTER (CONTAINS(?preferences, \"" + preference + "\"))" +
                "}";
        return executeUserQuery(queryString);
    }
    private List<UserInfo> executeUserQuery(String queryString) {
        List<UserInfo> users = new ArrayList<>();
        Model model = ModelFactory.createDefaultModel();
        try (InputStream inputStream = new FileInputStream(RDF_FILE_PATH)) {
            model.read(inputStream, null);
            Query query = QueryFactory.create(queryString);
            try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
                ResultSet results = qexec.execSelect();
                while (results.hasNext()) {
                    QuerySolution soln = results.nextSolution();
                    String uri = soln.getResource("user").getURI();
                    String name = soln.getLiteral("name").getString();
                    String location = soln.contains("location") ? soln.getLiteral("location").getString() : "";
                    double budget = soln.contains("budget") ? soln.getLiteral("budget").getDouble() : 0.0;
                    String ecoAwarenessLevel = soln.contains("ecoAwarenessLevel") ? soln.getLiteral("ecoAwarenessLevel").getString() : "";
                    String preferences = soln.contains("preferences") ? soln.getLiteral("preferences").getString() : "";

                    // Add user to the list with the updated constructor
                    users.add(new UserInfo(uri, name, location, budget, ecoAwarenessLevel, preferences));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<ActivityInfo> getActivitiesByCategory(String category) {
        String queryString = "PREFIX ont: <http://www.semanticweb.org/lenovo/ontologies/2024/9/untitled-ontology-4#> " +
                "SELECT ?activity ?name ?duration ?ecoRating ?impactScore WHERE { " +
                "?activity a ont:Activity . " +
                "?activity ont:name ?name . " +
                "?activity ont:duration ?duration . " +
                "?activity ont:ecoRating ?ecoRating . " +
                "?activity ont:impactScore ?impactScore . " +
                "?activity ont:category ?category . " +
                "FILTER (?category = \"" + category + "\")" +
                "}";
        return executeActivityQuery(queryString);
    }
    public List<ActivityInfo> getActivitiesByEcoRatingRange(double minRating, double maxRating) {
        String queryString = "PREFIX ont: <http://www.semanticweb.org/lenovo/ontologies/2024/9/untitled-ontology-4#> " +
                "SELECT ?activity ?name ?category ?impactScore WHERE { " +
                "?activity a ont:Activity . " +
                "?activity ont:name ?name . " +
                "?activity ont:category ?category . " +
                "?activity ont:impactScore ?impactScore . " +
                "?activity ont:ecoRating ?ecoRating . " +
                "FILTER (?ecoRating >= " + minRating + " && ?ecoRating <= " + maxRating + ")" +
                "}";
        return executeActivityQuery(queryString);
    }
    public List<ActivityInfo> getActivitiesByDuration(float minDuration, float maxDuration) {
        String queryString = "PREFIX ont: <http://www.semanticweb.org/lenovo/ontologies/2024/9/untitled-ontology-4#> " +
                "SELECT ?activity ?name ?category ?ecoRating ?impactScore WHERE { " +
                "?activity a ont:Activity . " +
                "?activity ont:name ?name . " +
                "?activity ont:category ?category . " +
                "?activity ont:ecoRating ?ecoRating . " +
                "?activity ont:impactScore ?impactScore . " +
                "?activity ont:duration ?duration . " +
                "FILTER (?duration >= " + minDuration + " && ?duration <= " + maxDuration + ")" +
                "}";
        return executeActivityQuery(queryString);
    }
    private List<ActivityInfo> executeActivityQuery(String queryString) {
        List<ActivityInfo> activities = new ArrayList<>();
        Model model = ModelFactory.createDefaultModel();

        try (InputStream inputStream = new FileInputStream(RDF_FILE_PATH)) {
            model.read(inputStream, null);

            Query query = QueryFactory.create(queryString);
            try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
                ResultSet results = qexec.execSelect();
                while (results.hasNext()) {
                    QuerySolution soln = results.nextSolution();
                    String uri = soln.getResource("activity").getURI();
                    String name = soln.getLiteral("name").getString();
                    String category = soln.contains("category") ? soln.getLiteral("category").getString() : "";
                    double ecoRating = soln.contains("ecoRating") ? soln.getLiteral("ecoRating").getDouble() : 0.0;
                    float duration = soln.contains("duration") ? soln.getLiteral("duration").getFloat() : 0.0f;
                    float impactScore = soln.contains("impactScore") ? soln.getLiteral("impactScore").getFloat() : 0.0f;
                    activities.add(new ActivityInfo(uri, name, category, ecoRating, duration, impactScore));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return activities;
    }
    public List<EventInfo> getEventsByType(String type) {
        String queryString = "PREFIX ont: <http://www.semanticweb.org/lenovo/ontologies/2024/9/untitled-ontology-4#> " +
                "SELECT ?event ?name ?duration ?impactScore WHERE { " +
                "?event a ont:Event . " +
                "?event ont:name ?name . " +
                "?event ont:duration ?duration . " +
                "?event ont:impactScore ?impactScore . " +
                "?event ont:type ?type . " +
                "FILTER (?type = \"" + type + "\")" +
                "}";
        return executeEventQuery(queryString);
    }
    public List<EventInfo> getEventsByDurationRange(float minDuration, float maxDuration) {
        String queryString = "PREFIX ont: <http://www.semanticweb.org/lenovo/ontologies/2024/9/untitled-ontology-4#> " +
                "SELECT ?event ?name ?type ?impactScore WHERE { " +
                "?event a ont:Event . " +
                "?event ont:name ?name . " +
                "?event ont:type ?type . " +
                "?event ont:impactScore ?impactScore . " +
                "?event ont:duration ?duration . " +
                "FILTER (?duration >= " + minDuration + " && ?duration <= " + maxDuration + ")" +
                "}";
        return executeEventQuery(queryString);
    }
    public List<EventInfo> getEventsByImpactScore(double maxImpactScore) {
        String queryString = "PREFIX ont: <http://www.semanticweb.org/lenovo/ontologies/2024/9/untitled-ontology-4#> " +
                "SELECT ?event ?name ?type ?duration WHERE { " +
                "?event a ont:Event . " +
                "?event ont:name ?name . " +
                "?event ont:type ?type . " +
                "?event ont:duration ?duration . " +
                "?event ont:impactScore ?impactScore . " +
                "FILTER (?impactScore <= " + maxImpactScore + ")" +
                "}";
        return executeEventQuery(queryString);
    }

    private List<EventInfo> executeEventQuery(String queryString) {
        List<EventInfo> events = new ArrayList<>();
        Model model = ModelFactory.createDefaultModel();

        try (InputStream inputStream = new FileInputStream(RDF_FILE_PATH)) {
            model.read(inputStream, null);

            Query query = QueryFactory.create(queryString);
            try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
                ResultSet results = qexec.execSelect();
                while (results.hasNext()) {
                    QuerySolution soln = results.nextSolution();
                    String uri = soln.getResource("event").getURI();
                    String name = soln.getLiteral("name").getString();
                    String type = soln.contains("type") ? soln.getLiteral("type").getString() : "";
                    float duration = soln.contains("duration") ? soln.getLiteral("duration").getFloat() : 0.0f;
                    float impactScore = soln.contains("impactScore") ? soln.getLiteral("impactScore").getFloat() : 0.0f;
                    events.add(new EventInfo(uri, name, type, duration, impactScore));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return events;
    }

    public List<RestaurantInfo> getRestaurantsByCuisineType(String cuisineType) {
        String queryString = "PREFIX ont: <http://www.semanticweb.org/lenovo/ontologies/2024/9/untitled-ontology-4#> " +
                "SELECT ?restaurant ?name ?ecoRating ?impactScore WHERE { " +
                "?restaurant a ont:Restaurant . " +
                "?restaurant ont:name ?name . " +
                "?restaurant ont:ecoRating ?ecoRating . " +
                "?restaurant ont:impactScore ?impactScore . " +
                "?restaurant ont:cuisineType ?cuisineType . " +
                "FILTER (?cuisineType = \"" + cuisineType + "\")" +
                "}";
        return executeRestaurantQuery(queryString);
    }
    public List<RestaurantInfo> getRestaurantsByEcoRatingRange(double minRating, double maxRating) {
        String queryString = "PREFIX ont: <http://www.semanticweb.org/lenovo/ontologies/2024/9/untitled-ontology-4#> " +
                "SELECT ?restaurant ?name ?cuisineType ?impactScore WHERE { " +
                "?restaurant a ont:Restaurant . " +
                "?restaurant ont:name ?name . " +
                "?restaurant ont:cuisineType ?cuisineType . " +
                "?restaurant ont:ecoRating ?ecoRating . " +
                "?restaurant ont:impactScore ?impactScore . " +
                "FILTER (?ecoRating >= " + minRating + " && ?ecoRating <= " + maxRating + ")" +
                "}";
        return executeRestaurantQuery(queryString);
    }
    public List<RestaurantInfo> getRestaurantsByImpactScore(double maxImpactScore) {
        String queryString = "PREFIX ont: <http://www.semanticweb.org/lenovo/ontologies/2024/9/untitled-ontology-4#> " +
                "SELECT ?restaurant ?name ?cuisineType ?ecoRating WHERE { " +
                "?restaurant a ont:Restaurant . " +
                "?restaurant ont:name ?name . " +
                "?restaurant ont:cuisineType ?cuisineType . " +
                "?restaurant ont:ecoRating ?ecoRating . " +
                "?restaurant ont:impactScore ?impactScore . " +
                "FILTER (?impactScore <= " + maxImpactScore + ")" +
                "}";
        return executeRestaurantQuery(queryString);
    }
    private List<RestaurantInfo> executeRestaurantQuery(String queryString) {
        List<RestaurantInfo> restaurants = new ArrayList<>();
        Model model = ModelFactory.createDefaultModel();

        try (InputStream inputStream = new FileInputStream(RDF_FILE_PATH)) {
            model.read(inputStream, null);

            Query query = QueryFactory.create(queryString);
            try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
                ResultSet results = qexec.execSelect();
                while (results.hasNext()) {
                    QuerySolution soln = results.nextSolution();
                    String uri = soln.getResource("restaurant").getURI();
                    String name = soln.getLiteral("name").getString();
                    String cuisineType = soln.contains("cuisineType") ? soln.getLiteral("cuisineType").getString() : "";
                    double ecoRating = soln.contains("ecoRating") ? soln.getLiteral("ecoRating").getDouble() : 0.0;
                    float impactScore = soln.contains("impactScore") ? soln.getLiteral("impactScore").getFloat() : 0.0f;
                    restaurants.add(new RestaurantInfo(uri, name, cuisineType, ecoRating, impactScore));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return restaurants;
    }
    public List<TransportationInfo> getTransportationByType(String type) {
        String queryString = "PREFIX ont: <http://www.semanticweb.org/lenovo/ontologies/2024/9/untitled-ontology-4#> " +
                "SELECT ?transport ?name ?ecoRating ?impactScore WHERE { " +
                "?transport a ont:Transportation . " +
                "?transport ont:name ?name . " +
                "?transport ont:ecoRating ?ecoRating . " +
                "?transport ont:impactScore ?impactScore . " +
                "?transport ont:type ?type . " +
                "FILTER (?type = \"" + type + "\")" +
                "}";
        return executeTransportationQuery(queryString);
    }
    public List<TransportationInfo> getTransportationByEcoRatingRange(double minRating, double maxRating) {
        String queryString = "PREFIX ont: <http://www.semanticweb.org/lenovo/ontologies/2024/9/untitled-ontology-4#> " +
                "SELECT ?transport ?name ?type ?impactScore WHERE { " +
                "?transport a ont:Transportation . " +
                "?transport ont:name ?name . " +
                "?transport ont:type ?type . " +
                "?transport ont:ecoRating ?ecoRating . " +
                "?transport ont:impactScore ?impactScore . " +
                "FILTER (?ecoRating >= " + minRating + " && ?ecoRating <= " + maxRating + ")" +
                "}";
        return executeTransportationQuery(queryString);
    }
    public List<TransportationInfo> getTransportationByImpactScore(double maxImpactScore) {
        String queryString = "PREFIX ont: <http://www.semanticweb.org/lenovo/ontologies/2024/9/untitled-ontology-4#> " +
                "SELECT ?transport ?name ?type ?ecoRating WHERE { " +
                "?transport a ont:Transportation . " +
                "?transport ont:name ?name . " +
                "?transport ont:type ?type . " +
                "?transport ont:ecoRating ?ecoRating . " +
                "?transport ont:impactScore ?impactScore . " +
                "FILTER (?impactScore <= " + maxImpactScore + ")" +
                "}";
        return executeTransportationQuery(queryString);
    }
    private List<TransportationInfo> executeTransportationQuery(String queryString) {
        List<TransportationInfo> transportations = new ArrayList<>();
        Model model = ModelFactory.createDefaultModel();

        try (InputStream inputStream = new FileInputStream(RDF_FILE_PATH)) {
            model.read(inputStream, null);

            Query query = QueryFactory.create(queryString);
            try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
                ResultSet results = qexec.execSelect();
                while (results.hasNext()) {
                    QuerySolution soln = results.nextSolution();
                    String uri = soln.getResource("transport").getURI();
                    String name = soln.getLiteral("name").getString();
                    String type = soln.contains("type") ? soln.getLiteral("type").getString() : "";
                    double ecoRating = soln.contains("ecoRating") ? soln.getLiteral("ecoRating").getDouble() : 0.0;
                    float impactScore = soln.contains("impactScore") ? soln.getLiteral("impactScore").getFloat() : 0.0f;
                    transportations.add(new TransportationInfo(uri, name, type, ecoRating, impactScore));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transportations;
    }

    // Method to fetch detailed information about a destination by URI
    public Map<String, Object> getDestinationDetails(String destinationUri) {
        Model model = ModelFactory.createDefaultModel();
        Map<String, Object> details = new HashMap<>();

        try (InputStream inputStream = new FileInputStream(RDF_FILE_PATH)) {
            model.read(inputStream, null);

            // SPARQL query to retrieve related entities with their names
            String queryString = "PREFIX ont: <http://www.semanticweb.org/lenovo/ontologies/2024/9/untitled-ontology-4#> " +
                    "SELECT ?event ?eventName ?activity ?activityName ?restaurant ?restaurantName ?transportation ?transportationName WHERE { " +
                    "<" + destinationUri + "> ont:holdsEvent ?event . " +
                    "OPTIONAL { ?event ont:name ?eventName } ." +
                    "OPTIONAL { <" + destinationUri + "> ont:offersActivity ?activity . ?activity ont:name ?activityName } ." +
                    "OPTIONAL { <" + destinationUri + "> ont:hasRestaurant ?restaurant . ?restaurant ont:name ?restaurantName } ." +
                    "OPTIONAL { <" + destinationUri + "> ont:hasTransportation ?transportation . ?transportation ont:name ?transportationName } ." +
                    "}";

            Query query = QueryFactory.create(queryString);
            try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
                ResultSet results = qexec.execSelect();
                if (results.hasNext()) {
                    QuerySolution soln = results.nextSolution();
                    details.put("holdsEvent", soln.contains("eventName") ? soln.getLiteral("eventName").getString() : "Unnamed Event");
                    details.put("offersActivity", soln.contains("activityName") ? soln.getLiteral("activityName").getString() : "Unnamed Activity");
                    details.put("hasRestaurant", soln.contains("restaurantName") ? soln.getLiteral("restaurantName").getString() : "Unnamed Restaurant");
                    details.put("hasTransportation", soln.contains("transportationName") ? soln.getLiteral("transportationName").getString() : "Unnamed Transportation");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return details;
    }

}


