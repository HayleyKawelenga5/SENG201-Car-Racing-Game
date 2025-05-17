package seng201.team0.services;

import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.List;

import seng201.team0.models.Car;

/**
 * Handles game initialisation, including player name, difficulty, money, car selection, and season length.
 */
public class GameInitializer {

    private String playerName;
    private int seasonLength;
    private String difficulty;
    private int money;
    private List<Car> playerCars = new ArrayList<>();
    private Car currentCar;
    private CarService carService = new CarService();
    private static final int MAX_CARS = 3;
    private static final int MIN_SEASON_LENGTH = 3;
    private static final int MAX_SEASON_LENGTH = 15;

    /**
     * Validates and sets the player's name.
     *
     * @param name The player's name.
     * @throws InvalidNameException if the name is invalid.
     */
    public void selectName(String name) throws InvalidNameException {
        if (name.length() < 3 || name.length() > 15 || !name.matches("[a-zA-Z0-9 ]+")){
            throw new InvalidNameException("Player name must be between 3 and 15 characters and contain no special characters.");
        }
        this.playerName = name;
    }

    /**
     * Sets the season length if within valid range.
     *
     * @param length Number of races.
     * @throws IllegalArgumentException if length is outside 5–15.
     */
    public void selectSeasonLength(int length) throws IllegalArgumentException {
        if (length < 5 || length > 15) {
            throw new IllegalArgumentException("Season length must be between 5 and 15 races");
        }
        this.seasonLength = length;
    }

    /**
     * Sets the difficulty and adjusts starting money accordingly.
     *
     * @param difficulty The selected difficulty ("EASY" or "HARD").
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
        this.difficulty = difficulty;
    }

    public int getMoney() { return money; }

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

    public List<Car> getPlayerCars() { return playerCars; }

    public void setPlayerCars(List<Car> playerCars) { this.playerCars = playerCars; }

    /**
     * Attempts to add a car to the player's selection.
     * A car can only be added if the player does not already have it,
     * has fewer than 3 selected cars, and has enough money to purchase it.
     *
     * @param car The Car to add to the selection.
     * @return true if the car was successfully added, false otherwise.
     */
    public boolean addCar(Car car) {
        if (!playerCars.contains(car) && playerCars.size() < 3) {
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
     * @param car The Car to remove from the selection.
     */
    public boolean deleteCar(Car car) {
        if (playerCars.contains(car)) {
            playerCars.remove(car);
            money += car.getCarCost();
            return true;
        }
        return false;
    }

    public int getMaxCars() {
        return MAX_CARS;
    }

    public int getMinSeasonLength() {
        return MIN_SEASON_LENGTH;
    }

    public int getMaxSeasonLength() {
        return MAX_SEASON_LENGTH;
    }

}