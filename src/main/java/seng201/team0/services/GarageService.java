package seng201.team0.services;

import seng201.team0.models.Car;
import seng201.team0.models.Upgrade;

import java.util.ArrayList;
import java.util.List;

public class GarageService {

    private Car currentCar;
    private List<Car> playerCars;
    private List<Upgrade> playerUpgrades;

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
    public boolean installUpgrade(Car currentCar, Upgrade upgrade, List<Upgrade> playerUpgrades) {
        if (currentCar == null || upgrade == null || !playerUpgrades.contains(upgrade)) {
            return false;
        }

        currentCar.setCarSpeed(Math.min(100, currentCar.getCarSpeed() + upgrade.getUpgradeSpeed()));
        currentCar.setCarHandling(Math.min(100, currentCar.getCarHandling() + upgrade.getUpgradeHandling()));
        currentCar.setCarReliability(Math.min(100, currentCar.getCarReliability() + upgrade.getUpgradeReliability()));
        currentCar.setCarFuelEconomy(Math.min(100, currentCar.getCarFuelEconomy() + upgrade.getUpgradeFuelEconomy()));
        currentCar.setCarCost(currentCar.getCarCost() + upgrade.getUpgradeCost());
        playerUpgrades.remove(upgrade);
        return true;
    }

}
