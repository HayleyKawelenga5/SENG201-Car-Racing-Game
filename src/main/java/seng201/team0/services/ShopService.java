package seng201.team0.services;

import seng201.team0.models.Car;
import seng201.team0.models.Upgrade;

import java.util.ArrayList;
import java.util.List;

public class ShopService {
    private List<Car> playerCars = new ArrayList<>();
    private List<Upgrade> playerUpgrades = new ArrayList<>();
    private int playerMoney;
    private final int MAX_CARS = 5;


    /**
     * Attempts to buy a car for the player. Fails if player has max cars or not enough money.
     *
     * @param car The car to buy.
     * @return True if the purchase was successful, false otherwise.
     */
    public boolean buyCar(Car car) {
        if (playerCars.size() >= MAX_CARS || playerMoney < car.getCost()) {
            return false;
        }
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
        if (playerMoney < upgrade.getCost()) {
            return false;
        }
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
