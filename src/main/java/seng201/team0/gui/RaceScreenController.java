package seng201.team0.gui;

import javafx.scene.control.*;

import seng201.team0.GameManager;
import seng201.team0.models.Race;
import seng201.team0.models.Route;
import seng201.team0.services.RaceEngine;
import seng201.team0.services.RaceService;
import seng201.team0.models.Car;

import javafx.fxml.FXML;
import seng201.team0.utils.ScreenUpdater;

import java.util.Optional;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for the Race Screen GUI.
 *
 * This screen allows the user to select a race route based on its stats, start the race and make decision in the race
 * including refuelling and handling random events. Player race position, amount of prize money won or reason for not
 * finishing race are displayed on this screen.
 *
 * The controller handles user interactions during race setup and simulation and update GUI elements accordingly
 */
public class RaceScreenController extends ScreenController implements ScreenControllerInterface {

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
    @FXML private Label carUpgradesLabel;

    @FXML private ProgressBar distanceProgressBar;
    @FXML private ProgressBar fuelProgressBar;

    @FXML private Label hoursLabel;

    @FXML private Button refuelButton;
    @FXML private Button continueButton;

    @FXML private Label positionLabel;
    @FXML private Label prizeMoneyLabel;

    @FXML private Button backButton;

    /**
     * Service responsible for handling race-related logic and operations.
     */
    private final RaceService raceService = new RaceService();

    /**
     * The route chosen by the player for the current race.
     */
    private Route chosenRoute;

    /**
     * The route currently selected.
     */
    private Route selectedRoute;

    /**
     * List of routes available for selection in the current race context.
     */
    private List<Route> availableRoutes;

    /**
     * A copy of the current player's car used during race simulation to preserve original state.
     */
    private Car currentCarCopy;

    /**
     * The current player's car participating in the race.
     */
    private Car currentCar;

    /**
     * The engine managing the race simulation logic.
     */
    private RaceEngine raceEngine;

    /**
     * The total prize money available for the current race.
     */
    private int prizeMoney;

    /**
     * List of cars owned by the player.
     */
    private List<Car> playerCars = new ArrayList<>();

    /**
     * Constructs a RaceScreenController with the given {@link GameManager}
     * @param manager The GameManager managing game state. (GameManager)
     */
    public RaceScreenController(GameManager manager) {
        super(manager);
    }

    /**
     * Returns the path to the FXML file for this screen.
     * @return Path to FXML file.
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/race_screen.fxml";
    }

    /**
     * Returns the window title for this screen.
     * @return Title of the race screen.
     */
    @Override
    protected String getTitle() {
        return "Race Screen";
    }

    /**
     * Initializes the controller, sets up button handlers and populates GUI with initial data.
     */
    @FXML
    public void initialize() {
        Race selectedRace = getGameManager().getSelectedRace();
        availableRoutes = selectedRace.getAvailableRoutes();

        playerCars = getGameManager().getPlayerCars();

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
        continueButton.setDisable(true);
        refuelButton.setDisable(true);

        currentCar = getGameManager().getCurrentCar();
        carNameLabel.setText("Current car: " + currentCar.getCarName());
        ScreenUpdater.updateCarStats(currentCar, carSpeedLabel, carHandlingLabel, carReliabilityLabel, carFuelEconomyLabel, carUpgradesLabel);
        currentCarCopy = raceService.copyCar(currentCar);
        ScreenUpdater.updateRouteButtons(availableRouteButtons, availableRoutes);
    }

    /**
     * Handles the action of confirming a selected route for the race.
     */
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

    /**
     * Handles user clicking one of the route option buttons and displays player car stats showing how the selected
     * route affects certain attributes of their car.
     *
     * @param index The index of the selected route. (int)
     */
    @FXML
    private void onRouteButtonClicked(int index) {
        if (index >= 0 && index < availableRoutes.size()) {
            Car previewCurrentCar = raceService.previewMultiplier(currentCarCopy, availableRoutes.get(index).getRouteDifficultyMultiplier());
            ScreenUpdater.updateCarStats(previewCurrentCar, carSpeedLabel, carHandlingLabel, carReliabilityLabel, carFuelEconomyLabel, carUpgradesLabel);
            selectRouteButton.setStyle("");
            selectedRoute = availableRoutes.get(index);
            ScreenUpdater.updateRouteStats(selectedRoute, routeDescriptionLabel, routeDistanceLabel, routeFuelStopsLabel, routeDifficultyLabel);
        }
    }

