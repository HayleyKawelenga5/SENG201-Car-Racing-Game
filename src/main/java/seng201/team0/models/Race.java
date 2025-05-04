package seng201.team0.models;

import java.util.*;

public class Race {

    private int hours;
    private List<Route> routes;
    private int entries;
    private int prizeMoney;

    public Race(int hours, List<Route> routes, int entries, int prizeMoney) {
        this.hours = hours;
        this.routes = routes;
        this.entries = entries;
        this.prizeMoney = prizeMoney;
    }

    public int getHours() {
        return hours;
    }

    public StringBuilder getRoutes() {
        StringBuilder routeString = new StringBuilder();
        Set<Route.RouteType> uniqueTypes = new HashSet<>();

        for (Route route : routes) {
            uniqueTypes.add(route.getDescription());
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

    public int getEntries() {
        return entries;
    }

    public int getPrizeMoney() {
        return prizeMoney;
    }


}
