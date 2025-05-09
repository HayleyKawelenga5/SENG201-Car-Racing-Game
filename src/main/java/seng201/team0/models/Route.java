package seng201.team0.models;

/**
 * Represents a route that can be taken during a race.
 * Each route has specific characteristics that affect the race such as distance, number of fuel stops, difficulty, and type.
 */
public class Route {

    /**
     * Represents the type of race route.
     * A route can be flat, hilly, off-road,windy or beach which influences car performance during a race.
     */
    public enum RouteType { FLAT, HILLY, OFFROAD, WINDY, BEACH }

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

    /**
     * Returns the type or description of the route.
     *
     * @return the {@code RouteType} of the route (e.g., FLAT, HILLY, OFFROAD)
     */
    public RouteType getRouteDescription() {
        return description;
    }

    /**
     * Returns the distance of the route in kilometers.
     *
     * @return the distance of the route
     */
    public int getRouteDistance() {
        return distance;
    }

    /**
     * Returns the number of fuel stops along the route.
     *
     * @return the number of fuel stops
     */
    public int getRouteFuelStops() {
        return fuelStops;
    }

    /**
     * Returns the difficulty multiplier of the route.
     * The difficulty multiplier is a value that affects different attributes of the player's car
     * @return the difficulty multiplier
     */
    public double getRouteDifficultyMultiplier() {
        return difficultyMultiplier;
    }

    /**
     * Sets the type or description of the route.
     *
     * @param description the new type of the route (e.g., FLAT, HILLY, OFFROAD)
     */
    public void setRouteDescription(RouteType description) {
        this.description = description;
    }

    /**
     * Sets the distance of the route in kilometers.
     *
     * @param distance the new distance for the route
     */
    public void setRouteDistance(int distance) {
        this.distance = distance;
    }


    /**
     * Sets the number of fuel stops along the route.
     *
     * @param fuelStops the new number of fuel stops
     */
    public void setRouteFuelStops(int fuelStops) {
        this.fuelStops = fuelStops;
    }

    /**
     * Sets the difficulty multiplier of the route.
     *
     * @param difficultyMultiplier the new difficulty multiplier
     */
    public void setRouteDifficultyMultiplier(double difficultyMultiplier) { this.difficultyMultiplier = difficultyMultiplier; }

}