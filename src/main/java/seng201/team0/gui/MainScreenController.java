package seng201.team0.gui;

import javafx.scene.control.*;

import seng201.team0.GameManager;

import seng201.team0.models.Race;
import seng201.team0.models.Route;
import seng201.team0.services.RaceService;
import seng201.team0.services.StartScreen;

import seng201.team0.models.Car;
import seng201.team0.models.Upgrade;

import javafx.fxml.FXML;

import javax.naming.InvalidNameException;
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MainScreenController extends ScreenController {

    @FXML private Button race1Button;
    @FXML private Button race2Button;
    @FXML private Button race3Button;

    @FXML private Button toShopButton;
    @FXML private Button toGarageButton;
    @FXML private Button selectRaceButton;
    @FXML private Button startRaceButton;

    @FXML private Button route1Button;
    @FXML private Button route2Button;
    @FXML private Button route3Button;
    @FXML private Button selectRouteButton;

    @FXML private Label currentCarNameLabel;
    @FXML private Label currentCarSpeedLabel;
    @FXML private Label currentCarHandlingLabel;
    @FXML private Label currentCarReliabilityLabel;
    @FXML private Label currentCarFuelEconomyLabel;

    @FXML private Label seasonLengthLabel;
    @FXML private Label racesRemainingLabel;
    @FXML private Label moneyLabel;

    @FXML private Label raceHoursLabel;
    @FXML private Label raceRoutesLabel;
    @FXML private Label raceEntriesLabel;
    @FXML private Label racePrizeMoneyLabel;

    @FXML private Label selectedRaceLabel;

    private RaceService raceService;
    private List<Race> availableRaces;
    private Race selectedRace;
    private int selectedRaceIndex = -1;
    private Route selectedRoute;

    private Race chosenRace;
    private Route chosenRoute;

    private List<Button> routeButtons;

    //private MainScreen mainScreen = new MainScreen();



    public MainScreenController(GameManager manager) {
        super(manager);
    }

    @Override
    protected String getFxmlFile() {return "/fxml/main_screen.fxml";}

    @Override
    protected String getTitle() {return "Main Screen";}

    @FXML
    public void initialize() {
        GameManager mainScreen = getGameManager();

        String playerName = mainScreen.getPlayerName();
        int seasonLength = mainScreen.getSeasonLength();
        String difficulty = mainScreen.getDifficulty();
        int money = mainScreen.getMoney();
        List<Car> playerCars = mainScreen.getPlayerCars();
        List<Upgrade> playerUpgrades = mainScreen.getPlayerUpgrades();

        Car currentCar = mainScreen.getCurrentCar();
        if (!playerCars.contains(currentCar)) {
            currentCar = playerCars.get(0);
        }
        mainScreen.setCurrentCar(currentCar);

        raceService = new RaceService();
        availableRaces = raceService.generateRaces(3, difficulty);


        List<Button> availableRaceButtons = List.of(race1Button, race2Button, race3Button);
        for (int i = 0; i < availableRaceButtons.size(); i++) {
            int index = i;
            availableRaceButtons.get(i).setOnAction(event -> onAvailableRaceButtonClicked(index));
        }

        routeButtons = List.of(route1Button, route2Button, route3Button);
        for (int i = 0; i < routeButtons.size(); i++) {
            int index = i;
            routeButtons.get(i).setOnAction(event -> onRouteButtonClicked(index));
        }

        route1Button.setVisible(false);
        route2Button.setVisible(false);
        route3Button.setVisible(false);

        selectRouteButton.setOnAction(event -> onSelectRouteButtonClicked());
        selectRaceButton.setOnAction(event -> onSelectRaceButtonClicked());
        startRaceButton.setOnAction(event -> onStartRaceButtonClicked());

        moneyLabel.setText("Money: $" + String.valueOf(money));
        racesRemainingLabel.setText("Races Remaining: " + String.valueOf(seasonLength)); // NEED TO FIX
        seasonLengthLabel.setText("Season Length: " + String.valueOf(seasonLength));

        currentCarNameLabel.setText("Current car: " + currentCar.getCarName());
        currentCarSpeedLabel.setText("Speed: " + currentCar.getCarSpeed());
        currentCarHandlingLabel.setText("Handling: " + currentCar.getCarHandling());
        currentCarReliabilityLabel.setText("Reliability: " + currentCar.getCarReliability());
        currentCarFuelEconomyLabel.setText("Fuel Economy: " + currentCar.getCarFuelEconomy());

        toGarageButton.setOnAction(event -> onToGarageButtonClicked());
        toShopButton.setOnAction(event -> onToShopButtonClicked());
        //selectRaceButton.setOnAction(event -> onSelectRaceButtonClicked());
        //startRaceButton.setOnAction(event -> onStartRaceButtonClicked());
    }

    @FXML
    private void onAvailableRaceButtonClicked(int index){
        if (index >=0 && index < availableRaces.size()){
            selectedRace = availableRaces.get(index);
            selectedRaceIndex = index;
            updateRaceStats(selectedRace);
        }
    }

    @FXML
    private void onSelectRaceButtonClicked() {
        if (selectedRace == null){
            showAlert("No Race Selected", "Please select a race.");
            return;
        } else {
            chosenRace = selectedRace;
        }

    }

    @FXML
    private void onStartRaceButtonClicked() {
        if (selectedRace == null){
            showAlert("No Race Selected", "Please select a race.");
        }
        getGameManager().startRace(chosenRace, chosenRoute);
    }

    @FXML
    private void onSelectRouteButtonClicked(){
        if (selectedRoute == null){
            showAlert("No Route Selected", "Please select a route.");
        } else {
            chosenRoute = selectedRoute;
        }
    }

    @FXML
    private void onRouteButtonClicked(int index) {
        selectedRoute = selectedRace.getRoutes().get(index);

        for (int i = 0; i < routeButtons.size(); i++) {
            if (i == index){
                routeButtons.get(i).setStyle("-fx-background-color: LightGreen");
            } else {
                routeButtons.get(i).setStyle("");
            }
        }
    }

    private void updateRaceStats(Race race){
        if (race == null){
            raceHoursLabel.setText("Hours: ");
            raceRoutesLabel.setText("Routes: ");
            raceEntriesLabel.setText("Entries: ");
            racePrizeMoneyLabel.setText("Prize Money: ");
            route1Button.setVisible(false);
            route2Button.setVisible(false);
            route3Button.setVisible(false);
        } else {
            raceHoursLabel.setText("Hours: " + race.getHours());
            raceRoutesLabel.setText("Routes: " + race.getRoutesDescription());
            raceEntriesLabel.setText("Entries: " + race.getEntries());
            racePrizeMoneyLabel.setText("Prize Money: " + race.getPrizeMoney());

            List<Route> routes = race.getRoutes();
            List<Route> routesNoDuplicates = new ArrayList<>(new HashSet<>(routes));
            for (int i = 0; i < routeButtons.size(); i++) {
                if (i < routesNoDuplicates.size()){
                routeButtons.get(i).setText(routesNoDuplicates.get(i).getDescription().toString());
                routeButtons.get(i).setVisible(true);

            } else {
                routeButtons.get(i).setVisible(false);
            }
        }}
    }

    @FXML
    public void onToGarageButtonClicked() {
        getGameManager().goToGarage();
    }

    @FXML
    public void onToShopButtonClicked() {
        getGameManager().goToShop();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
