package seng201.team0.gui;

import javafx.scene.control.*;

import seng201.team0.GameManager;

import seng201.team0.models.Race;
import seng201.team0.services.RaceService;

import seng201.team0.models.Car;

import javafx.fxml.FXML;
import seng201.team0.utils.ScreenUpdater;

import java.util.List;

public class MainScreenController extends ScreenController {

    @FXML private Button race1Button;
    @FXML private Button race2Button;
    @FXML private Button race3Button;

    @FXML private Button toShopButton;
    @FXML private Button toGarageButton;
    @FXML private Button selectRaceButton;
    @FXML private Button toStartLineButton;

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

    private List<Race> availableRaces;
    private Race selectedRace;
    private Race chosenRace;

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
        mainScreen.initializePlayerCar();

        Car currentCar = mainScreen.getCurrentCar();
        int seasonLength = mainScreen.getSeasonLength();
        String difficulty = mainScreen.getDifficulty();
        int money = mainScreen.getMoney();
        int racesRemaining = mainScreen.getRacesRemaining();
        RaceService raceService = new RaceService();
        availableRaces = raceService.generateRaces(3, difficulty);



        List<Button> availableRaceButtons = List.of(race1Button, race2Button, race3Button);
        for (int i = 0; i < availableRaceButtons.size(); i++) {
            int index = i;
            availableRaceButtons.get(i).setOnAction(event -> onAvailableRaceButtonClicked(index));
        }

        selectRaceButton.setOnAction(event -> onSelectRaceButtonClicked());
        toStartLineButton.setOnAction(event -> onToStartLineButtonClicked());
        toGarageButton.setOnAction(event -> onToGarageButtonClicked());
        toShopButton.setOnAction(event -> onToShopButtonClicked());

        moneyLabel.setText("Money: $" + money);
        racesRemainingLabel.setText("Races Remaining: " + racesRemaining);
        seasonLengthLabel.setText("Season Length: " + seasonLength);

        currentCarNameLabel.setText("Current car: " + currentCar.getCarName());
        currentCarSpeedLabel.setText("Speed: " + currentCar.getCarSpeed());
        currentCarHandlingLabel.setText("Handling: " + currentCar.getCarHandling());
        currentCarReliabilityLabel.setText("Reliability: " + currentCar.getCarReliability());
        currentCarFuelEconomyLabel.setText("Fuel Economy: " + currentCar.getCarFuelEconomy());
    }

    @FXML
    private void onAvailableRaceButtonClicked (int index) {
        if (index >= 0 && index < availableRaces.size()) {
            chosenRace = null;
            selectRaceButton.setStyle("");
            selectedRace = availableRaces.get(index);
            ScreenUpdater.updateRaceStats(selectedRace, raceHoursLabel, raceRoutesLabel, raceEntriesLabel, racePrizeMoneyLabel);
        }
    }

    @FXML
    private void onSelectRaceButtonClicked() {
        if (selectedRace == null) {
            showNoRacesSelectedAlert();
        } else {
            selectRaceButton.setStyle("-fx-background-color: LightGreen");
            chosenRace = selectedRace;
        }

    }

    @FXML
    private void onToStartLineButtonClicked() {
        if (chosenRace == null) {
            showNoRacesSelectedAlert();
        }
        getGameManager().setSelectedRace(chosenRace);
        getGameManager().startRace(chosenRace);
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

    private void showNoRacesSelectedAlert() {
        showAlert("No races selected", "Please select a race.");
    }

}
