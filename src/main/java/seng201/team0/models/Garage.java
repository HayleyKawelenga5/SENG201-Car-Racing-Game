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
     * a list of owned cars, and upgrades.
     *
     * @param currentCar       The car selected for the next race.
     * @param playerCars      The list of all other cars owned by the player.
     * @param availableUpgrades The list of upgrades available to be installed.
     */
    public Garage(Car currentCar, List<Car> playerCars, ArrayList<Upgrade> availableUpgrades) {
        this.playerCars = playerCars;
        this.currentCar = currentCar;
        this.availableUpgrades = availableUpgrades;
    }

    /**
     * Gets the user's current car.
     * @return the currently selected car
     */
    public Car getCurrentCar() {
        return currentCar;
    }

    /**
     * Gets the list of cars owned by the player.
     * @return the list of cars owned by the player
     */
    public List<Car> getPlayerCars() {
        return playerCars;
    }

    /**
     * Gets the list of upgrades available to the user.
     * @return the list of available upgrades
     */
    public List<Upgrade> getAvailableUpgrades() {
        return availableUpgrades;
    }

}
