package seng201.team0.services;

import seng201.team0.models.Car;
import seng201.team0.models.Race;
import seng201.team0.models.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RaceEngine {
    private Race race;
    private Route selectedRoute;
    private Car playerCar;
    private int raceTimeElapsed = 0;
    private boolean playerFinished;
    private boolean playerOutOfFuel;
    private boolean playerRefueled;
    private int playerDistanceCovered = 0;

    private List<Car> opponents;
    private Map<Car, Integer> carDistances;

    public RaceEngine(Race race, Route selectedRoute, Car playerCar, List<Car> opponents) {
        this.race = race;
        this.selectedRoute = selectedRoute;
        this.playerCar = playerCar;
        this.opponents = opponents;
        this.carDistances = new HashMap<>();
        for (Car car : opponents) {
            carDistances.put(car, 0);
        }
        carDistances.put(playerCar, 0);
    }

    public void startRace(){
        while (raceTimeElapsed < race.getHours()) {
            raceTimeElapsed++;
        }
    }
}
