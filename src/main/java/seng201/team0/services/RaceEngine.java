package seng201.team0.services;

import javafx.application.Platform;
import seng201.team0.models.Car;
import seng201.team0.models.Race;
import seng201.team0.models.Route;

import seng201.team0.GameManager;

import seng201.team0.gui.RaceScreenController;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import java.util.Random;

public class RaceEngine {

    private Race race;
    private Route selectedRoute;
    private Car playerCar;
    private String gameDifficulty;
    private int playerMoney;

    private List<Car> opponents;
    private Map<Car, Integer> carDistances;

    private List<Integer> fuelStops = new ArrayList<>();
    private Map<Car, Set<Integer>> triggeredFuelStops = new HashMap<>();
    private Map<Car, Integer> refuelPenalties = new HashMap<>();

    private List<Car> finishPositions = new ArrayList<>();

    private Map<Car, Integer> carHours = new HashMap<>();

    private CarService carService = new CarService();

    private int currentHour;

    private RaceScreenController raceScreenController;

    public enum RaceStatus {
        RUNNING, FINISHED, DNF
    }

    private Map<Car, RaceStatus> carStatus = new HashMap<>();

    public RaceEngine(Race race, Route selectedRoute, Car playerCar, String difficulty, int playerMoney, RaceScreenController raceScreenController) {
        this.race = race;
        this.selectedRoute = selectedRoute;
        this.playerCar = playerCar;
        this.gameDifficulty = difficulty;
        this.playerMoney = playerMoney;
        this.raceScreenController = raceScreenController;

        this.opponents = carService.generateRandomCars(race.getRaceEntries());
        this.carDistances = new HashMap<>();
        this.fuelStops = new ArrayList<>();

        for (Car car : opponents) {
            carDistances.put(car, 0);
            carHours.put(car, 0);
            carStatus.put(car, RaceStatus.RUNNING);
            triggeredFuelStops.put(car, new HashSet<>());
        }
        carDistances.put(playerCar, 0);
        carHours.put(playerCar, 0);
        carStatus.put(playerCar, RaceStatus.RUNNING);
        triggeredFuelStops.put(playerCar, new HashSet<>());

    }

    public void applyRouteMultipliers() {
        double difficultyMultiplier = selectedRoute.getRouteDifficultyMultiplier();
        for (Car car : carDistances.keySet()) {
            car.setCarSpeed((int) (car.getCarSpeed() / difficultyMultiplier));
            car.setCarHandling((int) (car.getCarHandling() / difficultyMultiplier));
            car.setCarReliability((int) (car.getCarReliability() / difficultyMultiplier));
            car.setCarFuelEconomy((int) (car.getCarFuelEconomy() / difficultyMultiplier));
        }
    }

    public void consumeFuel(Car car) {
        car.setCarFuelAmount(car.getCarFuelAmount() - (car.getCarSpeed() / 2));
    }

    public void refuel(Car car) {
        car.setCarFuelAmount(car.getCarFuelEconomy());
        refuelPenalties.put(car, refuelPenalties.getOrDefault(car, 0) + 10);
    }

    public void generateFuelStops() {
        int routeFuelStops = selectedRoute.getRouteFuelStops();
        int routeDistance = selectedRoute.getRouteDistance();
        int fuelStopDistance = routeDistance / (routeFuelStops + 1);
        for (int i = 0; i < routeFuelStops; i++) {
            fuelStops.add((i + 1) * fuelStopDistance);
        }
    }

    public void startRace() {
        new Thread(() -> {
            currentHour = 0;
            applyRouteMultipliers();
            generateFuelStops();
            playerCar.setCarFuelAmount(playerCar.getCarFuelEconomy());

            for (Car car : opponents) {
                while (carHours.get(car) < race.getRaceHours() && carStatus.get(car).equals(RaceStatus.RUNNING)) {
                    boolean atFuelStop = updateOpponentCar(car);
                    if (!atFuelStop) {
                        carHours.put(car, carHours.get(car) + 1);
                    }
                }
            }

            while (carHours.get(playerCar) <= race.getRaceHours() && carStatus.get(playerCar).equals(RaceStatus.RUNNING)) {
                boolean atFuelStop = updatePlayerCar();
                if (!atFuelStop) {
                    carHours.put(playerCar, carHours.get(playerCar) + 1);
                }
                waitForPlayerContinue();
            }
            if (carHours.get(playerCar) > race.getRaceHours()) {
                playerOutOfTime();
            }

        }).start();
    }



