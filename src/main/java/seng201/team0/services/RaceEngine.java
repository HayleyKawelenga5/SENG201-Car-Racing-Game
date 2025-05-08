package seng201.team0.services;

import javafx.application.Platform;
import seng201.team0.models.Car;
import seng201.team0.models.Race;
import seng201.team0.models.Route;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class RaceEngine {
    private Race race;
    private Route selectedRoute;
    private Car playerCar;
    private int raceTimeElapsed = 0;
    private boolean playerFinished;
    private boolean playerOutOfFuel;
    private boolean playerOutOfTime;
    private boolean playerRefueled;
    private int playerDistanceCovered = 0;
    private CarService carService = new CarService();
    private String gameDifficulty;
    private List<Car> finishOrder = new ArrayList<>();
    private int numberOfFuelStops;
    private List<Integer> fuelStops = new ArrayList<>();
    private Set<Integer> triggeredStops = new HashSet<>(); //PREVENTS RETRIGGERING THE SAME STOP

    private List<Car> opponents;
    private Map<Car, Integer> carDistances;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private volatile boolean raceRunning = false;

    public interface RaceUpdateListener {
        void onProgressUpdate(int distance, int fuel, int timeElapsed);
        void onFuelStop(int stopIndex, int currentDistance);
        void onRaceEnd(boolean finished, boolean outOfFuel, boolean outOfTime);
    }

    public RaceEngine(Race race, Route selectedRoute, Car playerCar, String gameDifficulty) {
        this.race = race;
        this.selectedRoute = selectedRoute;
        this.playerCar = playerCar;
        this.gameDifficulty = gameDifficulty;


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
        double multiplier = selectedRoute.getRouteDifficultyMultiplier();
        for (Car car :  carDistances.keySet()) {
            car.setCarSpeed((int)(car.getCarSpeed()*multiplier));
            car.setCarHandling((int)(car.getCarHandling()*multiplier));
            car.setCarReliability((int)(car.getCarReliability()*multiplier));
            car.setCarFuelEconomy((int)(car.getCarFuelEconomy()*multiplier));
        }
    }

    public void consumeFuel(Car car){
        int fuelEconomy = car.getCarFuelEconomy();
        double baseConsumptionAmount = 100; //amount of fuel lost regardless of fuel economy
        int newFuel = (int) (car.getCarFuel() -  baseConsumptionAmount/fuelEconomy);
        car.setCarFuel(newFuel);
    }

    public void generateFuelStops(){
        numberOfFuelStops = selectedRoute.getRouteFuelStops();
        int raceDistance = selectedRoute.getRouteDistance();
        int fuelStopInterval = raceDistance/(numberOfFuelStops+1); //+1 so it doesnt generate stops at the start or end of the race

        for (int i = 0; i < numberOfFuelStops; i++) {
            fuelStops.add(i* fuelStopInterval); //generates fuel stops evenly spaced along route distance
        }
    }

    public List<Integer> getFuelStopDistances(Route selectedRoute) {
        List<Integer> fuelStopDistances = new ArrayList<>();
        int stopInterval = selectedRoute.getRouteDistance() / (selectedRoute.getRouteFuelStops() + 1);
        for (int i = 1; i <= selectedRoute.getRouteFuelStops(); i++) {
            fuelStopDistances.add(stopInterval * i);
        }
        return fuelStopDistances;
    }

    public void startRaceAsync(RaceUpdateListener listener){
        raceRunning = true;
        executorService.submit(() -> {
            applyRouteMultipliers();
            generateFuelStops();
            while (raceTimeElapsed < race.getRaceHours()) {
                raceTimeElapsed++;

                for (Car car : new ArrayList<>(carDistances.keySet())) {
                    if (car.getCarFuel() > 0){
                        int oldDistance = carDistances.get(car);
                        int distanceTravelled = car.getCarSpeed();
                        int newDistance = oldDistance + distanceTravelled;
                        carDistances.put(car, newDistance);

                        if (car == playerCar) {
                            for (int stop : fuelStops) {
                                if (!triggeredStops.contains(stop) && oldDistance < stop && newDistance >= stop) {
                                    //boolean choseToRefuel = propmt player UI logic to be implemented later
//                                    handleFuelStop(car); //??
//                                    triggeredStops.add(stop);
//                                    Platform.runLater(() -> listener.onFuelStop(stop, newDistance));

                                    int stopFinal = stop;
                                    Platform.runLater(() -> {
                                        listener.onFuelStop(stopFinal, newDistance); //calls the controller
                                    });
                                    triggeredStops.add(stop);
                                    break;
                                }
                            }
                            Platform.runLater(() -> listener.onProgressUpdate(newDistance, car.getCarFuel(), raceTimeElapsed));
                            triggerRandomEvent();
                        }
                        //FUEL CONSUMPTION
                        consumeFuel(car);
                        if (car.getCarFuel() <= 0) {
                            carDistances.remove(car); //effectively removes car from race
                            if (car == playerCar){
                                raceRunning = false;
                                Platform.runLater(()-> listener.onRaceEnd(false, true, false));
                            }
                            continue;
                        }
                        if (carDistances.get(car) >= selectedRoute.getRouteDistance()){
                            finishOrder.add(car);
                            if (car == playerCar){
                                raceRunning = false;
                                Platform.runLater(()-> listener.onRaceEnd(true, false, false));
                            }
                        }
                    }

                    //TRIGGER RANDOM EVENT?? PROBABILITY OF EVENT OCCURING SHOULD BE AFFECTED BY DIFFICULTY
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (!finishOrder.contains(playerCar)) {
                Platform.runLater(() -> listener.onRaceEnd(false, false, true));
            }
            executorService.shutdown();

        });
    }

    public void handleFuelStop(Car car){
        car.setCarFuel(car.getCarFuel() + 20); //MAYBE CHANGE THIS TO DEPEND ON DIFFICULTY?? THOUGHTS??
        carDistances.put(car, carDistances.get(car) - 20); //REDUCES PLAYER DISTANCE
    }

    public void triggerRandomEvent(){
        double chance = gameDifficulty.equals("HARD") ? 0.5 : 0.3; //50% chance of random event if hard 30% if easy
        if (Math.random()<chance){
            int eventType = new Random().nextInt(3);
            String alertText = "";
            switch(eventType){
                case 0:
                    alertText = handleBreakdownEvent();
                    break;
                case 1:
                    alertText = handleStrandedTraveller();
                    break;
                case 2:
                    alertText = handleSevereWeatherEvent();
                    raceRunning = false;
            }
        }
    }

    public String handleBreakdownEvent(){
        //show alert "Your car has broken down!"
        String alertText = "Your car has broken down!" ;
        int newDistance = carDistances.get(playerCar) - 20;
        carDistances.put(playerCar, newDistance); //REDUCES PLAYER DISTANCE
        //ADD CODE THAT REDUCES PLAYER MONEY
        return alertText;
    }

    public String handleStrandedTraveller() {
        //show
        String alertText = "You should help a traveller. Lost 30km but gained money!";
        int newDistance = carDistances.get(playerCar) - 20;
        carDistances.put(playerCar, newDistance);
        //ADD CODE THAT INCREASES PLAYER MONEY
        return alertText;
    }

    public String handleSevereWeatherEvent() {
        //show alert text on GUI
        String alertText = "Severe weather! All cars retire!";
        for (Car car : carDistances.keySet()) {
            carDistances.remove(car);
        }
        finishOrder.clear();
        return alertText;
    }

    public List<Car> getFinalPosition(){
        return finishOrder;
    }
    //RETURNS THE PLAYER'S CURRENT POSITION TO DISPLAY
    public  int getPlayerPosition() {
        List<Map.Entry<Car, Integer>> sortedPositions = carDistances.entrySet().stream()
                .sorted((a,b) -> b.getValue() - a.getValue())
                .collect(Collectors.toList());

        for (int i = 0; i < sortedPositions.size(); i++){
            if (sortedPositions.get(i).getKey().equals(playerCar)){
                return i + 1;
            }
        }
        return -1;
    }

}



