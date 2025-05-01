package seng201.team0.services;

import seng201.team0.models.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Service class for managing and generating car objects.
 * Provides functionality to generate a list of random cars with attributes,
 * and to retrieve the current list of available cars.
 */
public class CarService {

    private List<Car> availableCars = new ArrayList<>();

    /**
     * Generates a random car with random stats within the given range.
     * The cost is calculated based on the sum of the car's attributes.
     *
     * @return A list of Car objects with randomly generated stats.
     */
    public List<Car> generateRandomCars(int maxValue) {
        availableCars.clear();
        Random random = new Random();

        for (int i = 0; i < maxValue; i++) {
            int speed = random.nextInt(1, 11);
            int handling = random.nextInt(1, 11);
            int reliability = random.nextInt(1, 11);
            int fuelEconomy = random.nextInt(1, 11);
            int cost = (speed + handling + reliability + fuelEconomy) * 10;
            availableCars.add(new Car(speed, handling, reliability, fuelEconomy, cost));
        }
        return availableCars;
    }

    public List<Car> getAvailableCars() { return availableCars; }

}