package seng201.team0.models;

import java.util.ArrayList;

/**
 * Represents the player's garage where cars are stored,
 * parts can be installed, and the current car can be managed.
 */
public class Garage {
    private Car currentCar;
    private ArrayList<Car> reserveCars;
    private ArrayList<Upgrade> availableUpgrades;

    /**
     * Constructs a Garage with a selected current car,
     * a list of reserve cars, and upgrades.
     *
     * @param currentCar       The car selected for the next race.
     * @param reserveCars      The list of all backup cars.
     * @param availableUpgrades The list of upgrades available to be installed.
     */
    public Garage(Car currentCar, ArrayList<Car> reserveCars, ArrayList<Upgrade> availableUpgrades) {
        this.currentCar = currentCar;
        this.reserveCars = reserveCars;
        this.availableUpgrades = availableUpgrades;
    }

    /**
     * Returns the car selected for the next race.
     *
     * @return The current car.
     */
    public Car getCurrentCar() {
        return currentCar;
    }

    /**
     * Returns a list of the player's reserve cars.
     *
     * @return List of reserve cars.
     */
    public ArrayList<Car> getReserveCars() {
        return reserveCars;
    }

    /**
     * Returns the list of uninstalled upgrades.
     *
     * @return List of available upgrades.
     */
    public ArrayList<Upgrade> getAvailableUpgrades() {
        return availableUpgrades;
    }


}
