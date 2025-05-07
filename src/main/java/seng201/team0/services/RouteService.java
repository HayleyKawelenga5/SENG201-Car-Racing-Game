package seng201.team0.services;

import seng201.team0.models.Route;
import seng201.team0.models.Route.RouteType;

import java.util.Random;

public class RouteService {

    /**
     * Returns a randomly selected RouteType}.
     *
     * @return a random RouteType from the available enum.
     */
    public RouteType getRandomRouteType() {
        RouteType[] types = Route.RouteType.values();
        return types[new Random().nextInt(types.length)];
    }

    /**
     * Generates a random Route with attributes based on the randomly selected RouteType.
     *
     * @return a randomly generated Route object.
     */
    public Route generateRandomRoute() {
        Random random = new Random();

        int distance = 0;
        double difficultyMultiplier = 0.0;

        RouteType description = getRandomRouteType();

        switch (description) {
            case FLAT:
                distance = random.nextInt(60, 150);
                difficultyMultiplier = random.nextDouble(1.0, 1.4);
                break;
            case HILLY:
                distance = random.nextInt(40, 150);
                difficultyMultiplier = random.nextDouble(1.0, 1.6);
                break;
            case OFFROAD:
                distance = random.nextInt(40, 150);
                difficultyMultiplier = random.nextDouble(1.0, 1.8);
                break;
            case WINDY:
                distance = random.nextInt(40, 150);
                difficultyMultiplier = random.nextDouble(1.0, 1.8);
                break;
        }

        int fuelStops = random.nextInt(1, 5);

        return new Route(description, distance, fuelStops, difficultyMultiplier);
    }

}