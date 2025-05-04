package seng201.team0.services;

import seng201.team0.models.Race;
import seng201.team0.models.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RaceService {

    private RouteService routeService = new RouteService();
    private Random random = new Random();

    public RaceService() {
        this.routeService = new RouteService();
    }

    public Race generateRandomRace() {
        int hours = random.nextInt(2, 6);
        int entries = random.nextInt(3,9);
        int prizeMoney = (new Random().nextInt(9) + 2) * 100;

        int numberOfRoutes = random.nextInt(1,4);
        List<Route> routes = new ArrayList<>();
        for (int i = 0; i < numberOfRoutes; i++) {
            routes.add(routeService.generateRandomRoute());
        }
       return new Race(hours, routes, entries, prizeMoney);
    }

    public List<Race> generateRaces(int numberOfRaces){
        List<Race> races = new ArrayList<>();
        for (int i = 0; i < numberOfRaces; i++) {
            races.add(generateRandomRace());
        }
        return races;
    }
}