    private boolean updateOpponentCar(Car car) {

        int currentDistance = carDistances.get(car);
        int nextDistance = currentDistance + car.getCarSpeed();

        Set<Integer> triggeredStops = triggeredFuelStops.get(car);

        for (int fuelStop : fuelStops) {
            if (!triggeredStops.contains(fuelStop) && fuelStop > currentDistance && fuelStop <= nextDistance) {
                triggeredStops.add(fuelStop);
                Random random = new Random();
                if (random.nextInt(1, 3) == 1) {
                    refuel(car);
                }
                return true;
            }
        }

        consumeFuel(car);
        carDistances.put(car, nextDistance);

        if (car.getCarFuelAmount() <= 0) {
            carStatus.put(car, RaceStatus.DNF);
            System.out.println("Opponent DNF. Hours: " + carHours.get(car) + ". Status: " + carStatus.get(car) + ". Distance: " + carDistances.get(car));
            return true;
        }

        if (carDistances.get(car) >= selectedRoute.getRouteDistance()) {
            carStatus.put(car, RaceStatus.FINISHED);
            System.out.println("Opponent finished. Hours: " + carHours.get(car) + ". Status: " + carStatus.get(car) + ". Distance: " + carDistances.get(car));
            finishPositions.add(car);
            carHours.put(car, carHours.get(car) + 1);
            return true;
        }

        return false;
    }

    private boolean updatePlayerCar() {

        int currentDistance = carDistances.get(playerCar);
        int nextDistance = currentDistance + playerCar.getCarSpeed();

        Set<Integer> triggeredStops = triggeredFuelStops.get(playerCar);

        for (int fuelStop : fuelStops) {
            if (!triggeredStops.contains(fuelStop) && fuelStop > currentDistance && fuelStop <= nextDistance) {
                triggeredStops.add(fuelStop);
                Platform.runLater(() ->
                        raceScreenController.onFuelStop(currentDistance, fuelStop, playerCar.getCarFuelAmount())
                );
                if (playerCar.getCarFuelAmount() <= 0) {
                    carStatus.put(playerCar, RaceStatus.DNF);
                    playerDNF();
                    return false;
                }
                return true;
            }
        }

        consumeFuel(playerCar);
        carDistances.put(playerCar, nextDistance);
        triggerRandomEvent();

        Platform.runLater(() ->
                raceScreenController.onHourUpdate(nextDistance, playerCar.getCarFuelAmount(), carHours.get(playerCar))
        );

        if (playerCar.getCarFuelAmount() <= 0) {
            carStatus.put(playerCar, RaceStatus.DNF);
            playerDNF();
            return true;
        }

        if (carDistances.get(playerCar) >= selectedRoute.getRouteDistance()) {
            carStatus.put(playerCar, RaceStatus.FINISHED);
            finishPositions.add(playerCar);
            carHours.put(playerCar, carHours.get(playerCar) + 1);
            System.out.println("Player finished. Hours: " + carHours.get(playerCar) + ". Status: " + carStatus.get(playerCar) + ". Distance: " + carDistances.get(playerCar));
            playerFinished();
            return true;
        }

        return false;
    }

    private synchronized void waitForPlayerContinue() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void playerClickedContinue() {
        notifyAll();
    }

