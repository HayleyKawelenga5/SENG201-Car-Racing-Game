package seng201.team0.services;

import seng201.team0.models.Car;
import seng201.team0.models.Race;
import seng201.team0.models.Route;
import seng201.team0.models.Upgrade;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Provides services for generating races, applying multipliers to cars,
 * and managing car performance changes.
 */
public class RaceService {

    private Random random = new Random();

    private RouteService routeService = new RouteService();
    private String gameDifficulty;
    private int maxPrizeMoney;

    /**
     * Constructs a new RaceService with a default RouteService instance.
     */
    public RaceService() {
        this.routeService = new RouteService();
    }

    /**
     * Generates a single random race based on the game difficulty.
     * Difficulty affects the prize money.
     *
     * @param gameDifficulty The difficulty level of the game ("EASY" or "HARD").
     * @return A randomly generated {@link Race} instance.
     */
    public Race generateRandomRace(String gameDifficulty) {

        int hours = random.nextInt(3, 9);
        int entries = random.nextInt(3,9);

        if (gameDifficulty.equals("EASY")) {
            maxPrizeMoney = (hours + entries) * 40;
        } else if (gameDifficulty.equals("HARD")) {
            maxPrizeMoney = (hours + entries) * 20;
        }

        int numberOfRoutes = random.nextInt(1,4);
        List<Route> availableRoutes = new ArrayList<>();
        for (int i = 0; i < numberOfRoutes; i++) {
            availableRoutes.add(routeService.generateRandomRoute());
        }
       return new Race(hours, availableRoutes, entries, maxPrizeMoney);
    }

    /**
     * Generates a list of random races based on the number of races and game difficulty.
     *
     * @param numberOfRaces  The number of races to generate.
     * @param gameDifficulty The difficulty level of the game.
     * @return A list of randomly generated {@link Race} instances.
     */
    public List<Race> generateRaces(int numberOfRaces, String gameDifficulty) {
        List<Race> availableRaces = new ArrayList<>();
        for (int i = 0; i < numberOfRaces; i++) {
            availableRaces.add(generateRandomRace(gameDifficulty));
        }
        return availableRaces;
    }

    /**
     * Applies a performance multiplier to the given car's stats (speed, handling, reliability, fuel economy).
     *
     * @param car        The car to modify.
     * @param multiplier The performance multiplier.
     * @return The modified {@link Car} with updated stats.
     */
    public Car applyMultipliers(Car car, double multiplier) {
        car.setCarSpeed((int)(car.getCarSpeed() / multiplier));
        car.setCarHandling((int)(car.getCarHandling() / multiplier));
        car.setCarReliability((int)(car.getCarReliability() / multiplier));
        car.setCarFuelEconomy((int)(car.getCarFuelEconomy() / multiplier));
        return car;
    }

    /**
     * Creates a preview version of the car with performance stats affected by a multiplier,
     * without modifying the original car.
     *
     * @param car        The original car to preview changes for.
     * @param multiplier The performance multiplier.
     * @return A new {@link Car} instance with adjusted stats for preview.
     */
    public Car previewMultiplier(Car car, double multiplier) {
        int previewSpeed = (int) (car.getCarSpeed() / multiplier);
        int previewHandling = (int) (car.getCarHandling() / multiplier);
        int previewReliability = (int) (car.getCarReliability() / multiplier);
        int previewFuelEconomy = (int) (car.getCarFuelEconomy() / multiplier);
        int previewCost = car.getCarCost();
        List<Upgrade> upgrades = car.getUpgrades();
        Car previewCar = new Car(previewSpeed, previewHandling, previewReliability, previewFuelEconomy, previewCost, upgrades);
        return previewCar;
    }

    /**
     * Creates a deep copy of the given car, including all performance stats and upgrades.
     *
     * @param car The original car to copy.
     * @return A new {@link Car} instance that is a copy of the original.
     */
    public Car copyCar(Car car) {
        int copySpeed = car.getCarSpeed();
        int copyHandling = car.getCarHandling();
        int copyReliability = car.getCarReliability();
        int copyFuelEconomy = car.getCarFuelEconomy();
        int copyCost = car.getCarCost();
        List<Upgrade> copyUpgrades = car.getUpgrades();
        Car copyCar = new Car(copySpeed, copyHandling, copyReliability, copyFuelEconomy, copyCost, copyUpgrades);
        copyCar.setCarName(car.getCarName());
        return copyCar;
    }

}
