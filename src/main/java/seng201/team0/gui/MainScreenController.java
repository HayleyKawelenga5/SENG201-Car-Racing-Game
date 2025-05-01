package seng201.team0.gui;

import javafx.scene.control.*;
import seng201.team0.GameManager;
import seng201.team0.models.Car;
import seng201.team0.services.GameInitialiser;

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


    public MainScreenController(GameManager manager) {
        super(manager);
    }

    @Override
    protected String getFxmlFile() {return "/fxml/main_screen.fxml";}

    @Override
    protected String getTitle() {return "Main Screen";}


    @FXML
    public void initialize() {
        GameManager gameManager = getGameManager();

        String playerName = gameManager.getPlayerName();
        int seasonLength = gameManager.getSeasonLength();
        String difficulty = gameManager.getDifficulty();
        List<Car> selectedCars = gameManager.getSelectedCars();
        int playerMoney = gameManager.getPlayerMoney();
        Car currentCar = gameManager.getCurrentCar();

        moneyLabel.setText("Money: $" + String.valueOf(playerMoney));
        racesRemainingLabel.setText("Races Remaining: " + String.valueOf(seasonLength));
        seasonLengthLabel.setText("Season Length: " + String.valueOf(seasonLength));

        currentCarNameLabel.setText("Current car: " + currentCar.getName());
        currentCarSpeedLabel.setText("Speed: " + currentCar.getSpeed());
        currentCarHandlingLabel.setText("Handling: " + currentCar.getHandling());
        currentCarReliabilityLabel.setText("Reliability: " + currentCar.getReliability());
        currentCarFuelEconomyLabel.setText("Fuel Economy: " + currentCar.getFuelEconomy());

        toGarageButton.setOnAction(event -> onToGarageButtonClicked());

        toShopButton.setOnAction(event -> onToShopButtonClicked());
    }

    @FXML
    public void onToGarageButtonClicked() {
        getGameManager().goToGarage();
    }

    @FXML
    public void onToShopButtonClicked(){
        getGameManager().goToShop(getGameManager().getPlayerMoney(), getGameManager().getSelectedCars());
    }

    @FXML
    public void onBackButtonClicked() {
        getGameManager().goToGarage();
    }
}
