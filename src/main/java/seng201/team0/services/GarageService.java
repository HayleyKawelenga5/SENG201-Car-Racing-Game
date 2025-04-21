package seng201.team0.services;

import seng201.team0.models.Car;
import seng201.team0.models.Upgrade;
import java.util.ArrayList;

public class GarageService {
    private ArrayList<Car> reserveCars;
    private Car currentCar;
    private ArrayList<Upgrade> availableUpgrades;

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
