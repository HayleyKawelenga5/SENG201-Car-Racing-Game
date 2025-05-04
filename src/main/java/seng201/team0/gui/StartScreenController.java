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




    private StartScreen startScreen = new StartScreen();

    private List<Car> availableCars;
    private List<Car> playerCars = new ArrayList<>();
    private List<Upgrade> playerUpgrades = new ArrayList<>();

    private Car selectedCar;
    private Car currentCar;

    private int selectedCarIndex = -1;




    private GameManager gameManager;

    public StartScreenController(GameManager manager) {
        super(manager);
    }

    @Override
    protected String getFxmlFile() {return "/fxml/start_screen.fxml";}

    @Override
    protected String getTitle() {return "Start Screen";}


    @FXML
    public void initialize() {
        difficultyChoiceBox.getItems().clear();
        difficultyChoiceBox.getItems().addAll("EASY", "HARD");

        difficultyChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    startScreen.selectDifficulty(newValue);
                    playerCars.clear();
                    startScreen.setPlayerCars(playerCars);
                    updatePlayerCarButtons();
                    moneyLabel.setText("Money: $" + startScreen.getMoney());
                } catch (IllegalArgumentException e) {
                    showAlert("Difficulty Error", e.getMessage());
                }
            }
        });

        seasonLengthSlider.setMin(5);
        seasonLengthSlider.setMax(15);
        seasonLengthSlider.setMajorTickUnit(1);
        seasonLengthSlider.setSnapToTicks(true);
        seasonLengthSlider.setShowTickLabels(true);
        seasonLengthSlider.setShowTickMarks(true);
        seasonLengthSlider.setValue(5);
        seasonLengthSlider.valueProperty().addListener((observable, oldValue, newValue) -> {seasonLengthSlider.setValue(newValue.intValue());});

        List<Button> availableCarButtons = List.of(car1Button, car2Button, car3Button, car4Button, car5Button);
        List<Button> playerCarButtons = List.of(playerCar1Button, playerCar2Button, playerCar3Button);

        selectCarButton.setOnAction(event -> onSelectCarButtonClicked());
        deleteCarButton.setOnAction(event -> onDeleteCarButtonClicked());
        startGameButton.setOnAction(event -> onStartGameButtonClicked());

        availableCars = startScreen.getAvailableCars();

        for (int i = 0; i < availableCarButtons.size(); i++) {
            int index = i;
            availableCarButtons.get(i).setOnAction(event -> onAvailableCarButtonClicked(index));
        }

        for (int i = 0; i < playerCarButtons.size(); i++) {
            int index = i;
            playerCarButtons.get(i).setOnAction(event -> onPlayerCarButtonClicked(index));
        }

        updatePlayerCarButtons();
        moneyLabel.setText("Money: $" + startScreen.getMoney());

    }

    private void updateStats(Car car) {
        if (car == null) {
            carSpeedLabel.setText("Speed: ");
            carHandlingLabel.setText("Handling: ");
            carReliabilityLabel.setText("Reliability: ");
            carFuelEconomyLabel.setText("Fuel Economy: ");
            carCostLabel.setText("Cost: ");
        } else {
            carSpeedLabel.setText("Speed: " + car.getCarSpeed());
            carHandlingLabel.setText("Handling: " + car.getCarHandling());
            carReliabilityLabel.setText("Reliability: " + car.getCarReliability());
            carFuelEconomyLabel.setText("Fuel Economy: " + car.getCarFuelEconomy());
            carCostLabel.setText("Cost: $" + car.getCarCost());
        }
    }

    private void updatePlayerCarButtons() {
        List<Button> playerCarButtons = List.of(playerCar1Button, playerCar2Button, playerCar3Button);
        for (int i = 0; i < playerCarButtons.size(); i++) {
            if (i < playerCars.size()) {
                Car car = playerCars.get(i);
                playerCarButtons.get(i).setText(car.getCarName());
            } else {
                playerCarButtons.get(i).setText("");
            }
        }
    }

    @FXML
    private void onAvailableCarButtonClicked(int index) {
        if (index >= 0 && index < availableCars.size()) {
            selectedCar = availableCars.get(index);
            selectedCarIndex = index;
            updateStats(selectedCar);
        }
    }

    @FXML
    private void onPlayerCarButtonClicked(int index) {
        if (index >= 0 && index < playerCars.size()) {
            selectedCar = playerCars.get(index);
            selectedCarIndex = index;
            updateStats(selectedCar);
        }
    }

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

        if (playerCars.size() >= 3){
            showAlert("Selection limit reached", "You can only select up to a maximum of three cars.");
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
            }, () -> {
                selectedCar.setCarName("Car " + (selectedCarIndex + 1));
            });

            if (selectedCar != null && startScreen.addCar(selectedCar)) {
                playerCars = startScreen.getPlayerCars();
                updatePlayerCarButtons();
                moneyLabel.setText("Money: $" + startScreen.getMoney());
            }
        }
    }

    @FXML
    private void onDeleteCarButtonClicked() {
        if (selectedCar == null) {
            showAlert("No car selected", "Please select a car to delete.");
            return;
        }

        if (!playerCars.contains(selectedCar)) {
            showAlert("Invalid selection", "You haven't selected this car.");
            return;
        }

        if (selectedCar != null) {
            startScreen.deleteCar(selectedCar);
            playerCars.remove(selectedCar);
            updatePlayerCarButtons();
            updateStats(null);
            moneyLabel.setText("Money: $" + startScreen.getMoney());
        }
    }

    @FXML
    private void onStartGameButtonClicked() {
        String playerName = playerNameTextField.getText();
        int seasonLength = (int) seasonLengthSlider.getValue();
        String difficulty = difficultyChoiceBox.getValue();
        int money = startScreen.getMoney();

        if (startScreen.getPlayerCars().size() <= 0) {
            showAlert("Not cars selected", "Please select at least one car.");
            return;
        }

        try {
            startScreen.selectName(playerName);
            startScreen.selectSeasonLength(seasonLength);
            startScreen.selectDifficulty(difficulty);
            currentCar = playerCars.get(0);


            startScreen.setPlayerCars(playerCars);
            getGameManager().toMainScreen(playerName, seasonLength, difficulty, playerCars, money, currentCar, playerUpgrades);
        } catch (InvalidNameException e) {
            showAlert("Name Error", e.getMessage());
        } catch (IllegalArgumentException e) {
            showAlert("Input Error", e.getMessage());
        } catch (Exception e) {
            showAlert("Unexpected Error", "An unexpected error occurred: " + e.getMessage());
        }

    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}