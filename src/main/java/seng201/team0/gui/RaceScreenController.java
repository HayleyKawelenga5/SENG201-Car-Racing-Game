package seng201.team0.gui;

import javafx.scene.control.*;

import javafx.application.Platform;

import seng201.team0.GameManager;
import seng201.team0.models.Race;
import seng201.team0.models.Route;
import seng201.team0.services.RaceEngine;
import seng201.team0.services.RaceService;
import seng201.team0.models.Car;

import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.List;

public class RaceScreenController extends ScreenController {

    @FXML private Label informationLabel;

    @FXML private Button route1Button;
    @FXML private Button route2Button;
    @FXML private Button route3Button;
    @FXML private Button selectRouteButton;

    @FXML private Label routeDescriptionLabel;
    @FXML private Label routeDistanceLabel;
    @FXML private Label routeFuelStopsLabel;
    @FXML private Label routeDifficultyLabel;

    @FXML private Button startRaceButton;

    @FXML private Label currentDistanceLabel;
    @FXML private Label currentFuelLabel;

    @FXML private Label carNameLabel;
    @FXML private Label carSpeedLabel;
    @FXML private Label carHandlingLabel;
    @FXML private Label carReliabilityLabel;
    @FXML private Label carFuelEconomyLabel;

    @FXML private ProgressBar distanceProgressBar;
    @FXML private ProgressBar fuelProgressBar;

    @FXML private Label hoursLabel;

    @FXML private Button refuelButton;
    @FXML private Button continueButton;

    @FXML private Label positionLabel;
    @FXML private Label prizeMoneyLabel;

    @FXML private Button backButton;

    private RaceService raceService = new RaceService();
    private Route chosenRoute;
    private Route selectedRoute;
    private Race selectedRace;
    private int selectedRouteIndex = -1;
    private List<Route> availableRoutes;
    private Car previewCurrentCar;
    private Car currentCarCopy;
    private Car currentCar;
    private RaceEngine raceEngine;
    private int prizeMoney;

    private int currentDistance;
    private int nextFuelStopDistance;

    private RaceScreenController raceScreenController;

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
        refuelButton.setOnAction(event -> onRefuelButtonClicked());
        backButton.setOnAction(event -> onBackButtonClicked());

        List<Button> availableRouteButtons = List.of(route1Button, route2Button, route3Button);

        for (int i = 0; i < availableRouteButtons.size(); i++) {
            int index = i;
            availableRouteButtons.get(i).setOnAction(event -> onRouteButtonClicked(index));
        }

        backButton.setDisable(true);

