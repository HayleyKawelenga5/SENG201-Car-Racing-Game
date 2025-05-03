package seng201.team0.services;

import seng201.team0.models.Car;
import seng201.team0.models.Upgrade;

import java.util.ArrayList;
import java.util.List;

public class GarageService {

    private Car currentCar;
    private List<Car> playerCars;
    private List<Upgrade> availableUpgrades;

    /**
     * Swaps the current car with one from the reserve.
     *
     * @param car The reserve car to make current.
     * @return True if successful, false otherwise.
     */
    public boolean swapCurrentCar(Car car) {
        if (playerCars.contains(car)) {
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
            currentCar.setCarSpeed(currentCar.getCarSpeed() + upgrade.getUpgradeSpeed());
            currentCar.setCarHandling(currentCar.getCarHandling() + upgrade.getUpgradeHandling());
            currentCar.setCarReliability(currentCar.getCarReliability() + upgrade.getUpgradeReliability());
            currentCar.setCarFuelEconomy(currentCar.getCarFuelEconomy() + upgrade.getUpgradeFuelEconomy());
            return true;
        }
        return false;
    }

}
