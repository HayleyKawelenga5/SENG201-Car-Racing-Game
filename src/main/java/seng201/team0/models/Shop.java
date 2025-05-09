package seng201.team0.models;

import java.util.ArrayList;
import java.util.List;

import seng201.team0.models.Car;
import seng201.team0.models.Upgrade;

import seng201.team0.services.CarService;
import seng201.team0.services.UpgradeService;

/**
 * Represents a shop where the player can buy and sell cars and upgrades.
 * The shop generates a list of random items to purchase and keeps track of the player's inventory and money.
 */
public class Shop {

    private List<Car> availableCars = new ArrayList<>();
    private List<Upgrade> availableUpgrades = new ArrayList<>();

    private List<Car> playerCars = new ArrayList<>();
    private List<Upgrade> playerUpgrades = new ArrayList<>();

    private CarService carService;
    private UpgradeService upgradeService;

    private int money;

    /**
     * Constructs a new Shop instance and generates an initial inventory.
     *
     * @param money The amount of money the player has.
     * @param carService the service used to handle car-related operations
     * @param upgradeService the service used to handle upgrade-related operations
     */
    public Shop(int money, CarService carService, UpgradeService upgradeService) {
        this.money = money;
        this.carService = carService;
        this.upgradeService = upgradeService;
    }

    /**
     * Returns the amount of money the player currently has.
     *
     * @return the player's available money
     */
    public int getMoney() {
        return money;
    }

    /**
     * Returns the list of available cars for sale in the shop.
     *
     * @return a list of {@code Car} objects that are available to purchase
     */
    public List<Car> getAvailableCars() {
        return availableCars;
    }

    /**
     * Returns the list of available upgrades for sale in the shop.
     *
     * @return a list of {@code Upgrade} objects that are available to purchase
     */
    public List<Upgrade> getAvailableUpgrades() {
        return availableUpgrades;
    }

    /**
     * Returns the list of cars the player currently owns.
     *
     * @return a list of {@code Car} objects that the player has purchased
     */
    public List<Car> getPlayerCars() {
        return playerCars;
    }

    /**
     * Returns the list of upgrades the player currently owns.
     *
     * @return a list of {@code Upgrade} objects that the player has purchased
     */
    public List<Upgrade> getPlayerUpgrades() {
        return playerUpgrades;
    }

}
