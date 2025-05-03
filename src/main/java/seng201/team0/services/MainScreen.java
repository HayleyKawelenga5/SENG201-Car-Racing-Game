package seng201.team0.services;

import seng201.team0.models.Car;
//import seng201.team0.models.Shop;
//import seng201.team0.models.Garage;

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

    //private Shop shop;
    //private Garage garage;
    //private CarService carService;
    //private UpgradeService upgradeService;

    /**
     * Constructs a new game environment with the given parameters.
     *
     * @param playerName      The name of the player.
     * @param seasonLength    The total number of races in the season.
     * @param difficulty      The difficulty level.
     * @param playerMoney     The current amount of money.
     * @param selectedCars    The current car for the next race.
     * @param racesRemaining  The number of races remaining.
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

    public int getMoney(){
        return this.money;
    }

    public int getSeasonLength(){
        return this.seasonLength;
    }

    public int getRacesRemaining(){
        return this.racesRemaining;
    }

    /**
    public void visitShop(){
        //implement later to open shop interface
    }

    public void visitGarage(){
        //not yet implemented
    }

    public void selectRace(){
        //not yet implemented
    }
     */
}
