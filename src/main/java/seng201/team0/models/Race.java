package seng201.team0.models;

import java.util.*;

/**
 * Represents a race event, which includes a duration, a list
 * of available routes, the number of race entries and the
 * prize money awarded to the winner.
 */
public class Race {

    private final int hours;
    private final List<Route> availableRoutes;
    private final int entries;
    private int prizeMoney;

    /**
     * Constructs a new {@link Race} with the specified duration, routes,
     * number of entries and prize money.
     * @param hours the duration of the race
     * @param availableRoutes one to three routes that can be taken to complete the race
     * @param entries the number of cars in the race excluding the player
     * @param prizeMoney the amount of money awarded for coming in first place
     */
    public Race(int hours, List<Route> availableRoutes, int entries, int prizeMoney) {
        this.hours = hours;
        this.availableRoutes = availableRoutes;
        this.entries = entries;
        this.prizeMoney = prizeMoney;
    }

    /**
     * Gets the number of hours the race lasts.
     * @return the race duration in hours
     */
    public int getRaceHours() {
        return hours;
    }

    /**
     * Gets the list of available {@code Route} objects for this race.
     * @return the list of available routes
     */
    public List<Route> getAvailableRoutes() {
        return availableRoutes;
    }

    /**
     * Gets the number of entries in the race.
     * @return the number of entries in the race
     */
    public int getRaceEntries() {
        return entries;
    }

    /**
     * Gets the prize money awarded for winning the race.
     * @return the prize money amount
     */
    public int getRacePrizeMoney() {
        return prizeMoney;
    }

    /**
     * Sets the maximum prize money for the race.
     *
     * @param prizeMoney the total prize money for first place (must be non-negative)
     */
    public void setRacePrizeMoney(int prizeMoney) {
        this.prizeMoney = prizeMoney;
    }

    /**
     * Gets a comma-separated list of unique route types (descriptions)
     * involved in the race.
     * @return a (@code StringBuilder) containing the unique route types
     */
    public StringBuilder getRouteList() {

        StringBuilder routeString = new StringBuilder();
        Set<Route.RouteType> uniqueTypes = new HashSet<>();

        for (Route route : availableRoutes) {
            uniqueTypes.add(route.getRouteDescription());
        }

        int count = 0;
        for (Route.RouteType type : uniqueTypes) {
            routeString.append(type);
            if (++count < uniqueTypes.size()) {
                routeString.append(", ");
            }
        }
        return routeString;
    }
}
