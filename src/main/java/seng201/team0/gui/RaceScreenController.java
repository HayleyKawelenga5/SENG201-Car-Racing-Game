package seng201.team0.gui;

import javafx.scene.control.*;

import seng201.team0.GameManager;
import seng201.team0.models.Race;
import seng201.team0.models.Route;
import seng201.team0.services.RaceEngine;
import seng201.team0.services.RaceService;
import seng201.team0.models.Car;

import javafx.fxml.FXML;

import java.util.List;
import java.util.Optional;

public class RaceScreenController extends ScreenController {

    @FXML
    private Label informationLabel;

    @FXML
    private Button route1Button;
    @FXML
    private Button route2Button;
    @FXML
    private Button route3Button;
    @FXML
    private Button selectRouteButton;

    @FXML
    private Label routeDescriptionLabel;
    @FXML
    private Label routeDistanceLabel;
    @FXML
    private Label routeFuelStopsLabel;
    @FXML
    private Label routeDifficultyLabel;

    @FXML
    private Button startRaceButton;

    @FXML
    private Label carNameLabel;
    @FXML
    private Label carSpeedLabel;
    @FXML
    private Label carHandlingLabel;
    @FXML
    private Label carReliabilityLabel;
    @FXML
    private Label carFuelEconomyLabel;

    @FXML
    private ProgressBar distanceProgressBar;
    @FXML
    private ProgressBar fuelProgressBar;

    @FXML
    private Label hoursLabel;

    @FXML
    private Button refuelButton;
    @FXML
    private Button continueButton;

    @FXML
    private Label positionLabel;
    @FXML
    private Label prizeMoneyLabel;

    @FXML
    private Button backButton;

    private double currentDistance = 0;
    private double maxDistance;
    private int currentHour = 0;
    private int nextFuelStopIndex = 0;
    private List<Integer> fuelStopDistances;

    private boolean isRaceRunning = false;


    private RaceService raceService = new RaceService();
    private Route chosenRoute;
    private Route selectedRoute;
    private Race selectedRace;
    private int selectedRouteIndex = -1;
    private List<Route> availableRoutes;
    private Car previewCurrentCar;
    private Car currentCar = getGameManager().getCurrentCar();
    private RaceEngine raceEngine;


    public RaceScreenController(GameManager manager) {
        super(manager);
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/race_screen.fxml";
    }

    @Override
    protected String getTitle() {
        return "Race Screen";
    }

    @FXML
    public void initialize() {
        Race selectedRace = getGameManager().getSelectedRace();
        availableRoutes = selectedRace.getAvailableRoutes();

        selectRouteButton.setOnAction(event -> onSelectRouteButtonClicked());
        startRaceButton.setOnAction(event -> onStartRaceButtonClicked());
        continueButton.setOnAction(event -> onContinueButtonClicked());

        List<Button> availableRouteButtons = List.of(route1Button, route2Button, route3Button);

        for (int i = 0; i < availableRouteButtons.size(); i++) {
            int index = i;
            availableRouteButtons.get(i).setOnAction(event -> onRouteButtonClicked(index));
        }

        carNameLabel.setText("Current car: " + currentCar.getCarName());
        updateCarStats(currentCar);

        updateRouteButtons();

    }

    private void updateRouteButtons() {
        List<Button> availableRouteButtons = List.of(route1Button, route2Button, route3Button);
        for (int i = 0; i < availableRouteButtons.size(); i++) {
            if (i < availableRoutes.size()) {
                availableRouteButtons.get(i).setText(availableRoutes.get(i).getRouteDescription().toString());
            } else {
                availableRouteButtons.get(i).setText("");
            }
        }
    }

    @FXML
    private void onSelectRouteButtonClicked() {
        if (selectedRoute == null) {
            showAlert("No route selected", "Please select a route.");
        } else {
            selectRouteButton.setStyle("-fx-background-color: LightGreen");
            chosenRoute = selectedRoute;
        }
    }

    @FXML
    private void onStartRaceButtonClicked() {
        if (chosenRoute == null) {
            showAlert("No route selected", "Please select and confirm a route first.");
            return;
        }

        informationLabel.setVisible(false);
        route1Button.setVisible(false);
        route2Button.setVisible(false);
        route3Button.setVisible(false);
        selectRouteButton.setVisible(false);
        startRaceButton.setVisible(false);
        refuelButton.setDisable(true);
        backButton.setDisable(true);
        continueButton.setDisable(false);

        raceEngine = new RaceEngine(getGameManager().getSelectedRace(), chosenRoute, getGameManager().getCurrentCar(), getGameManager().getDifficulty(), getGameManager().getMoney());
        this.startRace();
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


    @FXML
    private void onRouteButtonClicked(int index) {
        if (index >= 0 && index < availableRoutes.size()) {
            previewCurrentCar = null;
            previewCurrentCar = raceService.previewApplyMultipliers(currentCar, availableRoutes.get(index).getRouteDifficultyMultiplier());
            updateCarStats(previewCurrentCar);
            selectRouteButton.setStyle("");
            selectedRoute = availableRoutes.get(index);
            selectedRouteIndex = index;
            updateRouteStats(selectedRoute);
        }
    }

    private void updateRouteStats(Route route) {
        if (route == null) {
            routeDescriptionLabel.setText("Description: ");
            routeDistanceLabel.setText("Distance: ");
            routeFuelStopsLabel.setText("Fuel Stops: ");
            routeDifficultyLabel.setText("Difficulty: ");
        } else {
            routeDescriptionLabel.setText("Description: " + route.getRouteDescription());
            routeDistanceLabel.setText("Distance: " + route.getRouteDistance() + "km");
            routeFuelStopsLabel.setText("Fuel Stops: " + route.getRouteFuelStops());
            routeDifficultyLabel.setText("Difficulty: " + String.format("%.2f", route.getRouteDifficultyMultiplier()));
        }

    }

    @FXML
    private void onContinueButtonClicked(){
        raceEngine.resumeRaceFromUI();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean showRefuelConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Fuel Stop");
        alert.setHeaderText("Do you want to refuel?");
        alert.setContentText("Refueling will cost you 20km");

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == yesButton;
    }

    private void startRace() {
        raceEngine.startRaceAsync(new RaceEngine.RaceUpdateListener() {

            @Override
            public void onProgressUpdate(int distance, int fuel, int timeElapsed) {
                distanceProgressBar.setProgress((double) distance / chosenRoute.getRouteDistance());
                fuelProgressBar.setProgress((double) fuel / 100); // assuming 100 max fuel
                hoursLabel.setText("Time: " + timeElapsed + "h");
            }

            @Override
            public void onFuelStop(int stopIndex, int currentDistance) {
                boolean shouldRefuel = showRefuelConfirmation();
                if (shouldRefuel) {
                    raceEngine.handleFuelStop(getGameManager().getCurrentCar());
                }
            }

            @Override
            public void onRaceEnd(boolean finished, boolean outOfFuel, boolean outOfTime) {
                if (finished) {
                    showAlert("Race finished", "You finished the race!");
                } else if (outOfFuel) {
                    showAlert("No fuel!", "You ran out of fuel!");
                } else if (outOfTime) {
                    showAlert("No time!", "Time ran out!");
                }
            }
        });


    }
}
