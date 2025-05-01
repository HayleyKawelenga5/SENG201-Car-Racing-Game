package seng201.team0.services;

import seng201.team0.models.Car;
import seng201.team0.models.Upgrade;

import java.util.ArrayList;
import java.util.List;

public class ShopService {
    private List<Car> playerCars = new ArrayList<>();
    private List<Upgrade> playerUpgrades = new ArrayList<>();
    private int money;
    private final int MAX_CARS = 5;
    private CarService carService;

    /**
     * Attempts to buy a car for the player. Fails if player has max cars or not enough money.
     *
     * @param car The car to buy.
     * @return True if the purchase was successful, false otherwise.
     */
    public boolean buyCar(Car car) {
        if (playerCars.size() >= MAX_CARS || money < car.getCost()) {
            return false;
        }
        money -= car.getCost();
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
            money += car.getCost() / 2;
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
        if (money < upgrade.getCost()) {
            return false;
        }
        money -= upgrade.getCost();
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
            money += upgrade.getCost() / 2;
            return true;
        }
        return false;
    }

    /**
     * Returns a list of available cars. If no cars have been generated yet,
     * this method will generate them first.
     *
     * @return A list of available Car objects.
     */
    public List<Car> getCarsForPurchase() {
        if (carService.getAvailableCars().isEmpty()) {
            carService.generateRandomCars(3);
        }
        return carService.getAvailableCars();
    }

}
