package seng201.team0.gui;

import javafx.scene.control.*;

import seng201.team0.GameManager;

import seng201.team0.services.GarageService;

import seng201.team0.models.Car;
import seng201.team0.models.Upgrade;

import javafx.fxml.FXML;
import seng201.team0.utils.ScreenUpdater;

import java.util.List;

/**
 * Controller class for the Garage Screen GUI.
 * This screen allows the user to view the currently selected car, view up to 4 reserve cars owned by the player,
 * view any tuning parts (upgrades) owned by the player, swap the currently selected car and install a tuning part
 * (upgrade) on any car owned by the player.
 * The controller manages button clicks, updates the car and upgrade stats on the GUI and communicates with the
 * {@link GarageService} for installing upgrades.
 */
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
    @FXML private Label carUpgradesLabel;

    @FXML private Label currentCarLabel;

    /**
     * Constructs a GarageScreenController with the given {@link GameManager}
     * @param manager The GameManager managing game state.
     */
    public GarageScreenController(GameManager manager) {super(manager); }

    /**
     * Returns the path to the FXML file for this screen.
     * @return Path to FXML file.
     */
    @Override
    protected String getFxmlFile() {return "/fxml/garage.fxml";}

    /**
     * Returns the window title for this screen.
     * @return Title of the garage screen.
     */
    @Override
    protected String getTitle() {return "Garage Screen";}

    private List<Car> playerCars;
    private List<Upgrade> playerUpgrades;
    private Car currentCar;

    private Car selectedCar;
    private Upgrade selectedUpgrade;

    private Car chosenCar;
    private Upgrade chosenUpgrade;

    private final GarageService garageService = new GarageService();

    /**
     * Initializes the Garage screen controller.
     * Loads the player's cars and upgrades from the {@link GameManager}, sets up the button event handlers for selecting
     * cars and upgrades and displays the current car.
     * This method is immediately called by the JavaFX framework after FXML loading.
     */
    @FXML
    private void initialize() {
        GameManager gameManager = getGameManager();
        playerCars = gameManager.getPlayerCars();
        playerUpgrades = gameManager.getPlayerUpgrades();
        currentCar = gameManager.getCurrentCar();

        List<Button> playerCarButtons = List.of(car1Button, car2Button, car3Button, car4Button, car5Button);
        List<Button> playerUpgradeButtons = List.of(upgrade1Button, upgrade2Button, upgrade3Button);

        selectUpgradeButton.setOnAction(event -> onSelectUpgradeButtonClicked());
        selectCarButton.setOnAction(event -> onSelectCarButtonClicked());
        makeCurrentCarButton.setOnAction(event -> onMakeCurrentCarButtonClicked());
        installUpgradeButton.setOnAction(event -> onInstallUpgradeButtonClicked());
        backButton.setOnAction(event -> onBackButtonClicked());

        //Setup buttons to select cars
        for (int i = 0; i < playerCarButtons.size(); i++) {
            int index = i;
            playerCarButtons.get(i).setOnAction(event -> onPlayerCarButtonClicked(index));
        }

        // Setup buttons to select upgrades
        for (int i = 0; i < playerUpgradeButtons.size(); i++) {
            int index = i;
            playerUpgradeButtons.get(i).setOnAction(event -> onPlayerUpgradeButtonClicked(index));
        }

        // Display player car names on buttons
        for (int i = 0; i < playerCars.size(); i++) {
            playerCarButtons.get(i).setText(playerCars.get(i).getCarName());
        }

        // Display player upgrade names on buttons
        for (int i = 0; i < playerUpgrades.size(); i++) {
            playerUpgradeButtons.get(i).setText(playerUpgrades.get(i).getUpgradeName());
        }

        // Display currently selected car
        currentCarLabel.setText("Current car: " + currentCar.getCarName());

    }

    /**
     * Handles the event when a player car button is clicked.
     * Updates the selected car and refreshes the displayed car stats.
     *
     * @param index the index of the clicked car button in the player's car list
     */
    @FXML
    private void onPlayerCarButtonClicked(int index) {
        if (index >= 0 && index < playerCars.size()) {
            selectCarButton.setStyle("");
            selectedCar = playerCars.get(index);
            ScreenUpdater.updateCarStats(selectedCar, carSpeedLabel, carHandlingLabel, carReliabilityLabel, carFuelEconomyLabel, carUpgradesLabel);
        }
    }

    /**
     * Handles the event when a player upgrade button is clicked.
     * Updates the selected upgrade and refreshes the displayed upgrade stats.
     *
     * @param index the index of the clicked upgrade button in the player's upgrade list
     */
    @FXML
    private void onPlayerUpgradeButtonClicked(int index) {
        if (index >= 0 && index < playerUpgrades.size()) {
            selectUpgradeButton.setStyle("");
            selectedUpgrade = playerUpgrades.get(index);
            ScreenUpdater.updateUpgradeStats(selectedUpgrade, upgradeSpeedLabel, upgradeHandlingLabel, upgradeReliabilityLabel, upgradeFuelEconomyLabel);
        }
    }

    /**
     * Handles the event when the "Select Upgrade" button is clicked.
     * Sets the chosen upgrade to the currently selected upgrade and updates GUI state.
     * Shows an alert if no upgrade is selected.
     */
    @FXML
    private void onSelectUpgradeButtonClicked() {
        if (selectedUpgrade == null) {
            showAlert("No upgrade selected", "Please select an upgrade to install.");
        } else {
            chosenUpgrade = selectedUpgrade;
            selectUpgradeButton.setStyle("-fx-background-color: LightGreen");
            ScreenUpdater.updateUpgradeStats(chosenUpgrade, upgradeSpeedLabel, upgradeHandlingLabel, upgradeReliabilityLabel, upgradeFuelEconomyLabel);
        }
    }

    /**
     * Handles the event when the "Select Car" button is clicked.
     * Sets the chosen car to the currently selected car and updates GUI state.
     * Shows an alert if no car is selected.
     */
    @FXML
    private void onSelectCarButtonClicked() {
        if (selectedCar == null) {
            showAlert("No car selected", "Please select a car to install an upgrade on.");
        } else {
            chosenCar = selectedCar;
            selectCarButton.setStyle("-fx-background-color: LightGreen");
            ScreenUpdater.updateCarStats(chosenCar, carSpeedLabel, carHandlingLabel, carReliabilityLabel, carFuelEconomyLabel, carUpgradesLabel);
        }
    }

    /**
     * Handles the event when the "Install Upgrade" button is clicked.
     * Attempts to install the chosen upgrade on the chosen car using the GarageService.
     * Clears the car and upgrade stats on success, or shows an alert on failure.
     * Shows alerts if no upgrade or car is selected.
     */
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
            ScreenUpdater.updateCarStats(chosenCar, carSpeedLabel, carHandlingLabel, carReliabilityLabel, carFuelEconomyLabel, carUpgradesLabel);
            selectUpgradeButton.setStyle("");
            selectCarButton.setStyle("");
            chosenUpgrade = null;
            ScreenUpdater.updateUpgradeStats(null, upgradeSpeedLabel, upgradeHandlingLabel, upgradeReliabilityLabel, upgradeFuelEconomyLabel);
        } else {
            showAlert("Error", "Failed to install upgrade.");
        }

    }

    /**
     * Handles the event when the "Make Current Car" button is clicked.
     * Sets the currently selected car as the current car for the next race and updates the label or displays an error
     * message if the user has not selected a car to swap.
     */
    @FXML
    private void onMakeCurrentCarButtonClicked() {
        if (selectedCar != null) {
            currentCar = selectedCar;
            currentCarLabel.setText("Current car: " + currentCar.getCarName());
        } else {
            showAlert("No car selected", "Please select a car to swap out.");
        }
    }

    /**
     * Handles the event when the "Back" button is clicked.
     * Saves the current car selection in the GameManager and navigates back to the previous screen.
     */
    @FXML
    private void onBackButtonClicked() {
        getGameManager().setCurrentCar(currentCar);
        getGameManager().goBack();
    }

    /**
     * Shows an alert dialog with the specified title and message.
     * @param title the title of the alert dialog
     * @param message the content message of the alert dialog
     */
    @FXML
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}