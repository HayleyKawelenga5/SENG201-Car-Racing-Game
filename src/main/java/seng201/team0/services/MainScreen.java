package seng201.team0.services;

import seng201.team0.models.Car;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the core game environment. Tracks the player state and allows interaction
 * with the shop, garage, and races.
 */
public class MainScreen {

    private String playerName;
    private int seasonLength;
    private String difficulty;
    private int money;
    private List<Car> playerCars;
    private int racesRemaining;
    private Car currentCar;

    /**
     * Constructs a new game environment with the given parameters.
     *
     * @param playerName      The name of the player.
     * @param seasonLength    The total number of races in the season.
     * @param difficulty      The difficulty level of the game.
     * @param money           The current amount of money the player has.
     * @param playerCars      A list of cars owned by the player.
     * @param racesRemaining  The number of races remaining in the season.
     * @param currentCar      The car selected for the next race.
     */
    public MainScreen(String playerName, int seasonLength, String difficulty, int money, ArrayList<Car> playerCars, int racesRemaining, Car currentCar) {
        this.playerName = playerName;
        this.seasonLength = seasonLength;
        this.difficulty = difficulty;
        this.money = money;
        this.playerCars = playerCars;
        this.racesRemaining = racesRemaining;
        this.currentCar = currentCar;
    }

    /**
     * Returns the amount of money the player currently has.
     *
     * @return The current money balance.
     */
    public int getMoney(){
        return this.money;
    }

    /**
     * Returns the total number of races in the season.
     *
     * @return The length of the season.
     */
    public int getSeasonLength(){
        return this.seasonLength;
    }

    /**
     * Returns the number of races remaining in the current season.
     *
     * @return The number of races left.
     */
    public int getRacesRemaining(){
        return this.racesRemaining;
    }

}
