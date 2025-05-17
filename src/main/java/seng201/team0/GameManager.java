package seng201.team0;

import seng201.team0.gui.ScreenNavigator;

import seng201.team0.models.Car;
import seng201.team0.models.Race;
import seng201.team0.models.Upgrade;

import java.util.List;

public class GameManager {

    private final ScreenNavigator navigator;

    private String playerName;
    private int seasonLength;
    private String difficulty;
    private int money;
    private List<Car> playerCars;
    private Car currentCar;

    private int racesRemaining;
    private double averagePlayerFinishPosition;
    private int playerTotalPrizeMoney;

    private List<Upgrade> playerUpgrades;

    private Race selectedRace;

    public GameManager() {
        this.navigator = null;
    }

    public GameManager(ScreenNavigator navigator) {
        this.navigator = navigator;
        navigator.launchStartScreen(this);
    }

    public void toMainScreen(String playerName, int seasonLength, String difficulty, List<Car> playerCars, int money, Car currentCar, List<Upgrade> playerUpgrades) {
        this.playerName = playerName;
        this.seasonLength = seasonLength;
        this.difficulty = difficulty;
        this.money = money;
        this.playerCars = playerCars;
        this.currentCar = currentCar;
        this.playerUpgrades = playerUpgrades;

        navigator.launchMainScreen(this);
    }

    public void toMainScreenFromRace(int money, Car currentCar, int seasonLength) {
        this.money = money;
        this.currentCar = currentCar;
        this.seasonLength = seasonLength;
        navigator.launchMainScreen(this);
    }

    public void toFinishScreen(double averagePlayerFinishPosition, int playerTotalPrizeMoney) {
        this.averagePlayerFinishPosition = averagePlayerFinishPosition;
        this.playerTotalPrizeMoney = playerTotalPrizeMoney;
        navigator.launchFinishScreen(this);
    }

    public void goToShop() {
        navigator.launchShopScreen(this);
    }

    public void goToGarage() {
        navigator.launchGarageScreen(this);
    }

    public void goBack() {
        navigator.launchMainScreen(this);
    }

    public void startRace(Race race) {
        this.selectedRace = race;
        navigator.launchRaceScreen(this);
    }

    public void onQuitRequested(){
        System.exit(0);
    }

    public void initializePlayerCar(){
        if (playerCars == null || playerCars.isEmpty()) {
            throw new IllegalStateException("No cars available to initialize");
        }
        if (!playerCars.contains(currentCar)) {
            currentCar = playerCars.getFirst();
        }
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getSeasonLength() {
        return seasonLength;
    }

    public int getRacesRemaining() {
        return racesRemaining;
    }

    public void setRacesRemaining(int racesRemaining) { this.racesRemaining = racesRemaining; }

    public String getDifficulty() {
        return difficulty;
    }

    public int getMoney() { return money; }

    public double getAveragePlayerFinishPositions(){
        return averagePlayerFinishPosition;
    }

    public int getPlayerTotalPrizeMoney(){
        return playerTotalPrizeMoney;
    }

    public void setMoney(int money) { this.money = money; }

    public List<Car> getPlayerCars() { return playerCars; }

    public void setPlayerCars(List<Car> playerCars) { this.playerCars = playerCars;}

    public Car getCurrentCar() { return currentCar; }

    public void setCurrentCar(Car currentCar) { this.currentCar = currentCar; }

    public List<Upgrade> getPlayerUpgrades() { return playerUpgrades; }

    public void setPlayerUpgrades(List<Upgrade> playerUpgrades) { this.playerUpgrades = playerUpgrades; }

    public Race getSelectedRace() { return selectedRace; }

    public void setSelectedRace(Race selectedRace) {
        this.selectedRace = selectedRace;
    }



}