    public void triggerRandomEvent() {
        double chance = gameDifficulty.equals("HARD") ? 10 : 5;
        Random random = new Random();
        if (random.nextInt(1, 101) < chance) {
            int eventType = new Random().nextInt(3);
            String alertText = "";
            switch(eventType) {
                case 0:
                    alertText = handleBreakdownEvent();
                    break;
                case 1:
                    alertText = handleStrandedTraveller();
                    break;
                case 2:
                    alertText = handleSevereWeatherEvent();
                    break;
            }
        }
    }

    public String handleBreakdownEvent() {
        String alertText = "Your car has broken down!";
        int newDistance = carDistances.get(playerCar) - 20;
        carDistances.put(playerCar, newDistance);
        playerMoney -= 50;
        return alertText;
    }

    public String handleStrandedTraveller() {
        String alertText = "You help a traveller. This costs you 20km but you receive $100!";
        int newDistance = carDistances.get(playerCar) - 20;
        carDistances.put(playerCar, newDistance);
        playerMoney += 50;
        return alertText;
    }

    public String handleSevereWeatherEvent() {
        String alertText = "Severe weather! All cars retire!";
        for (Car car : carDistances.keySet()) {
            carStatus.put(car, RaceStatus.DNF);
        }
        return alertText;
    }

    private void updateFinishPositions() {
        for (Car car : finishPositions) {
            int currentDistance = carDistances.getOrDefault(car, 0);
            int penalty = refuelPenalties.getOrDefault(car, 0);
            carDistances.put(car, currentDistance - penalty);
        }
        finishPositions.sort((car1, car2) -> {
            int hourComparison = Integer.compare(carHours.get(car1), carHours.get(car2));
            if (hourComparison != 0) {
                return hourComparison;
            }
            return Integer.compare(carDistances.get(car2), carDistances.get(car1));
        });
        System.out.println("Finish Positions:");
        for (int i = 0; i < finishPositions.size(); i++) {
            Car car = finishPositions.get(i);
            System.out.println((i + 1) + ". " + car.getCarName()
                    + " | Hours: " + carHours.get(car)
                    + " | Final Distance: " + carDistances.get(car)
                    + " | Penalty: " + refuelPenalties.getOrDefault(car, 0));
        }
    }

    private void playerDNF() {
        Platform.runLater(() -> {
            raceScreenController.onPlayerDNF();
        });
    }

    private void playerOutOfTime() {
        Platform.runLater(() -> {
            raceScreenController.onPlayerOutOfTime();
        });
    }

    private void playerFinished() {
        updateFinishPositions();

        int playerPosition = finishPositions.indexOf(playerCar) + 1;

        int prizeMoney = calculatePrizeMoney(playerPosition);

        Platform.runLater(() -> {
            raceScreenController.onPlayerFinished(playerPosition, prizeMoney);
        });
    }

    private int calculatePrizeMoney(int position) {
        int prizeMoney = race.getRacePrizeMoney();
        switch (position) {
            case 1: prizeMoney = prizeMoney; ; break;
            case 2: prizeMoney *= .8; break;
            case 3: prizeMoney *= .6; break;
            case 4: prizeMoney *= .4; break;
            case 5: prizeMoney *= .2; break;
            default: prizeMoney = 0; break;
        }
        return prizeMoney;
    }





