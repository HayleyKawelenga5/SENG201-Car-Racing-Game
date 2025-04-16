package seng201.team0.services;

import seng201.team0.models.Car;

import javax.naming.InvalidNameException;
import java.util.ArrayList;

public class GameInitialiser {
    private String playerName;
    private int seasonLength;
    private String difficulty;
    private int startingMoney;
    private ArrayList<Car> selectedCars = new ArrayList<>();

    public void selectName(String name) throws InvalidNameException {
        if (name.length() < 3 || name.length() > 15 || !name.matches("[a-zA-Z0-9 ]+")){
            throw new InvalidNameException("Name must be 3-15 characters and must not contain special characters.");
        }
        this.playerName = name;
    }

    public void selectSeasonLength(int length) throws IllegalArgumentException {
        if (length < 5 || length > 15){
            throw new IllegalArgumentException("Season must be between 5 and 15 races");
        }
        this.seasonLength = length;
    }

    public void selectDifficulty(String difficulty) throws IllegalArgumentException {
        this.difficulty = difficulty;
        //can later change this to scale other factors of game
        switch (difficulty){
            case "Easy":
                this.startingMoney = 100;
                break;
            case "Normal":
                this.startingMoney = 50;
                break;
            case "Hard":
                this.startingMoney = 20;
                break;
            default:
                throw new IllegalArgumentException("Difficulty must be easy, normal, or hard");
        }
    }

    public void selectCar(Car car) throws IllegalStateException {
        if (selectedCars.size() >= 3){
            throw new IllegalStateException("You can only select up to 3 cars.");
        }
        selectedCars.add(car);
    }

    public void selectName(ArrayList<Car> selectedCars, ArrayList<String> selectedNames) {
        int i = 0;
        for (Car car : selectedCars){
            if (selectedNames.get(i) == null){
                selectedNames.add(i, "Car" + (i+1)); //sets default name for car if empty
            }
            car.setName(selectedNames.get(i));
            i += 1;
        }
    }

    public String getPlayerName(){
        return this.playerName;
    }

    public int getSeasonLength(){
        return this.seasonLength;
    }

    public int getStartingMoney(){
        return this.startingMoney;
    }

    public ArrayList<Car> getSelectedCars(){
        return this.selectedCars;
    }

//    public GameEnvironment startGame(){
//        return new GameEnvironment(playerName, seasonLength, difficulty, startingMoney, selectedCars);
//    }
    //NEED TO IMPLEMENT GAME ENVIRONMENT FIRST
}
