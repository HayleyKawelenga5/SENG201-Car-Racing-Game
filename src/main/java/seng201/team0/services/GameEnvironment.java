package seng201.team0.services;

import seng201.team0.models.Car;

import java.util.ArrayList;

public class GameEnvironment {
    private String playerName;
    private int money;
    private int seasonLength;
    private int racesRemaining;
    private ArrayList<Car> selectedCars;
//    private Shop shop; CAN ADD THESE ONCE THESE CLASSES HAVE BEEN CREATED
//    private Garage garage;

    public GameEnvironment(String playerName, int money, int seasonLength, int racesRemaining, ArrayList<Car> selectedCars) {
        this.playerName = playerName;
        this.money = money;
        this.seasonLength = seasonLength;
        this.racesRemaining = racesRemaining;
        this.selectedCars = selectedCars;
    }

//    public int viewMoney(){
//        return
//    }
}
