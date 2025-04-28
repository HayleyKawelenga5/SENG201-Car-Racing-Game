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
    private final int MAX_CARS = 5;

    /**
     * Creates a new Shop instance and generates an initial inventory.
     *
     * @param money The amount of money the player has.
     */
    public Shop(int money, CarService carService, UpgradeService upgradeService) {
        this.money = money;
        this.carService = carService;
        this.upgradeService = upgradeService;
        generateInventory();
    }

    /**
     * Generates a new set of cars and upgrades for the shop to sell.
     * Clears any existing inventory before generating new items.
     */
    private void generateInventory() {
        availableCars.clear();
        availableUpgrades.clear();
        for (int i = 0; i < 3; i++) {
            availableCars.add(carService.generateRandomCars());
            availableUpgrades.add(upgradeService.generateRandomUpgrade());
        }
    }

    public int getMoney() {
        return money;
    }

    public List<Car> getAvailableCars() {
        return availableCars;
    }

    public List<Upgrade> getAvailableUpgrades() {
        return availableUpgrades;
    }

    public List<Car> getPlayerCars() {
        return playerCars;
    }

    public List<Upgrade> getPlayerUpgrades() {
        return playerUpgrades;
    }

}
