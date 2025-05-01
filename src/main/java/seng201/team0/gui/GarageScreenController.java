package seng201.team0.gui;

import javafx.scene.control.*;
import seng201.team0.GameManager;
import seng201.team0.models.Car;
import seng201.team0.services.CarService;
import seng201.team0.services.GameInitialiser;
import seng201.team0.services.MainScreen;
import seng201.team0.models.Garage;
import seng201.team0.services.GarageService;
import seng201.team0.services.UpgradeService;
import seng201.team0.models.Upgrade;

import javafx.fxml.FXML;

import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.List;

public class GarageScreenController extends ScreenController {

    @FXML private Button selectUpgradeButton;
    @FXML private Button selectCarButton;
    @FXML private Button makeCurrentCarButton;
    @FXML private Button installUpgradeButton;

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

    public GarageScreenController(GameManager manager) {super(manager);}

    @Override
    protected String getFxmlFile() {return "/fxml/garage.fxml";}

    @Override
    protected String getTitle() {return "Garage Screen";}


    private GarageService garage = new GarageService();
    private List<Car> selectedCars;
    private List<Upgrade> selectedUpgrades;
    private Car currentCar;
    private Car selectedCar;
    private Upgrade selectedUpgrade;
    private Car chosenCar;
    private Upgrade chosenUpgrade;

    @FXML
    public void initialize() {
        GameManager gameManager = getGameManager();
        selectedCars = gameManager.getSelectedCars();

        List<Button> carButtons = List.of(car1Button, car2Button, car3Button, car4Button, car5Button);
        List<Button> upgradeButtons = List.of(upgrade1Button, upgrade2Button, upgrade3Button, upgrade4Button, upgrade5Button);

        selectUpgradeButton.setOnAction(event -> onSelectUpgradeButtonClicked());
        selectCarButton.setOnAction(event -> onSelectCarButtonClicked());

        //makeCurrentCarButton.setOnAction(event -> onMakeCurrentCarButtonClicked());
        installUpgradeButton.setOnAction(event -> onInstallUpgradeButtonClciked());

        for (int i = 0; i < carButtons.size(); i++) {
            int index = i;
            carButtons.get(i).setOnAction(event -> onCarButtonClicked(index));
        }

        for (int i = 0; i < upgradeButtons.size(); i++) {
            int index = i;
            upgradeButtons.get(i).setOnAction(event -> onUpgradeButtonClicked(index));
        }

    }

    private void updateCarStats(Car car) {
        if (car == null) {
            carSpeedLabel.setText("Speed: ");
            carHandlingLabel.setText("Handling: ");
            carReliabilityLabel.setText("Reliability: ");
            carFuelEconomyLabel.setText("Fuel Economy: ");
        } else {
            carSpeedLabel.setText("Speed: " + car.getSpeed());
            carHandlingLabel.setText("Handling: " + car.getHandling());
            carReliabilityLabel.setText("Reliability: " + car.getReliability());
            carFuelEconomyLabel.setText("Fuel Economy: " + car.getFuelEconomy());
        }
    }

    private void updateUpgradeStats(Upgrade upgrade) {
        if (upgrade == null) {
            upgradeSpeedLabel.setText("Speed: ");
            upgradeHandlingLabel.setText("Handling: ");
            upgradeReliabilityLabel.setText("Reliability: ");
            upgradeFuelEconomyLabel.setText("Fuel Economy: ");
        } else {
            upgradeSpeedLabel.setText("Speed: " + upgrade.getSpeedUpgrade());
            upgradeHandlingLabel.setText("Handling: " + upgrade.getHandlingUpgrade());
            upgradeReliabilityLabel.setText("Reliability: " + upgrade.getReliabilityUpgrade());
            upgradeFuelEconomyLabel.setText("Fuel Economy: " + upgrade.getFuelEconomyUpgrade());
        }
    }


    @FXML
    private void onCarButtonClicked(int index) {
        if (index >= 0 && index < selectedCars.size()) {
            selectedCar = selectedCars.get(index);
            updateCarStats(selectedCar);
        }
    }


    @FXML
    private void onUpgradeButtonClicked(int index) {
        if (index >= 0 && index < selectedUpgrades.size()) {
            selectedUpgrade = selectedUpgrades.get(index);
            updateUpgradeStats(selectedUpgrade);
        }
    }


    @FXML
    private void onSelectUpgradeButtonClicked() {
        if (selectedUpgrade != null) {
            chosenUpgrade = selectedUpgrade;
            updateUpgradeStats(chosenUpgrade);
        }
    }


    @FXML
    private void onSelectCarButtonClicked() {
        if (selectedCar != null) {
            chosenCar = selectedCar;
            updateCarStats(chosenCar);
        }
    }

    @FXML
    private void onInstallUpgradeButtonClciked() {
        if (chosenUpgrade == null) {
            showAlert("No upgrade selected", "Please select an upgarde to install.");
            return;
        }

        if (chosenCar == null) {
            showAlert("No car selected", "Please select a car to install a part on.");
            return;
        }

        chosenCar.setSpeed(chosenCar.getSpeed() + chosenUpgrade.getSpeedUpgrade());
        chosenCar.setHandling(chosenCar.getHandling() + chosenUpgrade.getHandlingUpgrade());
        chosenCar.setReliability(chosenCar.getReliability() + chosenUpgrade.getReliabilityUpgrade());
        chosenCar.setFuelEconomy(chosenCar.getFuelEconomy() + chosenUpgrade.getFuelEconomyUpgrade());

        updateCarStats(chosenCar);

        //selectedUpgrades.removeUpgrade(chosenUpgrade);
        updateUpgradeStats(null);
    }

















    private void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}