package seng201.team0.gui;

import javafx.scene.control.*;
import seng201.team0.models.Car;
import seng201.team0.services.CarService;
import seng201.team0.services.GameInitialiser;

import javafx.fxml.FXML;

import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.List;

public class GameInitialiserController {

    @FXML private TextField playerNameTextField;
    @FXML private Slider seasonLengthSlider;
    @FXML private ChoiceBox<String> difficultyChoiceBox;

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
    public void initialise() {
        difficultyChoiceBox.getItems().clear();
        difficultyChoiceBox.getItems().addAll("EASY", "HARD");

        difficultyChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    game.selectDifficulty(newValue);
                    moneyLabel.setText("Money: $" + game.getMoney());
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

        List<Button> carButtons = List.of(car1Button, car2Button, car3Button, car4Button, car5Button);
        List<Button> selectedCarButtons = List.of(selectedCar1Button, selectedCar2Button, selectedCar3Button);

        carOptions = game.getAvailableCars();

        selectButton.setOnAction(event -> onSelectButtonClicked());

        for (int i = 0; i < carButtons.size(); i++) {
            int index = i;
            carButtons.get(i).setOnAction(event -> onCarButtonClicked(index));
        }

        for (int i = 0; i < selectedCarButtons.size(); i++) {
            int index = i;
            selectedCarButtons.get(i).setOnAction(event -> onSelectedCarButtonClicked(index));
        }

        startGameButton.setOnAction(event -> {
            if (selectedCars.size() < 1){
                showAlert("Not Enough Cars", "Please select at least one car to start.");
                return;
            }
            game.setSelectedCars(selectedCars);
        });

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
                selectedCarButtons.get(i).setText(car.getName());
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
        if (selectedCar == null) {
            showAlert("No Car Selected", "Please select a car first.");
            return;
        }

        if (selectedCars.contains(selectedCar)){
            showAlert("Car Already Selected", "You've already selected this car.");
            return;
        }

        if (selectedCars.size()>=3){
            showAlert("Selection Limit", "You can only select up to 3 cars.");
            return;
        }

        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Name Your Car");
        nameDialog.setHeaderText("Optional: Give your car a name! or Press OK");
        nameDialog.setContentText("Car name:");

        nameDialog.showAndWait().ifPresent(name -> {
            if (!name.isBlank()){
                selectedCar.setName(name);
            }
        });



        if (selectedCar != null && game.addCar(selectedCar)) {
            selectedCars = game.getSelectedCars();
            updateSelectedCarButtons();
            moneyLabel.setText("Money: $" + game.getMoney());
        }
    }

    @FXML
    private void onDeleteButtonClicked() {
        if (selectedCar == null){
            showAlert("No Car Selected", "Please select a car to delete.");
            return;
        }

        if (!selectedCars.contains(selectedCar)){
            showAlert("Invalid Selection", "The selected car is not in your chosen list.");
            return;
        }

        if (selectedCar != null) {
            game.deleteCar(selectedCar);
            selectedCar = null;
            selectedCarIndex = -1;
            selectedCars = game.getSelectedCars();
            updateSelectedCarButtons();
            updateStats(null);
            moneyLabel.setText("Money: $" + game.getMoney());
        }
    }

    @FXML
    private void onStartGameButtonClicked(){
        String playerName = playerNameTextField.getText();
        int seasonLength = (int) seasonLengthSlider.getValue();
        String difficulty = difficultyChoiceBox.getValue();

        try {
            game.selectName(playerName);
            game.selectSeasonLength(seasonLength);
            game.selectDifficulty(difficulty);

            if (game.getSelectedCars().size() != 3){
                showAlert("Car Selection Error", "Please select exactly 3 cars before starting the game.");
                return;
            }
            //proceed to next screen
        } catch (InvalidNameException e){
            showAlert("Name Error", e.getMessage());
        } catch (IllegalArgumentException e){
            showAlert("Input Error", e.getMessage());
        } catch (Exception e){
            showAlert("Unexpected Error", "An unexpected error occurred: " + e.getMessage());
        }

        }

    private void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public String getFxmlFile() {
        return "/fxml/game_initialiser_screen.fxml";
    }


    public String getTitle() {
        return "Game Initialiser";
    }

}