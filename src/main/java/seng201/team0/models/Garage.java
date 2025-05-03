package seng201.team0.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the player's garage where cars are stored,
 * parts can be installed, and the current car can be managed.
 */
public class Garage {

    private Car currentCar;
    private List<Car> playerCars;
    private List<Upgrade> availableUpgrades;

    /**
     * Constructs a Garage with a selected current car,
     * a list of reserve cars, and upgrades.
     *
     * @param currentCar       The car selected for the next race.
     * @param reserveCars      The list of all backup cars.
     * @param availableUpgrades The list of upgrades available to be installed.
     */
    public Garage(Car currentCar, List<Car> playerCars, ArrayList<Upgrade> availableUpgrades) {
        this.playerCars = playerCars;
        this.currentCar = currentCar;
        this.availableUpgrades = availableUpgrades;
    }

    public Car getCurrentCar() {
        return currentCar;
    }

    public List<Car> getPlayerCars() {
        return playerCars;
    }

    public List<Upgrade> getAvailableUpgrades() {
        return availableUpgrades;
    }

}
