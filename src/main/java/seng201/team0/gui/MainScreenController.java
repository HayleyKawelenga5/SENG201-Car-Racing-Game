package seng201.team0.gui;

import javafx.scene.control.*;

import seng201.team0.GameManager;

import seng201.team0.services.StartScreen;

import seng201.team0.models.Car;
import seng201.team0.models.Upgrade;

import javafx.fxml.FXML;

import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.List;

public class MainScreenController extends ScreenController {

    @FXML private Button race1Button;
    @FXML private Button race2Button;
    @FXML private Button race3Button;

    @FXML private Button toShopButton;
    @FXML private Button toGarageButton;
    @FXML private Button selectRaceButton;
    @FXML private Button startRaceButton;

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
    public void onToGarageButtonClicked() {
        getGameManager().goToGarage();
    }

    @FXML
    public void onToShopButtonClicked() {
        getGameManager().goToShop();
    }

}
