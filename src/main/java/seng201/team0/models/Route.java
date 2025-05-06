package seng201.team0.models;

/**
 * Represents a route that can be taken during a race.
 * Each route has specific characteristics that affect the race such as distance, number of fuel stops, difficulty, and type.
 */
public class Route {

    /**
     * Represents the type of a race route.
     * A route can be flat, hilly, off-road, or windy, which influences car performance during a race.
     */
    public enum RouteType { FLAT, HILLY, OFFROAD, WINDY }

    private RouteType description;
    private int distance;
    private int fuelStops;
    private double difficultyMultiplier;

    /**
     * Constructs a new Route object with the given parameters.
     *
     * @param description           a textual description of the route
     * @param distance              the distance of the route in kilometers
     * @param fuelStops             the number of fuel stops available along the route
     * @param difficultyMultiplier  a multiplier that adjusts the race difficulty
     */
    public Route(RouteType description, int distance, int fuelStops, double difficultyMultiplier) {
        this.description = description;
        this.distance = distance;
        this.fuelStops = fuelStops;
        this.difficultyMultiplier = difficultyMultiplier;
    }

    public RouteType getRouteDescription() {
        return description;
    }

    public int getRouteDistance() {
        return distance;
    }

    public int getRouteFuelStops() {
        return fuelStops;
    }

    public double getRouteDifficultyMultiplier() {
        return difficultyMultiplier;
    }

    public void setRouteDescription(RouteType description) {
        this.description = description;
    }

    public void setRouteDistance(int distance) {
        this.distance = distance;
    }

    public void setRouteFuelStops(int fuelStops) {
        this.fuelStops = fuelStops;
    }

    public void setRouteDifficultyMultiplier(double difficultyMultiplier) { this.difficultyMultiplier = difficultyMultiplier; }

}