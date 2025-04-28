package seng201.team0.models;

/**
 * Represents the type of a race route.
 * A route can be flat, hilly, off-road, or windy, which influences car performance during a race.
 */
public enum RouteType { FLAT, HILLY, OFFROAD, WINDY }

/**
 * Represents a route that can be taken during a race.
 * Each route has specific characteristics that affect the race such as distance, number of fuel stops, difficulty, and type.
 */
public class Route {

    private String description;
    private int distance;
    private int fuelStops;
    private int difficultyMultiplier;
    private RouteType type;

    /**
     * Constructs a new Route object with the given parameters.
     *
     * @param description           a textual description of the route
     * @param distance              the distance of the route in kilometers
     * @param fuelStops             the number of fuel stops available along the route
     * @param difficultyMultiplier  a multiplier that adjusts the race difficulty
     */
    public Route(String description, int distance, int fuelStops, int difficultyMultiplier) {
        this.description = description;
        this.distance = distance;
        this.fuelStops = fuelStops;
        this.difficultyMultiplier = difficultyMultiplier;
    }

    public String getDescription() {
        return description;
    }

    public int getDistance() {
        return distance;
    }

    public int getFuelStops() {
        return fuelStops;
    }

    public int getDifficultyMultiplier() {
        return difficultyMultiplier;
    }

    public RouteType getType() {
        return type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setFuelStops(int fuelStops) {
        this.fuelStops = fuelStops;
    }

    public void setDifficultyMultiplier(int difficultyMultiplier) {
        this.difficultyMultiplier = difficultyMultiplier;
    }

    public void setType(RouteType type) {
        this.type = type;
    }
}