    /**
     * Handles refueling during the race and update GUI elements (progress bar, label) accordingly.
     */
    @FXML
    private void onRefuelButtonClicked() {
        raceEngine.refuel(currentCarCopy);
        currentFuelLabel.setText("Current fuel: " + currentCarCopy.getCarFuelAmount());
        fuelProgressBar.setProgress((double) currentCarCopy.getCarFuelAmount() / currentCarCopy.getCarFuelEconomy());
        refuelButton.setDisable(true);
    }


    /**
     * Called when the player reaches a fuel stop during the race
     * @param currentDistance the player's current distance at the time they reach the fuel stop (int)
     * @param fuelStopDistance the distance point of the current fuel stop (int)
     * @param fuelAmount the amount of fuel in the player's car (int)
     */
    public void onFuelStop(int currentDistance, int fuelStopDistance, int fuelAmount) {
        currentDistanceLabel.setText("Current distance: " + fuelStopDistance + "km");
        currentFuelLabel.setText("Current fuel: " + Math.max(0, (fuelAmount - ((fuelStopDistance - currentDistance) / 2))));
        distanceProgressBar.setProgress(fuelStopDistance / (double) chosenRoute.getRouteDistance());
        fuelProgressBar.setProgress(Math.max(0, (double) (fuelAmount - ((fuelStopDistance - currentDistance) / 2)) / currentCarCopy.getCarFuelEconomy()));
        showInfo("Fuel Stop", "You are at a fuel stop!");
        refuelButton.setDisable(false);
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
     * Shows an information dialog with the specified title and message.
     * @param title the title of the information dialog (String)
     * @param message the content message of the information dialog (String)
     */
    private void showInfo(String title, String message) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle(title);
        info.setHeaderText(null);
        info.setContentText(message);
        info.showAndWait();
    }

    /**
     * Handles going back to the main screen after a race ends given the player has competed in all races or the player
     * does not have a functioning car and does not have enough money to repair it or buy a new one.
     * Car performance reduced at the end of each race to encourage player to buy upgrades/new cars.
     */
    @FXML
    private void onBackButtonClicked() {
        int racesRemaining = getGameManager().getRacesRemaining();
        if (racesRemaining <= 1) {
            getGameManager().toFinishScreen(raceEngine.getPlayerAveragePlacing(), raceEngine.getPlayerTotalPrizeMoney());
            return;
        }
        if (raceEngine.noCarsFunctioning(playerCars) && getGameManager().getMoney() <= 0){
            showAlert("No cars functioning", "You have no functioning cars.");
            getGameManager().toFinishScreen(raceEngine.getPlayerAveragePlacing(), raceEngine.getPlayerTotalPrizeMoney());
            return;
        }

        int totalPlayerMoney = getGameManager().getMoney() + prizeMoney;
        getGameManager().setMoney(getGameManager().getMoney() + prizeMoney);
        getGameManager().setRacesRemaining(getGameManager().getRacesRemaining() - 1);

        raceEngine.reduceCurrentCarStats(currentCar);

        getGameManager().setCurrentCar(currentCar);
        getGameManager().toMainScreenFromRace(totalPlayerMoney, currentCar, getGameManager().getSeasonLength());
    }

    /**
     * Called when player finishes the race successfully.
     * @param playerPosition the player's final position at the end of the race. (int)
     * @param prizeMoney the amount of money the player won based on their position in the race. (int)
     */
    public void onPlayerFinished(int playerPosition, int prizeMoney) {
        this.prizeMoney = prizeMoney;
        handleRaceEnd("Place: " + playerPosition, "Prize Money: $" + prizeMoney, "Race finished", "Position: " + playerPosition + " | Prize Money: $" + prizeMoney);
    }

    /**
     * Called if the player runs out of fuel and cannot finish the race.
     */
    public void onPlayerDNF() {
        handleRaceEnd("Place: DNF. Out of fuel!", "Prize Money: $0", "Out of fuel!", "Position: DNF | Prize money: $0");
    }