        currentCar = getGameManager().getCurrentCar();
        carNameLabel.setText("Current car: " + currentCar.getCarName());
        updateCarStats(currentCar);
        currentCarCopy = raceService.copyCar(currentCar);

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
            currentCarCopy = raceService.copyCar(currentCar);
        }
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
            previewCurrentCar = raceService.previewMultiplier(currentCarCopy, availableRoutes.get(index).getRouteDifficultyMultiplier());
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
    private void onRefuelButtonClicked() {
        raceEngine.refuel(currentCarCopy);
        currentFuelLabel.setText("Current fuel: " + currentCarCopy.getCarFuelAmount());
        fuelProgressBar.setProgress((double) currentCarCopy.getCarFuelAmount() / currentCarCopy.getCarFuelEconomy());
        refuelButton.setDisable(true);
    }


    public void onFuelStop(int currentDistance, int nextFuelStopDistance, int fuelAmount) {
        currentDistanceLabel.setText("Current distance: " + nextFuelStopDistance + "km");
        currentFuelLabel.setText("Current fuel: " + (fuelAmount - ((nextFuelStopDistance - currentDistance) / 2)));
        distanceProgressBar.setProgress(nextFuelStopDistance / (double) chosenRoute.getRouteDistance());
        fuelProgressBar.setProgress((double) (fuelAmount - ((nextFuelStopDistance - currentDistance) / 2)) / currentCarCopy.getCarFuelEconomy());
        showInfo("Fuel Stop", "You are at a fuel stop!");
        refuelButton.setDisable(false);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle(title);
        info.setHeaderText(null);
        info.setContentText(message);
        info.showAndWait();
    }

    @FXML
    public void onBackButtonClicked() {
        int money = getGameManager().getMoney() + prizeMoney;
        getGameManager().setMoney(money);
        getGameManager().setSeasonLength(getGameManager().getSeasonLength()-1);
        getGameManager().toMainScreenFromRace(money, currentCar, getGameManager().getSeasonLength());
//        currentCar.setCarSpeed(Math.max(0, currentCar.getCarSpeed() - 10));
//        currentCar.setCarHandling(Math.max(0, currentCar.getCarHandling() - 10));
//        currentCar.setCarReliability(Math.max(0, currentCar.getCarReliability() - 10));
//        currentCar.setCarFuelEconomy(Math.max(0, currentCar.getCarFuelEconomy() - 10));
//        currentCar.setCarCost(Math.max(0, currentCar.getCarCost() - 40));
        getGameManager().setCurrentCar(currentCar);
    }

    public void onPlayerFinished(int playerPosition, int prizeMoney) {
        this.prizeMoney = prizeMoney;
        showInfo("Race finished", "Position: " + playerPosition + " | Prize Money: $" + prizeMoney);
        positionLabel.setText("Place: " + playerPosition);
        prizeMoneyLabel.setText("Prize Money: $" + prizeMoney);
        backButton.setDisable(false);
        continueButton.setDisable(true);
    }

    public void onPlayerDNF() {
        showAlert("Out of fuel!", "Position: DNF | Prize money: $0");
        positionLabel.setText("Place: DNF");
        prizeMoneyLabel.setText("Prize Money: $0");
        backButton.setDisable(false);
        continueButton.setDisable(true);
    }

    public void onPlayerOutOfTime() {
        showAlert("Out of time!", "Position: DNF | Prize money: $0");
        positionLabel.setText("Place: DNF");
        prizeMoneyLabel.setText("Prize Money: $0");
        backButton.setDisable(false);
        continueButton.setDisable(true);
    }

    public void onHourUpdate(int currentDistance, int carFuelAmount, int currentHour) {
        hoursLabel.setText("Hour: " + currentHour);
        fuelProgressBar.setProgress((double) carFuelAmount / currentCarCopy.getCarFuelEconomy());
        currentFuelLabel.setText("Current fuel: " + carFuelAmount);
        currentDistanceLabel.setText("Current distance: " + currentDistance + "km");
        distanceProgressBar.setProgress(currentDistance / (double) chosenRoute.getRouteDistance());
    }



    @FXML
    private void onContinueButtonClicked() {
        refuelButton.setDisable(true);
        raceEngine.playerClickedContinue();
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

        raceEngine = new RaceEngine(
                getGameManager().getSelectedRace(),
                chosenRoute,
                currentCarCopy,
                getGameManager().getDifficulty(),
                getGameManager().getMoney(),
                this
        );

        raceEngine.startRace();

    }





    /**
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

    @FXML
    private void onContinueButtonClicked(){
        raceEngine.resumeRaceFromUI();
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
                    int[] carFuelAndDistance = raceEngine.handleFuelStop(getGameManager().getCurrentCar());
                    fuelProgressBar.setProgress((double) carFuelAndDistance[0]/100);
                    distanceProgressBar.setProgress((double) carFuelAndDistance[1]/chosenRoute.getRouteDistance());
                }
            }

            //shows reason why player did not finish race
            @Override
            public void onRaceEnd(boolean finished, boolean outOfFuel, boolean outOfTime) {
                continueButton.setDisable(true);
                continueButton.setStyle("-fx-text-fill: red;");
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
    */
}
