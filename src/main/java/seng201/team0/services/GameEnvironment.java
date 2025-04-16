package seng201.team0.services;

import seng201.team0.models.Car;

import java.util.ArrayList;

public class GameEnvironment {
    private String playerName;
    private int money;
    private int seasonLength;
    private int racesRemaining;
    private String difficulty;
    private ArrayList<Car> selectedCars;
//    private Shop shop; CAN ADD THESE ONCE THESE CLASSES HAVE BEEN CREATED
//    private Garage garage;

    public GameEnvironment(String playerName, int seasonLength, String difficulty, int money, ArrayList<Car> selectedCars, int racesRemaining) {
        this.playerName = playerName;
        this.money = money;
        this.seasonLength = seasonLength;
        this.racesRemaining = racesRemaining;
        this.selectedCars = selectedCars;
        this.difficulty = difficulty;
    }

    public int viewMoney(){
        return this.money;
    }

    public int viewSeasonLength(){
        return this.seasonLength;
    }

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
