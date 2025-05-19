package seng201.team0.services;

import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.List;

import seng201.team0.models.Car;

/**
 * Handles game initialisation, including player name, difficulty, money, car selection, and season length.
 */
public class GameInitializer {

    /** The amount of money the player has. */
    private int money;

    /** The list of cars owned by the player. */
    private List<Car> playerCars = new ArrayList<>();

    /** Service class used to manage car-related operations such as generation of cars. */
    private final CarService carService = new CarService();

    /** Maximum number of cars a player can own at the start of the game. */
    private static final int MAX_CARS = 3;

    /** Minimum number of races allowed in a season. */
    private static final int MIN_SEASON_LENGTH = 5;

    /** Maximum number of races allowed in a season. */
    private static final int MAX_SEASON_LENGTH = 15;

    /** Minimum length for a valid player name. */
    private static final int MIN_NAME_LENGTH = 3;

    /** Maximum length for a valid player name. */
    private static final int MAX_NAME_LENGTH = 15;

    /**
     * Validates and sets the player's name. For a name to be valid the length of the name must be between 3 and 15
     * characters inclusive and the name must not include any special characters.
     *
     * @param name The player's name. (String)
     * @throws InvalidNameException if the name is invalid.
     */
    public void selectName(String name) throws InvalidNameException {
        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH || !name.matches("[a-zA-Z0-9 ]+")){
            throw new InvalidNameException("Player name must be between 3 and 15 characters and contain no special characters.");
        }
    }

    /**
     * Sets the season length if within valid range.
     *
     * @param length Number of races. (int)
     * @throws IllegalArgumentException if length is outside 5–15.
     */
    public void selectSeasonLength(int length) throws IllegalArgumentException {
        if (length < MIN_SEASON_LENGTH || length > MAX_SEASON_LENGTH) {
            throw new IllegalArgumentException("Season length must be between 5 and 15 races");
        }
    }

    /**
     * Sets the difficulty and adjusts starting money accordingly.
     *
     * @param difficulty The selected difficulty ("EASY" or "HARD"). (String)
     * @throws IllegalArgumentException if difficulty is invalid.
     */
    public void selectDifficulty(String difficulty) {
        switch (difficulty) {
            case "EASY":
                this.money = 600;
                break;
            case "HARD":
                this.money = 400;
                break;
            default:
                throw new IllegalArgumentException("Invalid difficulty. Please select either EASY or HARD.");
        }
    }

    /**
     * Gets the amount of player money.
     * @return the amount of money for the player.
     */
    public int getMoney() { return money; }

    /**
     * Sets the player's money to the given value.
     * @param money amount for the player's money to be set to (int)
     */
    public void setMoney(int money) { this.money = money; }

    /**
     * Returns a list of available cars. If no cars have been generated yet,
     * this method will generate them first.
     *
     * @return A list of available Car objects.
     */
    public List<Car> getAvailableCars() {
        if (carService.getAvailableCars().isEmpty()) {
            carService.generateRandomCars(5);
        }
        return carService.getAvailableCars();
    }

    /**
     * Gets the list of cars owned by the player.
     * @return a list of cars owned by the player
     */
    public List<Car> getPlayerCars() { return playerCars; }

    /**
     * Sets the player's cars to the given list.
     * @param playerCars list of cars for the player's cars to be set to. (List containing objects of type Car)
     */
    public void setPlayerCars(List<Car> playerCars) { this.playerCars = playerCars; }

    /**
     * Attempts to add a car to the player's selection.
     * A car can only be added if the player does not already have it,
     * has fewer than the maximum amount of cars allowed (3) selected cars, and has enough money to purchase it.
     *
     * @param car The Car to add to the selection. (Car)
     * @return true if the car was successfully added, false otherwise.
     */
    public boolean addCar(Car car) {
        if (!playerCars.contains(car) && playerCars.size() < MAX_CARS) {
            if (money >= car.getCarCost()) {
                playerCars.add(car);
                money -= car.getCarCost();
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Removes a car from the player's selection and refunds its cost.
     * If the car is not in the selected list, this method does nothing.
     *
     * @param car The Car to remove from the selection. (Car)
     *
     * @return true if the car was successfully deleted from the player's inventory and false otherwise
     */
    public boolean deleteCar(Car car) {
        if (playerCars.contains(car)) {
            playerCars.remove(car);
            money += car.getCarCost();
            return true;
        }
        return false;
    }

    /**
     * Gets the maximum number of cars a player can have at the start of the game.
     * @return the maximum number of cars
     */
    public int getMaxCars() {
        return MAX_CARS;
    }

    /**
     * Gets the minimum season length.
     * @return the minimum season length.
     */
    public int getMinSeasonLength() {
        return MIN_SEASON_LENGTH;
    }

    /**
     * Gets the maximum season length
     * @return the maximum season length
     */
    public int getMaxSeasonLength() {
        return MAX_SEASON_LENGTH;
    }

}