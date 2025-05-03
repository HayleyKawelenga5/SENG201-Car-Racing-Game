package seng201.team0.models;

import java.util.List;

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

    public List<Route> getRoutes() {
        return routes;
    }

    public int getEntries() {
        return entries;
    }

    public int getPrizeMoney() {
        return prizeMoney;
    }


}
