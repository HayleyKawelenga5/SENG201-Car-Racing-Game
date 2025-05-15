package seng201.team0.gui;

import javafx.scene.control.*;

import seng201.team0.GameManager;

import seng201.team0.services.GarageService;

import seng201.team0.models.Car;
import seng201.team0.models.Upgrade;

import javafx.fxml.FXML;
import seng201.team0.utils.ScreenUpdater;

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
            ScreenUpdater.updateCarStats(selectedCar, carSpeedLabel, carHandlingLabel, carReliabilityLabel, carFuelEconomyLabel);
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
            ScreenUpdater.updateCarStats(chosenCar, carSpeedLabel, carHandlingLabel, carReliabilityLabel, carFuelEconomyLabel);
        }
    }

    @FXML
    private void onInstallUpgradeButtonClicked() {
        if (chosenUpgrade == null) {
            showAlert("No upgrade selected", "Please select an upgrade to install.");
            return;
        }

        if (chosenCar == null) {
            showAlert("No car selected", "Please select a car to install an upgrade.");
            return;
        }

        if (garageService.installUpgrade(chosenCar, chosenUpgrade, playerUpgrades)) {
            List<Button> playerUpgradeButtons = List.of(upgrade1Button, upgrade2Button, upgrade3Button);
            ScreenUpdater.updateUpgradeButtons(playerUpgradeButtons, playerUpgrades);
            ScreenUpdater.updateCarStats(chosenCar, carSpeedLabel, carHandlingLabel, carReliabilityLabel, carFuelEconomyLabel);
            selectUpgradeButton.setStyle("");
            selectCarButton.setStyle("");
            chosenUpgrade = null;
            updateUpgradeStats(null);
        } else {
            showAlert("Error", "Failed to install upgrade.");
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