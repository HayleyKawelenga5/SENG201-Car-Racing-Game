package seng201.team0;

import seng201.team0.gui.ScreenNavigator;
import seng201.team0.models.Car;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private final ScreenNavigator navigator;

    public GameManager(ScreenNavigator navigator) {
        this.navigator = navigator;
        navigator.launchGameInitialiserScreen(this);
    }

    public void onSetupComplete(String playerName, int seasonLength, String difficulty, List<Car> selectedCars, int playerMoney) {
        navigator.launchMainScreen(this);
    }

    public void onQuitRequested(){
        System.exit(0);
    }
}
