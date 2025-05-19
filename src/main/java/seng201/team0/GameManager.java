package seng201.team0;

import seng201.team0.gui.ScreenNavigator;

import seng201.team0.models.Car;
import seng201.team0.models.Race;
import seng201.team0.models.Upgrade;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code GameManager} class acts as the central coordinator of the game state and navigation between different screens.
 * It stores core player data, manages transitions between screens, and handles progression logic such as starting races or ending the game.
 */
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
    private final List<Integer> playerFinishPositions = new ArrayList<>();

    private List<Upgrade> playerUpgrades;

    private Race selectedRace;

    /**
     * Constructs a GameManager with no navigator (primarily for testing).
     */
    public GameManager() {
        this.navigator = null;
    }

    /**
     * Constructs a GameManager with a {@link ScreenNavigator} and launches the start screen.
     *
     * @param navigator The screen navigator for managing GUI transitions.
     */
    public GameManager(ScreenNavigator navigator) {
        this.navigator = navigator;
        navigator.launchStartScreen(this);
    }

    /**
     * Sets initial game parameters and launches the main game screen.
     *
     * @param playerName     The player's name.
     * @param seasonLength   The total number of races in the season.
     * @param difficulty     The difficulty setting.
     * @param playerCars     The list of cars owned by the player.
     * @param money          The player's current money.
     * @param currentCar     The player's currently selected car.
     * @param playerUpgrades The list of upgrades the player owns.
     */
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

    /**
     * Updates key game data and launches the main screen after a race.
     *
     * @param money        The updated money.
     * @param currentCar   The updated car.
     * @param seasonLength The updated season length.
     */
    public void toMainScreenFromRace(int money, Car currentCar, int seasonLength) {
        this.money = money;
        this.currentCar = currentCar;
        this.seasonLength = seasonLength;
        navigator.launchMainScreen(this);
    }

    /**
     * Transitions to the finish screen and records final game statistics.
     *
     * @param averagePlayerFinishPosition The average finish position of the player.
     * @param playerTotalPrizeMoney       The total prize money earned by the player.
     */
    public void toFinishScreen(double averagePlayerFinishPosition, int playerTotalPrizeMoney) {
        this.averagePlayerFinishPosition = averagePlayerFinishPosition;
        this.playerTotalPrizeMoney = playerTotalPrizeMoney;
        navigator.launchFinishScreen(this);
    }

    /**
     * Navigates to the shop screen.
     */
    public void goToShop() {
        navigator.launchShopScreen(this);
    }

    /**
     * Navigates to the garage screen.
     */
    public void goToGarage() {
        navigator.launchGarageScreen(this);
    }

    /**
     * Navigates back to the main screen.
     */
    public void goBack() {
        navigator.launchMainScreen(this);
    }

    /**
     * Starts a race and navigates to the race screen.
     *
     * @param race The selected race.
     */
    public void startRace(Race race) {
        this.selectedRace = race;
        navigator.launchRaceScreen(this);
    }

    /**
     * Exits the game.
     */
    public void onQuitRequested(){
        System.exit(0);
    }

    /**
     * Ensures the current car is valid and initializes it if not set.
     * Throws an exception if no cars are available.
     */
    public void initializePlayerCar(){
        if (playerCars == null || playerCars.isEmpty()) {
            throw new IllegalStateException("No cars available to initialize");
        }
        if (!playerCars.contains(currentCar)) {
            currentCar = playerCars.getFirst();
        }
    }

    /**
     * Gets the player's name
     * @return The player's name.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Gets the season length of the race.
     * @return The season length.
     */
    public int getSeasonLength() {
        return seasonLength;
    }

    /**
     * Gets the number of races remaining.
     * @return The number of races remaining.
     */
    public int getRacesRemaining() {
        return racesRemaining;
    }

    /**
     * Sets the number of races remaining.
     *
     * @param racesRemaining The new number of remaining races.
     */
    public void setRacesRemaining(int racesRemaining) { this.racesRemaining = racesRemaining; }


    /**
     * Gets the game difficulty.
     * @return The selected difficulty setting.
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * Gets the player's current amount of money
     * @return The player's current money.
     */
    public int getMoney() { return money; }

    /**
     * Gets the list of player finish positions
     * @return a list containing the player's finish positions throughout the race
     */
    public List<Integer> getPlayerFinishPositions() {
        return playerFinishPositions;
    }

    /**
     * Gets the player's average finish position.
     * @return The player's average finish position.
     */
    public double getAveragePlayerFinishPositions(){
        return averagePlayerFinishPosition;
    }

    /**
     * Gets the total prize money earned by the player.
     * @return The total prize money earned by the player.
     */
    public int getPlayerTotalPrizeMoney(){
        return playerTotalPrizeMoney;
    }

    /**
     * Adds the given amount to the total prize money
     * @param amount the amount to be added to the total prize money
     */
    public void addToTotalPrizeMoney(int amount){
        this.playerTotalPrizeMoney += amount;
    }

    /**
     * sets the player's money
     * @param money the value that the money will be set to
     */
    public void setMoney(int money) { this.money = money; }

    /**Gets the list of cars owned by the player.
     * @return The list of cars owned by the player.
     */
    public List<Car> getPlayerCars() { return playerCars; }

    /**
     * Sets the list of player cars.
     *
     * @param playerCars The new list of cars.
     */
    public void setPlayerCars(List<Car> playerCars) { this.playerCars = playerCars;}

    /**
     * Gets the currently selected car
     * @return The player's currently selected car.
     */
    public Car getCurrentCar() { return currentCar; }

    /**
     * Sets the player's current car.
     *
     * @param currentCar The car to set as current.
     */
    public void setCurrentCar(Car currentCar) { this.currentCar = currentCar; }

    /**
     * Gets the list of upgrades the player owns.
     * @return The list of upgrades the player owns.
     */
    public List<Upgrade> getPlayerUpgrades() { return playerUpgrades; }

    /**
     * Sets the list of player upgrades.
     *
     * @param playerUpgrades The new list of upgrades.
     */
    public void setPlayerUpgrades(List<Upgrade> playerUpgrades) { this.playerUpgrades = playerUpgrades; }

    /**
     * Gets the currently selected race.
     * @return The currently selected race.
     */
    public Race getSelectedRace() { return selectedRace; }

    /**
     * Sets the selected race.
     *
     * @param selectedRace The race to select.
     */
    public void setSelectedRace(Race selectedRace) {
        this.selectedRace = selectedRace;
    }



}
