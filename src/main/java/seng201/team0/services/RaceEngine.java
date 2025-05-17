package seng201.team0.services;

import javafx.application.Platform;
import seng201.team0.models.Car;
import seng201.team0.models.Race;
import seng201.team0.models.Route;

import seng201.team0.gui.RaceScreenController;

import java.util.*;

import java.util.Random;
import java.util.logging.Logger;

public class RaceEngine {

    private final Race race;
    private final Route selectedRoute;
    private final Car playerCar;
    private final String gameDifficulty;

    private final List<Car> opponents;
    private final Map<Car, Integer> carDistances;

    private final List<Integer> fuelStops = new ArrayList<>();
    private final Map<Car, Set<Integer>> triggeredFuelStops = new HashMap<>();
    private final Map<Car, Integer> racePenalties = new HashMap<>();

    private final List<Car> finishPositions = new ArrayList<>();
    private final List<Integer> playerFinishPositions = new ArrayList<>();
    private int playerTotalPrizeMoney;

    private final Map<Car, Integer> carHours = new HashMap<>();


    private final RaceScreenController raceScreenController;

    public enum RaceStatus {
        RUNNING, FINISHED, DNF
    }

    private final Map<Car, RaceStatus> carStatus = new HashMap<>();

    private static final Logger logger = Logger.getLogger(RaceEngine.class.getName());

