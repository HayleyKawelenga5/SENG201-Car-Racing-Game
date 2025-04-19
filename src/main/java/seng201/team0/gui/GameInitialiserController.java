package seng201.team0.gui;

import seng201.team0.models.Car;
import seng201.team0.services.CarService;
import seng201.team0.services.GameInitialiser;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Slider;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.List;

public class GameInitialiserController {

    @FXML private TextField playerNameTextField;
    @FXML private Slider seasonLengthSlider;
    @FXML private ComboBox<String> difficultyComboBox;

    @FXML private Button car1Button;
    @FXML private Button car2Button;
    @FXML private Button car3Button;
    @FXML private Button car4Button;
    @FXML private Button car5Button;

    @FXML private Button selectedCar1Button;
    @FXML private Button selectedCar2Button;
    @FXML private Button selectedCar3Button;

    @FXML private Button selectButton;
    @FXML private Button deleteButton;
    @FXML private Button startGameButton;

    @FXML private Label moneyLabel;
    @FXML private Label speedLabel;
    @FXML private Label handlingLabel;
    @FXML private Label reliabilityLabel;
    @FXML private Label fuelEconomyLabel;
    @FXML private Label costLabel;

    private GameInitialiser game = new GameInitialiser();
    private List<Car> carOptions;
    private Car selectedCar;
    private List<Car> selectedCars = new ArrayList<>();
    private int selectedCarIndex = -1;

    @FXML
    public void initialize() {

        difficultyComboBox.getItems().addAll("EASY", "HARD");

        List<Button> carButtons = List.of(car1Button, car2Button, car3Button, car4Button, car5Button);
        List<Button> selectedCarButtons = List.of(selectedCar1Button, selectedCar2Button, selectedCar3Button);

        carOptions = game.getAvailableCars();

        for (int i = 0; i < carButtons.size(); i++) {
            int index = i;
            carButtons.get(i).setOnAction(event -> onCarButtonClicked(index));
        }

        for (int i = 0; i < selectedCarButtons.size(); i++) {
            int index = i;
            selectedCarButtons.get(i).setOnAction(event -> onSelectedCarButtonClicked(index));
        }

        updateSelectedCarButtons();
        moneyLabel.setText("Money: $" + game.getMoney());
    }

    private void updateStats(Car car) {
        if (car == null) {
            speedLabel.setText("Speed: ");
            handlingLabel.setText("Handling: ");
            reliabilityLabel.setText("Reliability: ");
            fuelEconomyLabel.setText("Fuel Economy: ");
            costLabel.setText("Cost: ");
        } else {
            speedLabel.setText("Speed: " + car.getSpeed());
            handlingLabel.setText("Handling: " + car.getHandling());
            reliabilityLabel.setText("Reliability: " + car.getReliability());
            fuelEconomyLabel.setText("Fuel Economy: " + car.getFuelEconomy());
            costLabel.setText("Cost: " + car.getCost());
        }
    }

    private void updateSelectedCarButtons() {
        List<Button> selectedCarButtons = List.of(selectedCar1Button, selectedCar2Button, selectedCar3Button);
        for (int i = 0; i < selectedCarButtons.size(); i++) {
            if (i < selectedCars.size()) {
                Car car = selectedCars.get(i);
                selectedCarButtons.get(i).setText("Car " + (selectedCarIndex + 1));
            } else {
                selectedCarButtons.get(i).setText("");
            }
        }
    }

    @FXML
    private void onCarButtonClicked(int index) {
        if (index >= 0 && index < carOptions.size()) {
            selectedCar = carOptions.get(index);
            selectedCarIndex = index;
            updateStats(selectedCar);
        }
    }

    @FXML
    private void onSelectedCarButtonClicked(int index) {
        if (index >= 0 && index < selectedCars.size()) {
            selectedCar = selectedCars.get(index);
            selectedCarIndex = index;
            updateStats(selectedCar);
        }
    }

    @FXML
    private void onSelectButtonClicked() {
        if (selectedCar != null && game.addCar(selectedCar)) {
            selectedCars = game.getSelectedCars();
            updateSelectedCarButtons();
            moneyLabel.setText("Money: $" + game.getMoney());
        }
    }

    @FXML
    private void onDeleteButtonClicked() {
        if (selectedCar != null) {
            game.deleteCar(selectedCar);
            selectedCars = game.getSelectedCars();
            updateSelectedCarButtons();
            moneyLabel.setText("Money: $" + game.getMoney());
        }
    }


    public String getFxmlFile() {
        return "/fxml/game_initialiser_screen.fxml";
    }


    public String getTitle() {
        return "Game Initialiser";
    }

}