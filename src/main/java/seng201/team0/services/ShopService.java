package seng201.team0.services;

import seng201.team0.GameManager;

import seng201.team0.models.Car;
import seng201.team0.models.Upgrade;

/**
 * Handles the buying and selling of cars and upgrades from the shop
 */
public class ShopService {

    /**
     * The maximum number of cars a player can own in the garage.
     */
    private static final int MAX_CARS = 5;

    /**
     * The maximum number of upgrades that the player can own in the garage.
     */
    private static final int MAX_UPGRADES = 3;

    /**
     * The minimum number of cars a player must own to participate in a race.
     */
    private static final int MIN_CARS = 1;

    /**Attempts to buy a car for the player. Fails if player has max cars or not enough money.
     *
     * @param car the car to be bought (Type Car)
     * @param shopScreen the game manager (Type GameManager)
     * @return true if the car is successfully bought
     */
    public boolean buyCar(Car car, GameManager shopScreen) {
        if (shopScreen.getMoney() >= car.getCarCost() && shopScreen.getPlayerCars().size() < 5) {
            shopScreen.setMoney(shopScreen.getMoney() - car.getCarCost());
            shopScreen.getPlayerCars().add(car);
            return true;
        }
        return false;
    }

    /**
     * Sells a car from the player's inventory for half its original value.
     *
     * @param car The car to sell (Type Car).
     * @param shopScreen the game manager (Type GameManager)
     * @return True if the car was sold, false if the car was not found.
     */
    public boolean sellCar(Car car, GameManager shopScreen) {
        if (shopScreen.getPlayerCars().contains(car)) {
            shopScreen.setMoney(shopScreen.getMoney() + (car.getCarCost() / 2));
            shopScreen.getPlayerCars().remove(car);
            return true;
        }
        return false;
    }

    /**
     * Attempts to buy an upgrade for the player.
     *
     * @param upgrade The upgrade to buy (Type Upgrade)
     * @param shopScreen the game manager (Type GameManager).
     * @return True if the purchase was successful, false otherwise.
     */
    public boolean buyUpgrade(Upgrade upgrade, GameManager shopScreen) {
        if (shopScreen.getMoney() >= upgrade.getUpgradeCost() && shopScreen.getPlayerUpgrades().size() < 3) {
            shopScreen.setMoney(shopScreen.getMoney() - upgrade.getUpgradeCost());
            shopScreen.getPlayerUpgrades().add(upgrade);
            return true;
        }
        return false;
    }

    /**
     * Sells an upgrade from the player's inventory for half its original value.
     *
     * @param upgrade The upgrade to sell (Type Upgrade)
     * @param shopScreen the game manager (Type GameManager).
     * @return True if the upgrade was sold, false otherwise.
     */
    public boolean sellUpgrade(Upgrade upgrade, GameManager shopScreen) {
        if (shopScreen.getPlayerUpgrades().contains(upgrade)) {
            shopScreen.setMoney(shopScreen.getMoney() + (upgrade.getUpgradeCost() / 2));
            shopScreen.getPlayerUpgrades().remove(upgrade);
            return true;
        }
        return false;
    }

    /**
     * Returns the maximum number of cars a player can own.
     *
     * @return The maximum allowed number of cars.
     */
    public int getMaxMoney() {
        return MAX_CARS;
    }

    /**
     * Returns the minimum number of cars required.
     *
     * @return The minimum number of cars.
     */
    public int getMinCars(){
        return MIN_CARS;
    }

    /**
     * Returns the maximum number of upgrades that can be applied to a car.
     *
     * @return The maximum allowed upgrades per car.
     */
    public int getMaxUpgrades() {
        return MAX_UPGRADES;
    }

}
