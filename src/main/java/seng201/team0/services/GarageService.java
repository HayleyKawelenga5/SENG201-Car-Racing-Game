package seng201.team0.services;

import seng201.team0.models.Car;
import seng201.team0.models.Upgrade;

import java.util.List;

/**
 * The {@code GarageService} class provides functionality for managing and upgrading cars
 * in the player's garage. It handles the installation of upgrades which modify car stats,
 * ensuring that upgrades are only applied if they are owned by the player.
 */
public class GarageService {

    /**
     * Installs an upgrade to a car to improve its stats. This method ensures that the car's speed, handling, reliability
     * and fuel economy do not go above 100 as this is the max value for these attributes.
     * @param currentCar the car that the upgrade is being installed on
     * @param upgrade the upgrade to be installed
     * @param playerUpgrades the list of upgrades owned by the player
     * @return returns true if the upgrade was installed successfully and false otherwise
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
        currentCar.addToCarUpgrades(upgrade);
        playerUpgrades.remove(upgrade);
        return true;
    }

}
