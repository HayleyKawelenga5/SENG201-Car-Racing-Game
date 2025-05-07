package seng201.team0.gui;

import javafx.scene.control.*;

import seng201.team0.GameManager;

import seng201.team0.models.Race;
import seng201.team0.models.Route;
import seng201.team0.services.RaceService;
import seng201.team0.services.StartScreen;

import seng201.team0.models.Car;
import seng201.team0.models.Upgrade;

import javafx.fxml.FXML;

import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.List;

public class RaceScreenController extends ScreenController {

    @FXML private Button route1Button;
    @FXML private Button route2Button;
    @FXML private Button route3Button;
    @FXML private Button selectRouteButton;

    @FXML private Label routeDescriptionLabel;
    @FXML private Label routeDistanceLabel;
    @FXML private Label routeFuelStopsLabel;
    @FXML private Label routeDifficultyLabel;

    @FXML private Button startRaceButton;

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

    public RaceScreenController(GameManager manager) {
        super(manager);
    }

    @Override
    protected String getFxmlFile() {return "/fxml/race_screen.fxml";}

    @Override
    protected String getTitle() {return "Race Screen";}

    @FXML
    public void initialize() {
        Race selectedRace = getGameManager().getSelectedRace();
        availableRoutes = selectedRace.getRoutes();
        Car currentCar = getGameManager().getCurrentCar();
        //currentCar = raceService.applyMultipliers(currentCar, difficultyMultiplier); //MAY NEED TO CHANGE THIS METHOD TO ACCOUNT FOR MULTIPLIER BEING DOUBLE

        selectRouteButton.setOnAction(event -> onSelectRouteButtonClicked());
        //startRaceButton.setOnAction(event -> onStartRaceButtonClicked());

        List<Button> availableRouteButtons = List.of(route1Button, route2Button, route3Button);

        for (int i = 0; i < availableRouteButtons.size(); i++) {
            int index = i;
            availableRouteButtons.get(i).setOnAction(event -> onRouteButtonClicked(index));
        }

        carNameLabel.setText("Current car: " + currentCar.getCarName());
        carSpeedLabel.setText("Speed: " + currentCar.getCarSpeed());
        carHandlingLabel.setText("Handling: " + currentCar.getCarHandling());
        carReliabilityLabel.setText("Reliability: " + currentCar.getCarReliability());
        carFuelEconomyLabel.setText("Fuel Economy: " + currentCar.getCarFuelEconomy());

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
     /**

    */
     @FXML
     private void onRouteButtonClicked(int index) {
         if (index >= 0 && index < availableRoutes.size()) {
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
             routeDistanceLabel.setText("Distance: " + route. getRouteDistance() + "km");
             routeFuelStopsLabel.setText("Fuel Stops: " + route.getRouteFuelStops());
             routeDifficultyLabel.setText("Difficulty: " + route.getRouteDifficultyMultiplier());
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
