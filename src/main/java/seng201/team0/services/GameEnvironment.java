package seng201.team0.services;

import seng201.team0.models.Car;
import seng201.team0.models.Shop;
import seng201.team0.models.Garage;

import java.util.ArrayList;

/**
 * Represents the core game environment. Tracks the player state and allows interaction
 * with the shop, garage, and races.
 */
public class GameEnvironment {
    private String playerName;
    private int playerMoney;
    private int seasonLength;
    private int racesRemaining;
    private ArrayList<Car> selectedCars;
    private String difficulty;
    private Car currentCar;
    private Shop shop;
    private Garage garage;
    private CarService carService;
    private UpgradeService upgradeService;

    /**
     * Constructs a new game environment with the given parameters.
     *
     * @param playerName      The name of the player.
     * @param seasonLength    The total number of races in the season.
     * @param difficulty      The difficulty level.
     * @param playerMoney           The current amount of money.
     * @param selectedCars    The current car for the next race.
     * @param racesRemaining  The number of races remaining.
     */
    public GameEnvironment(String playerName, int seasonLength, String difficulty, int playerMoney, ArrayList<Car> selectedCars, int racesRemaining) {
        this.playerName = playerName;
        this.playerMoney = playerMoney;
        this.seasonLength = seasonLength;
        this.racesRemaining = racesRemaining;
        this.selectedCars = selectedCars;
        this.difficulty = difficulty;

        this.shop = new Shop(playerMoney, carService, upgradeService);
        //this.garage = new Garage(currentCar, reserveCars, availableUpgrades);
        //IMPLEMENT LATER
    }

    /**
     * Returns the player's current amount of money.
     *
     * @return The player's money.
     */
    public int viewMoney(){
        return this.playerMoney;
    }

    /**
     * Returns the total number of races in the season.
     *
     * @return The season length.
     */
    public int viewSeasonLength(){
        return this.seasonLength;
    }

    /**
     * Returns how many races are left to complete.
     *
     * @return Races remaining.
     */
    public int viewRacesRemaining(){
        return this.racesRemaining;
    }

    public void visitShop(){
        //implement later to open shop interface
    }

    public void visitGarage(){
        //not yet implemented
    }

    public void selectRace(){
        //not yet implemented
    }
}
