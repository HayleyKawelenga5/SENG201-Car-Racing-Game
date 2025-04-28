package seng201.team0.models

public enum RouteType { FLAT, HILLY, OFFROAD, WINDY }

public class Route {

    private String description;
    private int distance;
    private int fuelStops;
    private int difficultyMultiplier;
    private RouteType type;

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