    /**
    private Race race;
    private Route selectedRoute;
    private Car playerCar;
    private int playerMoney;
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

    private final Object pauseLock = new Object();
    private boolean waitForUser = false;

    public interface RaceUpdateListener {
        void onProgressUpdate(int distance, int fuel, int timeElapsed);
        void onFuelStop(int stopIndex, int currentDistance);
        void onRaceEnd(boolean finished, boolean outOfFuel, boolean outOfTime);
    }

    public RaceEngine(Race race, Route selectedRoute, Car playerCar, String gameDifficulty, int playerMoney) {
        this.race = race;
        this.selectedRoute = selectedRoute;
        this.playerCar = playerCar;
        this.gameDifficulty = gameDifficulty;
        this.playerMoney = playerMoney;

        this.opponents = carService.generateRandomCars(race.getRaceEntries());

        this.carDistances = new HashMap<>();
        for (Car car : opponents) {
            carDistances.put(car, 0);
        }
        carDistances.put(playerCar, 0);
    }

    public void applyRouteMultipliers(){
        double multiplier = selectedRoute.getRouteDifficultyMultiplier();
        for (Car car :  carDistances.keySet()) {
            car.setCarSpeed((int)(car.getCarSpeed() / multiplier));
            car.setCarHandling((int)(car.getCarHandling() / multiplier));
            car.setCarReliability((int)(car.getCarReliability() / multiplier));
            car.setCarFuelEconomy((int)(car.getCarFuelEconomy() / multiplier));
        }
    }

    public void consumeFuel(Car car) {
        int carFuelEconomy = car.getCarFuelEconomy();
        int carSpeed = car.getCarSpeed();
        int newFuel = car.getCarFuelAmount() -  (carSpeed/carFuelEconomy)*5;
        System.out.println(newFuel);
        car.setCarFuelAmount(newFuel);
    }

    public void generateFuelStops() {
        int numberOfFuelStops = selectedRoute.getRouteFuelStops();
        int raceDistance = selectedRoute.getRouteDistance();
        int fuelStopInterval = raceDistance / (numberOfFuelStops + 1); //+1 so it doesnt generate stops at the start or end of the race

        for (int i = 0; i < numberOfFuelStops; i++) {
            fuelStops.add(i * fuelStopInterval); //generates fuel stops evenly spaced along route distance
        }
    }

    public void startRaceAsync(RaceUpdateListener listener){
        raceRunning = true;
        executorService.submit(() -> {
            applyRouteMultipliers();
            generateFuelStops();
            while (raceTimeElapsed < race.getRaceHours()) {


                for (Car car : new ArrayList<>(carDistances.keySet())) {
                    if (car.getCarFuelAmount() > 0){
                        int oldDistance = carDistances.get(car);
                        int distanceTravelled = car.getCarSpeed();
                        int newDistance = oldDistance + distanceTravelled;
                        carDistances.put(car, newDistance);

                        if (car == playerCar) {
                            for (int stop : fuelStops) {
                                if (!triggeredStops.contains(stop) && oldDistance < stop && newDistance >= stop) {


                                    int stopFinal = stop;
                                    Platform.runLater(() -> {
                                        listener.onFuelStop(stopFinal, newDistance); //calls the controller
                                    });
                                    triggeredStops.add(stop);
                                    break;
                                }
                            }
                            raceTimeElapsed++;
                            //FUEL CONSUMPTION
                            consumeFuel(car);

                            //pause game each hour to show progress
                            synchronized (pauseLock) {
                                waitForUser = true;
                            }
                            Platform.runLater(() -> listener.onProgressUpdate(newDistance, car.getCarFuelAmount(), raceTimeElapsed));
                            pauseUntilUserContinues(); //wait until UI tells race to resume


                            triggerRandomEvent();
                        }

                        if (car.getCarFuelAmount() <= 0) {
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

    private void pauseUntilUserContinues() {
        synchronized (pauseLock) {
            while (waitForUser) {
                try {
                    pauseLock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void resumeRaceFromUI() {
        synchronized (pauseLock) {
            waitForUser = false;
            pauseLock.notifyAll();
        }
    }

    public int[] handleFuelStop(Car car){
        car.setCarFuelAmount(car.getCarFuelAmount() + 20); //MAYBE CHANGE THIS TO DEPEND ON DIFFICULTY?? THOUGHTS??
        carDistances.put(car, carDistances.get(car) - 20);//REDUCES PLAYER DISTANCE
        return new int[] {car.getCarFuelAmount(), carDistances.get(car)};
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
        playerMoney -= 50;
        return alertText;
    }

    public String handleStrandedTraveller() {
        //show
        String alertText = "You should help a traveller. Lost 30km but gained money!";
        int newDistance = carDistances.get(playerCar) - 20;
        carDistances.put(playerCar, newDistance);
        playerMoney += 50;
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
     */

}



