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

    private int playerMoney;
    private final int MAX_CARS = 5;

    /**
     * Creates a new Shop instance with a starting amount of money and generates an initial inventory.
     *
     * @param startingMoney The amount of money the player starts with.
     */
    public Shop(int startingMoney, CarService carService, UpgradeService upgradeService) {
        this.playerMoney = startingMoney;
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

        for (int i = 0; i < 5; i++) {
            //availableCars.add(carService.generateRandomCars());
            availableUpgrades.add(upgradeService.generateRandomUpgrade());
        }
    }

    /**
     * Returns the player's current amount of money.
     *
     * @return The player's money.
     */
    public int getPlayerMoney() {
        return playerMoney;
    }

    /**
     * Gets the list of cars currently available in the shop.
     *
     * @return A list of available cars.
     */
    public List<Car> getAvailableCars() {
        return availableCars;
    }

    /**
     * Gets the list of upgrades currently available in the shop.
     *
     * @return A list of available upgrades.
     */
    public List<Upgrade> getAvailableUpgrades() {
        return availableUpgrades;
    }
 //NEED TO CHANGE THIS TO ALSO INCLUDE STATS
    /**
     * Gets the list of cars currently owned by the player.
     *
     * @return A list of the player's cars.
     */
    public List<Car> getPlayerCars() {
        return playerCars;
    }

    /**
     * Gets the list of upgrades currently owned by the player.
     *
     * @return A list of the player's upgrades.
     */
    public List<Upgrade> getPlayerUpgrades() {
        return playerUpgrades;
    }



}
