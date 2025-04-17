package seng201.team0.models;

import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Handles initial game setup, including player name, difficulty, car selection, and season length.
 */
public class GameInitialiser {
    private String playerName;
    private String difficulty;
    private int seasonLength;
    private int startingMoney;
    private int minStat;
    private int maxStat = 11;
    private ArrayList<Car> selectedCars = new ArrayList<>();

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
        difficulty = difficulty.toUpperCase();
        switch (difficulty) {
            case "EASY":
                startingMoney = 1000;
                minStat = 5;
                break;
            case "HARD":
                startingMoney = 500;
                minStat = 1;
                break;
            default:
                throw new IllegalArgumentException('Invalid difficulty. Please select either "EASY" or "HARD".');
        }
        this.difficulty = difficulty;
    }

    /**
     * Adds a car to the selected cars list.
     *
     * @param car The car to add.
     * @throws IllegalStateException if more than 3 cars are selected.
     * @throws IllegalArgumentException if insufficient money to buy car.
     */
    public void selectCar(Car car) throws IllegalStateException, IllegalArgumentException {
        if (selectedCars.size() >= 3) {
            throw new IllegalStateException("You can only select up to 3 cars.");
        }
        if (car.getCost() > startingMoney) {
            throw new IllegalArgumentException("You do not have enough money to buy this car.");
        }
        selectedCars.add(car);
        startingMoney -= car.getCost();
    }

    /**
     * Generates a list of random car options based on difficulty.
     *
     * @param amount Number of cars to generate.
     * @return A list of random Car objects.
     */
    public ArrayList<Car> generateCarOptions(int amount) {
        ArrayList<Car> cars = new ArrayList();
        for (int i = 0; i < amount; i++) {
            cars.add(Car.generateRandomCar(minStat, maxStat));
        }
        return cars;
    }

//    public GameEnvironment startGame(){
//        return new GameEnvironment(playerName, seasonLength, difficulty, startingMoney, selectedCars);
//    }
    //NEED TO IMPLEMENT GAME ENVIRONMENT FIRST
}