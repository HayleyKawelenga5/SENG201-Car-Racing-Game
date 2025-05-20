package seng201.team0.gui;

import javafx.scene.control.*;

import seng201.team0.GameManager;

import seng201.team0.models.Race;
import seng201.team0.services.RaceService;

import seng201.team0.models.Car;

import javafx.fxml.FXML;
import seng201.team0.utils.ScreenUpdater;

import java.util.List;

/**
 * Controller for the Main Screen of the game.
 * This class manages the interaction between the GUI elements and the game logic on the main screen including
 * viewing player money, season length and number of remaining races, visiting the shop, going to the garage and selecting
 * and starting a race.
 * The controller uses {@link GameManager} to access game data and operations and {@link ScreenUpdater} utility methods
 * to update GUI labels efficiently
 */
public class MainScreenController extends ScreenController implements ScreenControllerInterface {

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
    @FXML private Label currentCarUpgradesLabel;

    @FXML private Label seasonLengthLabel;
    @FXML private Label racesRemainingLabel;
    @FXML private Label moneyLabel;

    @FXML private Label raceHoursLabel;
    @FXML private Label raceRoutesLabel;
    @FXML private Label raceEntriesLabel;
    @FXML private Label racePrizeMoneyLabel;

    /**
     * List of races available for the player to choose from.
     */
    private List<Race> availableRaces;

    /**
     * The race currently selected by the player, typically for display or confirmation.
     */
    private Race selectedRace;

    /**
     * The race officially chosen to be played next.
     */
    private Race chosenRace;

    /**
     * Constructs a MainScreenController with the given {@link GameManager}
     * @param manager The GameManager managing game state. (GameManager)
     */
    public MainScreenController(GameManager manager) {
        super(manager);
    }

    /**
     * Returns the path to the FXML file for this screen.
     * @return Path to FXML file.
     */
    @Override
    protected String getFxmlFile() {return "/fxml/main_screen.fxml";}

    /**
     * Returns the window title for this screen.
     * @return Title of the main screen.
     */
    @Override
    protected String getTitle() {return "Main Screen";}

    /**
     * Initializes the main screen GUI components and game data.
     * This method is called automatically by the JavaFX framework after the FXML is loaded.
     */
    @FXML
    public void initialize() {
        setupGameData();
        setupRaceButtons();
        setupNavigationButtons();
        updateLabels();
    }

    /**
     * Initializes game data required by the main screen, including generating the list of available races and initializing
     * the player's current currently selected car.
     */
    private void setupGameData(){
        GameManager gameManager = getGameManager();
        gameManager.initializePlayerCar();
        String difficulty = gameManager.getDifficulty();
        RaceService raceService = new RaceService();
        availableRaces = raceService.generateRaces(3, difficulty);
    }

    /**
     * Configures the race selection buttons with their corresponding event handlers.
     */
    private void setupRaceButtons(){
        List<Button> availableRaceButtons = List.of(race1Button, race2Button, race3Button);
        for (int i = 0; i < availableRaceButtons.size(); i++) {
            int index = i;
            availableRaceButtons.get(i).setOnAction(event -> onAvailableRaceButtonClicked(index));
        }
        selectRaceButton.setOnAction(event -> onSelectRaceButtonClicked());
    }

    /**
     * Configures navigation buttons (start line, garage, shop) with their event handlers.
     */
    private void setupNavigationButtons(){
        toStartLineButton.setOnAction(event -> onToStartLineButtonClicked());
        toGarageButton.setOnAction(event -> onToGarageButtonClicked());
        toShopButton.setOnAction(event -> onToShopButtonClicked());
    }

    /**
     * Updates all the labels on the screen to reflect the current game state,
     * including player car stats and game info (money, races remaining, season length).
     */
    private void updateLabels(){
        GameManager mainScreen = getGameManager();
        Car currentCar = mainScreen.getCurrentCar();
        int money = mainScreen.getMoney();
        int racesRemaining = mainScreen.getRacesRemaining();
        int seasonLength = mainScreen.getSeasonLength();
        currentCarNameLabel.setText(currentCar.getCarName());
        ScreenUpdater.updateCarStats(currentCar, currentCarSpeedLabel, currentCarHandlingLabel, currentCarReliabilityLabel, currentCarFuelEconomyLabel, currentCarUpgradesLabel);
        ScreenUpdater.updateGameInfo(money, racesRemaining, seasonLength, moneyLabel, racesRemainingLabel, seasonLengthLabel);
    }

    /**
     * Event handler for when a race button is clicked.
     * Updates the selected race and refreshes race stats in the GUI.
     *
     * @param index the index of the race selected (int)
     */
    @FXML
    private void onAvailableRaceButtonClicked (int index) {
        if (index >= 0 && index < availableRaces.size()) {
            chosenRace = null;
            selectRaceButton.setStyle("");
            selectedRace = availableRaces.get(index);
            ScreenUpdater.updateRaceStats(selectedRace, raceHoursLabel, raceRoutesLabel, raceEntriesLabel, racePrizeMoneyLabel);
        }
    }

    /**
     * Event handler for when the "Select Race" button is clicked.
     * Sets the chosen race to the currently selected race, if any.
     * Otherwise, shows an alert prompting the user to select a race first.
     */
    @FXML
    private void onSelectRaceButtonClicked() {
        if (selectedRace == null) {
            showNoRacesSelectedAlert();
        } else {
            selectRaceButton.setStyle("-fx-background-color: LightGreen");
            chosenRace = selectedRace;
        }
    }

    /**
     * Event handler for when the "Go to Start Line" button is clicked.
     * Starts the race if a race is chosen, otherwise shows an alert.
     */
    @FXML
    private void onToStartLineButtonClicked() {
        if (chosenRace == null) {
            showNoRacesSelectedAlert();
            return;
        }
        getGameManager().setSelectedRace(chosenRace);
        getGameManager().startRace(chosenRace);
    }

    /**
     * Event handler for when the "Go to Garage" button is clicked.
     * Navigates the user to the garage screen.
     */
    @FXML
    private void onToGarageButtonClicked() {
        getGameManager().goToGarage();
    }

    /**
     * Event handler for when the "Go to Shop" button is clicked.
     * Navigates the user to the shop screen.
     */
    @FXML
    private void onToShopButtonClicked() {
        getGameManager().goToShop();
    }

    /**
     * Shows an alert dialog with the specified title and message.
     * @param title the title of the alert dialog (String)
     * @param message the content message of the alert dialog (String)
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Shows a specific alert prompting the user to select a race,
     */
    private void showNoRacesSelectedAlert() {
        showAlert("No races selected", "Please select a race.");
    }
}