    public RaceEngine(Race race, Route selectedRoute, Car playerCar, String difficulty, RaceScreenController raceScreenController) {
        this.race = race;
        this.selectedRoute = selectedRoute;
        this.playerCar = playerCar;
        this.gameDifficulty = difficulty;
        this.raceScreenController = raceScreenController;

        CarService carService = new CarService();
        this.opponents = carService.generateRandomCars(race.getRaceEntries());
        this.carDistances = new HashMap<>();

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
            switch (selectedRoute.getRouteDescription()){
                case FLAT:
                case BEACH:
                    car.setCarSpeed((int) (car.getCarSpeed() / difficultyMultiplier));
                    car.setCarHandling((int) (car.getCarHandling() / difficultyMultiplier));
                    car.setCarReliability((int) (car.getCarReliability() / difficultyMultiplier));
                    car.setCarFuelEconomy((int) (car.getCarFuelEconomy() / difficultyMultiplier));
                    break;
                case HILLY:
                    car.setCarSpeed((int) (car.getCarSpeed() / difficultyMultiplier));
                    car.setCarFuelEconomy((int) (car.getCarFuelEconomy() / difficultyMultiplier));
                    break;
                case OFFROAD:
                    car.setCarHandling((int) (car.getCarHandling() / difficultyMultiplier));
                    car.setCarReliability((int) (car.getCarReliability() / difficultyMultiplier));
                    break;
                case WINDY:
                    car.setCarSpeed((int) (car.getCarSpeed() / difficultyMultiplier));
                    car.setCarHandling((int) (car.getCarHandling() / difficultyMultiplier));
                    break;
            }
        }
    }

    public void consumeFuel(Car car) {
        car.setCarFuelAmount(car.getCarFuelAmount() - (car.getCarSpeed() / 2));
    }

    public void refuel(Car car) {
        car.setCarFuelAmount(car.getCarFuelEconomy());
        racePenalties.put(car, racePenalties.getOrDefault(car, 0) + 10);
    }

    public void breakdownContinue() {
        racePenalties.put(playerCar, racePenalties.getOrDefault(playerCar, 0) + 20);
        System.out.println("Breakdown continue penalty: " + 20);
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

        if (carDistances.get(car) >= selectedRoute.getRouteDistance()) {
            carStatus.put(car, RaceStatus.FINISHED);
            System.out.println("Opponent finished. Hours: " + carHours.get(car) + ". Status: " + carStatus.get(car) + ". Distance: " + carDistances.get(car));
            finishPositions.add(car);
            carHours.put(car, carHours.get(car) + 1);
            return true;
        }

        if (car.getCarFuelAmount() <= 0) {
            carStatus.put(car, RaceStatus.DNF);
            System.out.println("Opponent DNF. Hours: " + carHours.get(car) + ". Status: " + carStatus.get(car) + ". Distance: " + carDistances.get(car));
            return true;
        }

        return false;
    }

    public boolean updatePlayerCar() {

        int currentDistance = carDistances.get(playerCar);
        int nextDistance = currentDistance + playerCar.getCarSpeed();

        Set<Integer> triggeredStops = triggeredFuelStops.get(playerCar);

        for (int fuelStop : fuelStops) {
            if (!triggeredStops.contains(fuelStop) && fuelStop > currentDistance && fuelStop <= nextDistance) {
                triggeredStops.add(fuelStop);
                Platform.runLater(() ->
                        raceScreenController.onFuelStop(currentDistance, fuelStop, playerCar.getCarFuelAmount())
                );
                return true;
            }
        }

        consumeFuel(playerCar);
        carDistances.put(playerCar, nextDistance);

        Platform.runLater(() ->
                raceScreenController.onHourUpdate(nextDistance, playerCar.getCarFuelAmount(), carHours.get(playerCar))
        );

        if (carDistances.get(playerCar) >= selectedRoute.getRouteDistance()) {
            carStatus.put(playerCar, RaceStatus.FINISHED);
            finishPositions.add(playerCar);
            carHours.put(playerCar, carHours.get(playerCar) + 1);
            System.out.println("Player finished. Hours: " + carHours.get(playerCar) + ". Status: " + carStatus.get(playerCar) + ". Distance: " + carDistances.get(playerCar));
            playerFinished();
            return true;
        }

        if (playerCar.getCarFuelAmount() <= 0) {
            carStatus.put(playerCar, RaceStatus.DNF);
            playerDNF();
            return true;
        }

        triggerRandomEvent();
        triggerCarPerformanceIssue();

        Platform.runLater(() ->
                raceScreenController.onHourUpdate(nextDistance, playerCar.getCarFuelAmount(), carHours.get(playerCar))
        );

        return false;
    }

    private synchronized void waitForPlayerContinue() {
        try {
            wait();
        } catch (InterruptedException e) {
            logger.warning("Thread was interrupted while waiting: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void playerClickedContinue() {
        notifyAll();
    }

    public void triggerCarPerformanceIssue() {
        int value = new Random().nextInt(1, 3);
        switch (value) {
            case 1:
                triggerCarBreakdown();
                break;
            case 2:
                triggerCarMalfunction();
                break;
        }
    }

    public void triggerCarBreakdown() {
        Random random = new Random();
        if (random.nextInt(1, 101) > (playerCar.getCarReliability() + 20)) {
            Platform.runLater(raceScreenController::onPlayerBreakdown);
        }
    }

    public void triggerCarMalfunction() {
        Random random = new Random();
        if (random.nextInt(1, 101) > (playerCar.getCarHandling() + 20)) {
            System.out.println("Malfunction penalty: " + 10);
            racePenalties.put(playerCar, racePenalties.getOrDefault(playerCar, 0) + 10);
            Platform.runLater(raceScreenController::onPlayerMalfunction);
        }
    }

    public void triggerRandomEvent() {
        int chance = gameDifficulty.equals("HARD") ? 20 : 10;
        Random random = new Random();
        if (random.nextInt(1, 101) < chance) {
            int value = new Random().nextInt(1, 3);
            switch (value) {
                case 1:
                    handleHitchhiker();
                    break;
                case 2:
                    handleSevereWeatherEvent();
                    break;
            }
        }
    }

    public void handleHitchhiker() {
        String infoText = "Pick up a hitchhiker. This costs you time, but they pay you $50!";
        racePenalties.put(playerCar, racePenalties.getOrDefault(playerCar, 0) + 20);
        System.out.println("Hitchhiker penalty: 20");
        Platform.runLater(() -> raceScreenController.onHitchhikerEvent(infoText));
    }

    public void handleSevereWeatherEvent() {
        String alertText = "Severe weather! All cars forced to retire from the race!";
        carStatus.replaceAll((c, v) -> RaceStatus.DNF);
        Platform.runLater(() -> raceScreenController.onSevereWeatherEvent(alertText));
    }

    private void updateFinishPositions() {
        for (Car car : finishPositions) {
            int currentDistance = carDistances.getOrDefault(car, 0);
            int penalty = racePenalties.getOrDefault(car, 0);
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
                    + " | Penalty: " + racePenalties.getOrDefault(car, 0));
        }
    }

    public void playerDNF() {
        Platform.runLater(raceScreenController::onPlayerDNF);
    }

    public void playerBreakdown(){
        Platform.runLater(raceScreenController::onPlayerRetired);
    }

    private void playerOutOfTime() {
        Platform.runLater(raceScreenController::onPlayerOutOfTime);
    }

    private void playerFinished() {
        updateFinishPositions();

        int playerPosition = finishPositions.indexOf(playerCar) + 1;
        playerFinishPositions.add(playerPosition); //list to keep track of player positions throughout race
        int prizeMoney = calculatePrizeMoney(playerPosition);
        playerTotalPrizeMoney += prizeMoney;
        Platform.runLater(() -> raceScreenController.onPlayerFinished(playerPosition, prizeMoney));
    }

    public double getPlayerAveragePlacing() {
        if (playerFinishPositions.isEmpty()) {
            return 0; //player did not place in any races, could change this to display DNF
        }
        int playerAveragePlacing = 0;
        for (Integer playerFinishPosition : playerFinishPositions) {
            playerAveragePlacing += playerFinishPosition;
        }
        playerAveragePlacing /= playerFinishPositions.size();
        return playerAveragePlacing;
    }

    public int getPlayerTotalPrizeMoney() {
        return playerTotalPrizeMoney;
    }

    public boolean noCarsFunctioning(List<Car> cars) {
        for (Car car : cars){
            if (car.getCarSpeed() == 0 || car.getCarReliability() == 0 || car.getCarFuelEconomy()==0){
                return true;
            }
        }
        return false;
    }

    private int calculatePrizeMoney(int position) {
        int prizeMoney = race.getRacePrizeMoney();
        switch (position) {
            case 1:
                break; //full amount
            case 2:
                prizeMoney = (int) (prizeMoney * 0.8);
                break;
            case 3:
                prizeMoney = (int) (prizeMoney * 0.6);
                break;
            case 4:
                prizeMoney = (int) (prizeMoney * 0.4);
                break;
            case 5:
                prizeMoney = (int) (prizeMoney * 0.2);
                break;
            default:
                prizeMoney = 0;
                break;
        }
        return prizeMoney;
    }
}







