package seng201.team0.gui;

import javafx.scene.control.*;

import seng201.team0.GameManager;

import seng201.team0.services.MainScreen;

import seng201.team0.services.GarageService;

import seng201.team0.models.Car;
import seng201.team0.models.Garage;
import seng201.team0.models.Upgrade;

import javafx.fxml.FXML;

import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.List;

public class GarageScreenController extends ScreenController {

    @FXML private Button selectUpgradeButton;
    @FXML private Button selectCarButton;
    @FXML private Button installUpgradeButton;
    @FXML private Button makeCurrentCarButton;
    @FXML private Button backButton;

    @FXML private Button upgrade1Button;
    @FXML private Button upgrade2Button;
    @FXML private Button upgrade3Button;
    @FXML private Button upgrade4Button;
    @FXML private Button upgrade5Button;

    @FXML private Button car1Button;
    @FXML private Button car2Button;
    @FXML private Button car3Button;
    @FXML private Button car4Button;
    @FXML private Button car5Button;

    @FXML private Label upgradeSpeedLabel;
    @FXML private Label upgradeHandlingLabel;
    @FXML private Label upgradeReliabilityLabel;
    @FXML private Label upgradeFuelEconomyLabel;

    @FXML private Label carSpeedLabel;
    @FXML private Label carHandlingLabel;
    @FXML private Label carReliabilityLabel;
    @FXML private Label carFuelEconomyLabel;

    @FXML private Label currentCarLabel;

    public GarageScreenController(GameManager manager) {super(manager); }

    @Override
    protected String getFxmlFile() {return "/fxml/garage.fxml";}

    @Override
    protected String getTitle() {return "Garage Screen";}

    private List<Car> playerCars;
    private List<Upgrade> playerUpgrades;
    private Car currentCar;

    private Car selectedCar;
    private Upgrade selectedUpgrade;

    private Car chosenCar;
    private Upgrade chosenUpgrade;

    private int money;

    private GarageService garageService = new GarageService();

    @FXML
    public void initialize() {
        GameManager garageScreen = getGameManager();
        playerCars = garageScreen.getPlayerCars();
        playerUpgrades = garageScreen.getPlayerUpgrades();
        currentCar = garageScreen.getCurrentCar();

        List<Button> playerCarButtons = List.of(car1Button, car2Button, car3Button, car4Button, car5Button);
        List<Button> playerUpgradeButtons = List.of(upgrade1Button, upgrade2Button, upgrade3Button);

        selectUpgradeButton.setOnAction(event -> onSelectUpgradeButtonClicked());
        selectCarButton.setOnAction(event -> onSelectCarButtonClicked());
        makeCurrentCarButton.setOnAction(event -> onMakeCurrentCarButtonClicked());
        installUpgradeButton.setOnAction(event -> onInstallUpgradeButtonClicked());
        backButton.setOnAction(event -> onBackButtonClicked());

        for (int i = 0; i < playerCarButtons.size(); i++) {
            int index = i;
            playerCarButtons.get(i).setOnAction(event -> onPlayerCarButtonClicked(index));
        }

        for (int i = 0; i < playerUpgradeButtons.size(); i++) {
            int index = i;
            playerUpgradeButtons.get(i).setOnAction(event -> onPlayerUpgradeButtonClicked(index));
        }

        for (int i = 0; i < playerCars.size(); i++) {
            playerCarButtons.get(i).setText(playerCars.get(i).getCarName());
        }

        for (int i = 0; i < playerUpgrades.size(); i++) {
            playerUpgradeButtons.get(i).setText(playerUpgrades.get(i).getUpgradeName());
        }

        currentCarLabel.setText("Current car: " + currentCar.getCarName());

    }

    private void updateCarStats(Car car) {
        if (car == null) {
            carSpeedLabel.setText("Speed: ");
            carHandlingLabel.setText("Handling: ");
            carReliabilityLabel.setText("Reliability: ");
            carFuelEconomyLabel.setText("Fuel Economy: ");
        } else {
            carSpeedLabel.setText("Speed: " + car.getCarSpeed());
            carHandlingLabel.setText("Handling: " + car.getCarHandling());
            carReliabilityLabel.setText("Reliability: " + car.getCarReliability());
            carFuelEconomyLabel.setText("Fuel Economy: " + car.getCarFuelEconomy());
        }
    }

