package seng201.team0;

import seng201.team0.gui.ScreenNavigator;
import seng201.team0.models.Car;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private final ScreenNavigator navigator;

    private String playerName;
    private int seasonLength;
    private String difficulty;
    private List<Car> selectedCars;
    private int playerMoney;
    private Car currentCar;

    public GameManager(ScreenNavigator navigator) {
        this.navigator = navigator;
        navigator.launchGameInitialiserScreen(this);
    }

    public void onSetupComplete(String playerName, int seasonLength, String difficulty, List<Car> selectedCars, int playerMoney, Car currentCar) {
        this.playerName = playerName;
        this.seasonLength = seasonLength;
        this.difficulty = difficulty;
        this.selectedCars = selectedCars;
        this.playerMoney = playerMoney;
        this.currentCar = currentCar;

        navigator.launchMainScreen(this);
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getSeasonLength() {
        return seasonLength;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public List<Car> getSelectedCars() {
        return selectedCars;
    }

    public int getPlayerMoney() {
        return playerMoney;
    }

    public Car getCurrentCar() { return currentCar;}

    public void goToShop(int playerMoney, List<Car> selectedCars){
        this.playerMoney = playerMoney;
        navigator.launchShopScreen(this);
    }

    public void goToGarage() {

        this.selectedCars = selectedCars;
        navigator.launchGarageScreen(this);
    }

    public void goBack() {
        //this.currentCar = currentCar;
        this.selectedCars = selectedCars;
        navigator.launchMainScreen(this);
    }

    public void onQuitRequested(){
        System.exit(0);
    }
}