    /**
     * Called if the player retires from the race after a breakdown event.
     */
    public void onPlayerRetired(){
        handleRaceEnd("Place: DNF. Breakdown!","Prize Money: $0","Retired!", "Car retired from race!\nTip: Upgrade car reliability");
    }
    /**
     * Called if the player runs out of time to finish the race.
     */
    public void onPlayerOutOfTime() {
        handleRaceEnd("Place: DNF. Out of time!", "Prize Money: $0", "Out of time!", "Position: DNF | Prize money: $0");
    }

    /**
     * Called when the player's car breaks down and the player is given the option to retire from the race or pay for
     * repairs and lose race time and money.
     */
    public void onPlayerBreakdown() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        showAlert("Breakdown!", "Retire from race or continue?\nClicking proceed costs you time and money!");
        ButtonType retiredButton = new ButtonType("Retire", ButtonBar.ButtonData.YES);
        ButtonType continuedButton = new ButtonType("Continue", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(retiredButton, continuedButton);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == retiredButton) {
                raceEngine.playerRetired();
            } else {
                showInfo("Continued!", "You continued the race at a cost!\nTip: Upgrade car reliability");
                getGameManager().setMoney(getGameManager().getMoney() - 50);
                raceEngine.breakdownContinue();
                raceEngine.updatePlayerCar();
            }
        }
    }

    /**
     * Called if player car malfunctions due to poor handling. Implementation of a random event.
     */
    public void onPlayerMalfunction() {
        showInfo("Malfunction!", "Challenging route conditions are impacting car performance.\nTip: Upgrade car handling");
    }

    /**
     * Called when a severe weather event causes race termination
     * @param alertText message indicating to the user that a random event has occurred including details of the event. (string)
     */
    public void onSevereWeatherEvent(String alertText) {
        handleRaceEnd("Place: DNF. Severe Weather!", "Prize Money: $0", "Severe Weather", alertText);
    }

    /**
     * Called when a player picks up a hitchhiker
     * @param infoText message indicating to the user that a random event has occurred including details of the event. (String)
     */
    public void onHitchhikerEvent(String infoText) {
        showInfo("Hitchhiker", infoText);
        getGameManager().setMoney(getGameManager().getMoney() + 50);
    }

    /**
     * Handles the common logic to display race results at the end of the race.
     * @param positionText tells the user what position they came in the race (String)
     * @param prizeMoneyText tells the user the amount of prize money won or the reason they didn't finish the race (String)
     * @param alertTitle title of the dialog box shown to the user. (String)
     * @param alertMessage message displayed to the user (String)
     */
    private void handleRaceEnd(String positionText, String prizeMoneyText, String alertTitle, String alertMessage) {
        showInfo(alertTitle, alertMessage);
        positionLabel.setText(positionText);
        prizeMoneyLabel.setText(prizeMoneyText);
        backButton.setDisable(false);
        continueButton.setDisable(true);
    }

    /**
     * Called once every hour of simulated race time to update the GUI.
     * @param currentDistance the distance the player has travelled at the given simulated hour. (int)
     * @param carFuelAmount the amount of fuel in the player's car. (int)
     * @param currentHour the amount of simulated hours that have passed in the race. (int)
     */
    public void onHourUpdate(int currentDistance, int carFuelAmount, int currentHour) {
        hoursLabel.setText("Hour: " + currentHour);
        fuelProgressBar.setProgress(Math.max(0, (double) carFuelAmount / currentCarCopy.getCarFuelEconomy()));
        currentFuelLabel.setText("Current fuel: " + Math.max(0, carFuelAmount));
        currentDistanceLabel.setText("Current distance: " + currentDistance + "km");
        distanceProgressBar.setProgress(currentDistance / (double) chosenRoute.getRouteDistance());
    }

    /**
     * Handles the player clicking continue on the race screen.
     */
    @FXML
    private void onContinueButtonClicked() {
        refuelButton.setDisable(true);
        raceEngine.playerClickedContinue();
    }

    /**
     * Starts the race simulation after a route has been selected
     * Removes the route info (labels and buttons) from the screen
     */
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
                getGameManager(),
                getGameManager().getSelectedRace(),
                chosenRoute,
                currentCarCopy,
                getGameManager().getDifficulty(),
                this
        );
        raceEngine.startRace();
    }
}