    private void updateUpgradeStats(Upgrade upgrade) {
        if (upgrade == null) {
            upgradeSpeedLabel.setText("Speed: ");
            upgradeHandlingLabel.setText("Handling: ");
            upgradeReliabilityLabel.setText("Reliability: ");
            upgradeFuelEconomyLabel.setText("Fuel Economy: ");
        } else {
            upgradeSpeedLabel.setText("Speed: " + upgrade.getUpgradeSpeed());
            upgradeHandlingLabel.setText("Handling: " + upgrade.getUpgradeHandling());
            upgradeReliabilityLabel.setText("Reliability: " + upgrade.getUpgradeReliability());
            upgradeFuelEconomyLabel.setText("Fuel Economy: " + upgrade.getUpgradeFuelEconomy());
        }
    }

    @FXML
    private void onPlayerCarButtonClicked(int index) {
        if (index >= 0 && index < playerCars.size()) {
            selectCarButton.setStyle("");
            selectedCar = playerCars.get(index);
            updateCarStats(selectedCar);
        }
    }

    @FXML
    private void onPlayerUpgradeButtonClicked(int index) {
        if (index >= 0 && index < playerUpgrades.size()) {
            selectUpgradeButton.setStyle("");
            selectedUpgrade = playerUpgrades.get(index);
            updateUpgradeStats(selectedUpgrade);
        }
    }

    @FXML
    private void onSelectUpgradeButtonClicked() {
        if (selectedUpgrade == null) {
            showAlert("No upgrade selected", "Please select an upgrade to install.");
        } else {
            chosenUpgrade = selectedUpgrade;
            selectUpgradeButton.setStyle("-fx-background-color: LightGreen");
            updateUpgradeStats(chosenUpgrade);
        }
    }

    @FXML
    private void onSelectCarButtonClicked() {
        if (selectedCar == null) {
            showAlert("No car selected", "Please select a car to install an upgrade on.");
        } else {
            chosenCar = selectedCar;
            selectCarButton.setStyle("-fx-background-color: LightGreen");
            updateCarStats(chosenCar);
        }
    }

    @FXML
    private void onInstallUpgradeButtonClicked() {
        if (chosenUpgrade == null) {
            showAlert("No upgrade selected", "Please select an upgarde to install.");
            return;
        }

        if (chosenCar == null) {
            showAlert("No car selected", "Please select a car to install an upgrade.");
            return;
        }

        chosenCar.setCarSpeed(chosenCar.getCarSpeed() + chosenUpgrade.getUpgradeSpeed());
        chosenCar.setCarHandling(chosenCar.getCarHandling() + chosenUpgrade.getUpgradeHandling());
        chosenCar.setCarReliability(chosenCar.getCarReliability() + chosenUpgrade.getUpgradeReliability());
        chosenCar.setCarFuelEconomy(chosenCar.getCarFuelEconomy() + chosenUpgrade.getUpgradeFuelEconomy());
        chosenCar.setCarCost(chosenCar.getCarCost() + chosenUpgrade.getUpgradeCost());

        updateCarStats(chosenCar);

        playerUpgrades.remove(chosenUpgrade);
        updatePlayerUpgradeButtons();
        selectUpgradeButton.setStyle("");
        selectCarButton.setStyle("");
        chosenUpgrade = null;
        updateUpgradeStats(null);
    }

    private void updatePlayerUpgradeButtons() {
        List<Button> playerUpgradeButtons = List.of(upgrade1Button, upgrade2Button, upgrade3Button);
        for (int i = 0; i < playerUpgradeButtons.size(); i++) {
            if (i < playerUpgrades.size()) {
                playerUpgradeButtons.get(i).setText(playerUpgrades.get(i).getUpgradeName());
            } else {
                playerUpgradeButtons.get(i).setText("");
            }
        }
    }

    @FXML
    public void onMakeCurrentCarButtonClicked() {
        if (selectedCar != null) {
            currentCar = selectedCar;
            currentCarLabel.setText("Current car: " + currentCar.getCarName());
        }
    }

    @FXML
    public void onBackButtonClicked() {
        getGameManager().setCurrentCar(currentCar);
        getGameManager().goBack();
    }

















    private void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}