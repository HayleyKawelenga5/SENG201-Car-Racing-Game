package seng201.team0.models;

import java.util.*;

public class Race {

    private int hours;
    private List<Route> availableRoutes;
    private int entries;
    private int prizeMoney;

    public Race(int hours, List<Route> availableRoutes, int entries, int prizeMoney) {
        this.hours = hours;
        this.availableRoutes = availableRoutes;
        this.entries = entries;
        this.prizeMoney = prizeMoney;
    }

    public int getRaceHours() {
        return hours;
    }

    public List<Route> getAvailableRoutes() {
        return availableRoutes;
    }

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

    public int getRaceEntries() {
        return entries;
    }

    public int getRacePrizeMoney() {
        return prizeMoney;
    }

}
