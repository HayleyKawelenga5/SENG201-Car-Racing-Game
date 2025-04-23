package seng201.team0.services;

import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.List;

import seng201.team0.models.Car;
import seng201.team0.services.CarService;

/**
 * Handles game initialisation, including player name, difficulty, car selection, and season length.
 */
public class GameInitialiser {
    private String playerName;
    private String difficulty;
    private int seasonLength;
    private int money;
    private CarService carService = new CarService();
    private List<Car> selectedCars = new ArrayList<>();

    /**
     * Validates and sets the player's name.
     *
     * @param name The player's name.
     * @throws InvalidNameException if the name is invalid.
     */
    public void selectName(String name) throws InvalidNameException {
        if (name.length() < 3 || name.length() > 15 || !name.matches("[a-zA-Z0-9 ]+")){
            throw new InvalidNameException("Player name must be 3-15 characters and contain no special characters.");
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
        if (length < 5 || length > 15){
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
                this.money = 1000;
                break;
            case "HARD":
                this.money = 500;
                break;
            default:
                throw new IllegalArgumentException("Invalid difficulty. Please select either EASY or HARD.");
        }
        this.difficulty = difficulty;
    }

    /**
     * Returns the player's current amount of money.
     *
     * @return The amount of money the player has.
     */
    public int getMoney() { return money; }

    /**
     * Sets the player's money to the specified amount.
     *
     * @param money The new amount of money to set.
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
            carService.generateRandomCars();
        }
        return carService.getAvailableCars();
    }

    /**
     * Returns the list of cars currently selected by the player.
     *
     * @return A list of selected Car objects.
     */
    public List<Car> getSelectedCars() { return selectedCars; }

    public void setSelectedCars(List<Car> selectedCars) { this.selectedCars = selectedCars; }

    /**
     * Attempts to add a car to the player's selection.
     * A car can only be added if the player does not already have it,
     * has fewer than 3 selected cars, and has enough money to purchase it.
     *
     * @param car The {@link Car} to add to the selection.
     * @return true if the car was successfully added, false otherwise.
     */
    public boolean addCar(Car car) {
        if (!selectedCars.contains(car) && selectedCars.size() < 3) {
            if (money >= car.getCost()) {
                selectedCars.add(car);
                money -= car.getCost();
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
    public void deleteCar(Car car) {
        if (selectedCars.contains(car)) {
            selectedCars.remove(car);
            money += car.getCost();
        }
    }
}