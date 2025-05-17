package seng201.team0.gui;

import javafx.scene.control.*;
import seng201.team0.GameManager;
import seng201.team0.services.GameInitializer;

import seng201.team0.models.Car;
import seng201.team0.models.Upgrade;

import javafx.fxml.FXML;
import seng201.team0.utils.ScreenUpdater;

import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for the Start Screen GUI.
 *<p></p>
 * This controller handles user interaction for setting up a new game, including selecting player name, difficulty, cars
 * and season length.
 */
public class StartScreenController extends ScreenController {

    @FXML private TextField playerNameTextField;
    @FXML private Slider seasonLengthSlider;
    @FXML private ChoiceBox<String> difficultyChoiceBox;

    @FXML private Button car1Button;
    @FXML private Button car2Button;
    @FXML private Button car3Button;
    @FXML private Button car4Button;
    @FXML private Button car5Button;

    @FXML private Button playerCar1Button;
    @FXML private Button playerCar2Button;
    @FXML private Button playerCar3Button;

    @FXML private Button selectCarButton;
    @FXML private Button deleteCarButton;
    @FXML private Button startGameButton;

    @FXML private Label moneyLabel;

    @FXML private Label carSpeedLabel;
    @FXML private Label carHandlingLabel;
    @FXML private Label carReliabilityLabel;
    @FXML private Label carFuelEconomyLabel;
    @FXML private Label carCostLabel;

    private final GameInitializer gameInitializer = new GameInitializer();

    private List<Car> availableCars;
    private List<Car> playerCars = new ArrayList<>();
    private final List<Upgrade> playerUpgrades = new ArrayList<>();
    private List<Button> playerCarButtons;

    private Car selectedCar;
    private int selectedCarIndex = -1;

    /**
     * Constructs a StartScreenController with the given {@link GameManager}
     * @param manager The GameManager managing game state.
     */
    public StartScreenController(GameManager manager) {
        super(manager);
    }

    /**
     * Returns the path to the FXML file for this screen.
     * @return Path to FXML file.
     */
    @Override
    protected String getFxmlFile() {return "/fxml/start_screen.fxml";}

    /**
     * Returns the window title for this screen.
     * @return Title of the start screen.
     */
    @Override
    protected String getTitle() {return "Start Screen";}

