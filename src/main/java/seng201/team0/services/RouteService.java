package seng201.team0.services;

import seng201.team0.models.Route;
import seng201.team0.models.Route.RouteType;

import java.util.Random;

/**
 * Handles the generation of random routes for the race, as well as their difficulty multipliers to be applied to
 * different stats of the player's car depending on the RouteType.
 */
public class RouteService {

    /**
     * Returns a randomly selected RouteType.
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

        int distance;
        double difficultyMultiplier;

        RouteType description = getRandomRouteType();

        difficultyMultiplier = switch (description) {
            case FLAT -> {
                distance = random.nextInt(60, 151);
                yield random.nextDouble(1.0, 1.41);
            }
            case HILLY -> {
                distance = random.nextInt(60, 151);
                yield random.nextDouble(1.2, 1.61);
            }
            case OFFROAD -> {
                distance = random.nextInt(60, 151);
                yield random.nextDouble(1.2, 1.61);
            }
            case WINDY -> {
                distance = random.nextInt(60, 151);
                yield random.nextDouble(1.2, 1.61);
            }
            case BEACH -> {
                distance = random.nextInt(60, 151);
                yield random.nextDouble(1.4, 1.81);
            }
        };

        int fuelStops = random.nextInt(1, 5);

        return new Route(description, distance, fuelStops, difficultyMultiplier);
    }

}