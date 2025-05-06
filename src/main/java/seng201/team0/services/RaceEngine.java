package seng201.team0.services;

import seng201.team0.models.Car;
import seng201.team0.models.Race;
import seng201.team0.models.Route;

import java.util.ArrayList;
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
    private CarService carService = new CarService();
    private String gameDifficulty;
    private List<Car> finishOrder = new ArrayList<>();

    private List<Car> opponents;
    private Map<Car, Integer> carDistances;

    public RaceEngine(Race race, Route selectedRoute, Car playerCar, List<Car> opponents) {
        this.race = race;
        this.selectedRoute = selectedRoute;
        this.playerCar = playerCar;

        if (gameDifficulty.equals("EASY")) {
            this.opponents = carService.generateRandomCars(3);
        } else if (gameDifficulty.equals("HARD")){
            this.opponents = carService.generateRandomCars(5);
        }
        this.carDistances = new HashMap<>();
        for (Car car : opponents) {
            carDistances.put(car, 0);
        }
        carDistances.put(playerCar, 0);
    }

    public void applyRouteMultipliers(){
        double multiplier = selectedRoute.getDifficultyMultiplier();
        for (Car car :  carDistances.keySet()) {
            car.setCarSpeed((int)(car.getCarSpeed()*multiplier));
            car.setCarHandling((int)(car.getCarHandling()*multiplier));
            car.setCarReliability((int)(car.getCarReliability()*multiplier));
            car.setCarFuelEconomy((int)(car.getCarFuelEconomy()*multiplier));
        }
    }

    public void consumeFuel(Car car){
        int fuelEconomy = car.getCarFuelEconomy();
        double consumptionAmount = 10 * (11-fuelEconomy)/10;
        int newFuel = (int) (car.getCarFuel() -  consumptionAmount);
        car.setCarFuel(newFuel);
        }

    public void startRace(){
        applyRouteMultipliers();

        while (raceTimeElapsed < race.getHours()) {
            raceTimeElapsed++;

            for (Car car : carDistances.keySet()) {
                if (car.getCarFuel() > 0){
                    int distanceTravelled = car.getCarSpeed();
                    carDistances.put(car, carDistances.get(car) + distanceTravelled);

                    //FUEL CONSUMPTION
                    consumeFuel(car);

                    if (carDistances.get(car) >= selectedRoute.getDistance()){
                        finishOrder.add(car);
                    }
                    //TRIGGER RANDOM EVENT?? SHOULD BE AFFECTED BY DIFFICULTY
                }
            }
            if (carDistances.get(playerCar)>= selectedRoute.getDistance() && !finishOrder.contains(playerCar)){
                playerFinished = true; //CHANGE THIS TO ADD THE CARS TO A LIST IN ORDER OF COMPLETION
            }
            if (playerCar.getCarFuel() <= 0 && !playerRefueled){
                playerOutOfFuel = true;
            }
        }
    }
}
