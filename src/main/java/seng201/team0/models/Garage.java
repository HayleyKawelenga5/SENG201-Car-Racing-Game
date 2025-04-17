package seng201.team0.models;

import java.util.ArrayList;

/**
 * Represents the player's garage where cars are stored,
 * parts can be installed, and the current car can be managed.
 */
public class Garage {
    private Car currentCar;
    private List<Car> reserveCars;
    private List<Upgrade> availableUpgrades;

    /**
     * Constructs a Garage with a selected current car,
     * a list of reserve cars, and upgrades.
     *
     * @param currentCar       The car selected for the next race.
     * @param reserveCars      The list of all backup cars.
     * @param availableUpgrades The list of upgrades available to be installed.
     */
    public Garage(Car currentCar, List<Car> reserveCars, List<Upgrade> availableUpgrades) {
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
    public List<Car> getReserveCars() {
        return reserveCars;
    }

    /**
     * Returns the list of uninstalled upgrades.
     *
     * @return List of available upgrades.
     */
    public List<Upgrade> getAvailableUpgrades() {
        return availableUpgrades;
    }

    /**
     * Swaps the current car with one from the reserve.
     *
     * @param car The reserve car to make current.
     * @return True if successful, false otherwise.
     */
    public boolean swapCurrentCar(Car car) {
        if (reserveCars.contains(car)) {
            reserveCars.remove(car);
            reserveCars.add(currentCar);
            currentCar = car;
            return true;
        }
        return false;
    }

    /**
     * Installs an upgrade to the current car, improving its stats.
     *
     * @param upgrade The upgrade to install.
     * @return True if installed, false if not in list.
     */
    public boolean installUpgrade(Upgrade upgrade) {
        if (availableUpgrades.remove(upgrade)) {
            currentCar.setSpeed(currentCar.getSpeed() + upgrade.getSpeedUpgrade());
            currentCar.setHandling(currentCar.getHandling() + upgrade.getHandlingUpgrade());
            currentCar.setReliability(currentCar.getReliability() + upgrade.getReliabilityUpgrade());
            currentCar.setFuelEconomy(currentCar.getFuelEconomy() + upgrade.getFuelEconomyUpgrade());
            return true;
        }
        return false;
    }
}
