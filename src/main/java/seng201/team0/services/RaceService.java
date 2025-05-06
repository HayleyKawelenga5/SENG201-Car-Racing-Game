package seng201.team0.services;

import seng201.team0.models.Car;
import seng201.team0.models.Race;
import seng201.team0.models.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RaceService {

    private RouteService routeService = new RouteService();
    private Random random = new Random();
    private String gameDifficulty;
    private int maxPrizeMoney;

    public RaceService() {
        this.routeService = new RouteService();
    }

    public Race generateRandomRace(String gameDifficulty) {
        int hours = random.nextInt(2, 6);
        int entries = random.nextInt(3,9);

        if (gameDifficulty.equals("EASY")) {
            maxPrizeMoney = (hours + entries) * 50;
        } else if (gameDifficulty.equals("HARD")) {
            maxPrizeMoney = (hours + entries) * 30;
        }

        int numberOfRoutes = random.nextInt(1,4);
        List<Route> routes = new ArrayList<>();
        for (int i = 0; i < numberOfRoutes; i++) {
            routes.add(routeService.generateRandomRoute());
        }
       return new Race(hours, routes, entries, maxPrizeMoney);
    }

    public List<Race> generateRaces(int numberOfRaces, String gameDifficulty) {
        List<Race> races = new ArrayList<>();
        for (int i = 0; i < numberOfRaces; i++) {
            races.add(generateRandomRace(gameDifficulty));
        }
        return races;
    }

    public Car applyMultipliers(Car car, double multiplier){
        car.setCarSpeed((int)(car.getCarSpeed() / multiplier));
        car.setCarHandling((int)(car.getCarHandling() / multiplier));
        car.setCarReliability((int)(car.getCarReliability() / multiplier));
        car.setCarFuelEconomy((int)(car.getCarFuelEconomy() / multiplier));
        return car;
    }
}
