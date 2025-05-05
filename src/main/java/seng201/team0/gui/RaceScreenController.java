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
    private Button refuelButton;
    private Button handleEventButton;
    private Button startRaceButton;

    private RaceService raceService = new RaceService();

    public RaceScreenController(GameManager manager) {
        super(manager);
    }

    @Override
    protected String getFxmlFile() {return "/fxml/race_screen.fxml";}

    @Override
    protected String getTitle() {return "Race Screen";}

    public void initialize() {
        Route selectedRoute = getGameManager().getSelectedRoute();
        Race selectedRace = getGameManager().getSelectedRace();
        Car currentCar = getGameManager().getCurrentCar();
        double difficultyMultiplier = selectedRoute.getDifficultyMultiplier();
        currentCar = raceService.applyMultipliers(currentCar, difficultyMultiplier); //MAY NEED TO CHANGE THIS METHOD TO ACCOUNT FOR MULTIPLIER BEING DOUBLE
    }
}
