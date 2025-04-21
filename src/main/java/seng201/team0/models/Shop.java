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
            availableCars.add(carService.generateRandomCars());
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

    /**
     * Attempts to buy a car for the player. Fails if player has max cars or not enough money.
     *
     * @param car The car to buy.
     * @return True if the purchase was successful, false otherwise.
     */
    public boolean buyCar(Car car) {
        if (playerCars.size() >= MAX_CARS || playerMoney < car.getCost()) return false;
        playerMoney -= car.getCost();
        playerCars.add(car);
        return true;
    }

    /**
     * Sells a car from the player's inventory for half its original value.
     *
     * @param car The car to sell.
     * @return True if the car was sold, false if the car was not found.
     */
    public boolean sellCar(Car car) {
        if (playerCars.remove(car)) {
            playerMoney += car.getCost() / 2;
            return true;
        }
        return false;
    }

    /**
     * Attempts to buy an upgrade for the player.
     *
     * @param upgrade The upgrade to buy.
     * @return True if the purchase was successful, false otherwise.
     */
    public boolean buyUpgrade(Upgrade upgrade) {
        if (playerMoney < upgrade.getCost()) return false;
        playerMoney -= upgrade.getCost();
        playerUpgrades.add(upgrade);
        return true;
    }

    /**
     * Sells an upgrade from the player's inventory for half its original value.
     *
     * @param upgrade The upgrade to sell.
     * @return True if the upgrade was sold, false otherwise.
     */
    public boolean sellUpgrade(Upgrade upgrade) {
        if (playerUpgrades.remove(upgrade)) {
            playerMoney += upgrade.getCost() / 2;
            return true;
        }
        return false;
    }

}