    /**
     * Initializes the Start screen controller.
     * Sets up button actions and listeners.
     * This method is immediately called by the JavaFX framework after FXML loading.
     */
    @FXML
    private void initialize() {
        difficultyChoiceBox.getItems().clear();
        difficultyChoiceBox.getItems().addAll("EASY", "HARD");
        difficultyChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    gameInitializer.selectDifficulty(newValue);
                    playerCars.clear();
                    gameInitializer.setPlayerCars(playerCars);
                    updatePlayerCarButtons();
                    moneyLabel.setText("Money: $" + gameInitializer.getMoney());
                } catch (IllegalArgumentException e) {
                    showAlert("Difficulty Error", e.getMessage());
                }
            }
        });

        seasonLengthSlider.setMin(gameInitializer.getMinSeasonLength());
        seasonLengthSlider.setMax(gameInitializer.getMaxSeasonLength());
        seasonLengthSlider.setMajorTickUnit(1);
        seasonLengthSlider.setSnapToTicks(true);
        seasonLengthSlider.setShowTickLabels(true);
        seasonLengthSlider.setShowTickMarks(true);
        seasonLengthSlider.setValue(gameInitializer.getMinSeasonLength());
        seasonLengthSlider.valueProperty().addListener((observable, oldValue, newValue) -> seasonLengthSlider.setValue(newValue.intValue()));

        List<Button> availableCarButtons = List.of(car1Button, car2Button, car3Button, car4Button, car5Button);
        playerCarButtons = List.of(playerCar1Button, playerCar2Button, playerCar3Button);

        selectCarButton.setOnAction(event -> onSelectCarButtonClicked());
        deleteCarButton.setOnAction(event -> onDeleteCarButtonClicked());
        startGameButton.setOnAction(event -> onStartGameButtonClicked());

        availableCars = gameInitializer.getAvailableCars();

        for (int i = 0; i < availableCarButtons.size(); i++) {
            int index = i;
            availableCarButtons.get(i).setOnAction(event -> onAvailableCarButtonClicked(index));
        }
        for (int i = 0; i < playerCarButtons.size(); i++) {
            int index = i;
            playerCarButtons.get(i).setOnAction(event -> onPlayerCarButtonClicked(index));
        }

        updatePlayerCarButtons();
        moneyLabel.setText("Money: $" + gameInitializer.getMoney());

    }

    /**
     * Updates the car stats section of the screen with the selected car's details.
     *
     * @param car the car to display stats for, or null to clear display
     */
    private void updateCarStats(Car car) {
        ScreenUpdater.updateCarStats(car, carSpeedLabel, carHandlingLabel, carReliabilityLabel, carFuelEconomyLabel);
        if (car == null) {
            carCostLabel.setText("Cost: ");
        } else {
            carCostLabel.setText("Cost: $" + car.getCarCost());
        }
    }

    /**
     * Updates the player car buttons to reflect the current cars selected by the player.
     */
    private void updatePlayerCarButtons() {
        playerCarButtons = List.of(playerCar1Button, playerCar2Button, playerCar3Button);
        for (int i = 0; i < playerCarButtons.size(); i++) {
            if (i < playerCars.size()) {
                Car car = playerCars.get(i);
                playerCarButtons.get(i).setText(car.getCarName());
            } else {
                playerCarButtons.get(i).setText("");
            }
        }
    }

    /**
     * Handles the event when an available car button is clicked. Updates the stats on the screen for that car.
     *
     * @param index index of the selected available car
     */
    @FXML
    private void onAvailableCarButtonClicked(int index) {
        if (index >= 0 && index < availableCars.size()) {
            selectedCar = availableCars.get(index);
            selectedCarIndex = index;
            updateCarStats(selectedCar);
        }
    }

    /**
     * Handles the event when a player car button is clicked. Updates the stats on the screen for that car.
     *
     * @param index index of the player's selected car
     */
    @FXML
    private void onPlayerCarButtonClicked(int index) {
        if (index >= 0 && index < playerCars.size()) {
            selectedCar = playerCars.get(index);
            selectedCarIndex = index;
            updateCarStats(selectedCar);
        }
    }

    /**
     * Handles the event when the "Select Car" button is clicked.
     * Adds the selected car to the player's collection if valid i.e. the player has selected a difficulty (so the player
     * has been assigned a certain amount of starting money), the player has not already selected the chosen car, the
     * player has enough money and the player does not have the maximum number of cars. The player is given the option to
     * (optionally) name their car.
     */
    @FXML
    private void onSelectCarButtonClicked() {
        boolean difficultySelected = false;

        if (difficultyChoiceBox.getValue() == null) {
            showAlert("No difficulty selected", "Please select a difficulty.");
        } else {
            difficultySelected = true;
        }

        if (selectedCar == null) {
            showAlert("No car selected", "Please select a car.");
            return;
        }

        if (playerCars.contains(selectedCar)) {
            showAlert("Car already selected", "You've already selected this car.");
            return;
        }

        if (playerCars.size() >= gameInitializer.getMaxCars()){
            showAlert("Selection limit reached", "You can only select up to a maximum of three cars.");
            return;
        }
        if (gameInitializer.getMoney() < selectedCar.getCarCost()) {
            showAlert("Not enough money", "You don't have enough money to buy this car.");
            return;
        }

        if (difficultySelected) {
            TextInputDialog nameDialog = new TextInputDialog();
            nameDialog.setTitle("Name Your Car!");
            nameDialog.setHeaderText("Optional: Give your car a name or press OK");
            nameDialog.setContentText("Car name:");

            nameDialog.showAndWait().ifPresentOrElse(name -> {
                if (!name.isBlank()) {
                    selectedCar.setCarName(name);
                } else {
                    selectedCar.setCarName("Car " + (selectedCarIndex + 1));
                }
            }, () -> selectedCar.setCarName("Car " + (selectedCarIndex + 1)));

            if (gameInitializer.addCar(selectedCar)) {
                playerCars = gameInitializer.getPlayerCars();
                updatePlayerCarButtons();
                moneyLabel.setText("Money: $" + gameInitializer.getMoney());
                selectedCar = null;
                updateCarStats(null);
            }
        }
    }


    /**
     * Handles the event when the "Delete Car" button is clicked.
     * Removes the selected car from the player's collection if valid.
     */
    @FXML
    private void onDeleteCarButtonClicked() {
        if (selectedCar == null) {
            showAlert("No car selected", "Please select a car to delete.");
            return;
        }

        boolean canDeleteCar = gameInitializer.deleteCar(selectedCar);
        if (!canDeleteCar) {
            showAlert("Invalid selection", "You haven't selected this car.");
            return;
        }

        playerCars = gameInitializer.getPlayerCars();
        updatePlayerCarButtons();
        updateCarStats(null);
        moneyLabel.setText("Money: $" + gameInitializer.getMoney());
        selectedCar = null;
    }

    /**
     * Handles the event when the "Start Game" button is clicked.
     * Validates all inputs and transitions to the main game screen.
     */
    @FXML
    private void onStartGameButtonClicked() {
        String playerName = playerNameTextField.getText();
        int seasonLength = (int) seasonLengthSlider.getValue();
        String difficulty = difficultyChoiceBox.getValue();
        int money = gameInitializer.getMoney();

        if (gameInitializer.getPlayerCars().isEmpty()) {
            showAlert("No cars selected", "Please select at least one car.");
            return;
        }

        try {
            gameInitializer.selectName(playerName);
            gameInitializer.selectSeasonLength(seasonLength);
            gameInitializer.selectDifficulty(difficulty);
            Car currentCar = playerCars.getFirst();

            getGameManager().setRacesRemaining(seasonLength);
            gameInitializer.setPlayerCars(playerCars);
            getGameManager().toMainScreen(playerName, seasonLength, difficulty, playerCars, money, currentCar, playerUpgrades);
        } catch (InvalidNameException e) {
            showAlert("Name Error", e.getMessage());
        } catch (IllegalArgumentException e) {
            showAlert("Input Error", e.getMessage());
        } catch (Exception e) {
            showAlert("Unexpected Error", "An unexpected error occurred: " + e.getMessage());
        }

    }

    /**
     * Displays an alert dialog with the specified title and message.
     *
     * @param title   the title of the alert
     * @param message the message to display
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}