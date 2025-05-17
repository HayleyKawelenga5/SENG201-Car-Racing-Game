package seng201.team0.services;

import javafx.application.Platform;
import seng201.team0.models.Car;
import seng201.team0.models.Race;
import seng201.team0.models.Route;

import seng201.team0.gui.RaceScreenController;

import java.util.*;

import java.util.Random;
import java.util.logging.Logger;

/**
 * The RaceEngine class handles the simulation and logic of the car race event, including tracking progress of the
 * player and opponent cars, fuel consumption, random events, penalties, race status and updating GUI via the controller
 */
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
    private static final Logger logger = Logger.getLogger(RaceEngine.class.getName());

    /**
     * Enum to represent the current status of a car in the race. Running if the player is still in the race, finished
     * if the player has finished the race and DNF if the player was not able to complete the race.
     */
    public enum RaceStatus {
        RUNNING, FINISHED, DNF
    }

    private final Map<Car, RaceStatus> carStatus = new HashMap<>();

    /**
     * Constructs a RaceEngine to handle the race logic and execution.
     *
     * @param race                 The Race object representing the race event.
     * @param selectedRoute        The Route object representing the route for the race.
     * @param playerCar            The player's Car object.
     * @param difficulty           Game difficulty.
     * @param raceScreenController UI controller to update race progress.
     */
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

    /**
     * Applies route-specific multipliers to all participating cars. The route affects a car's stats based on its properties
     * in the form of multipliers applied to the car's stats.
     */
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

    /**
     * Reduces the fuel level of a given car based on its speed.
     *
     * @param car The car whose fuel is being consumed.
     */
    public void consumeFuel(Car car) {
        car.setCarFuelAmount(car.getCarFuelAmount() - (car.getCarSpeed() / 2));
    }

    /**
     * Refuels the car and applies a time penalty for refueling.
     *
     * @param car The car being refueled.
     */
    public void refuel(Car car) {
        car.setCarFuelAmount(car.getCarFuelEconomy());
        racePenalties.put(car, racePenalties.getOrDefault(car, 0) + 10);
    }

    /**
     * Applies a breakdown penalty if the player continues after a breakdown.
     */
    public void breakdownContinue() {
        racePenalties.put(playerCar, racePenalties.getOrDefault(playerCar, 0) + 20);
    }

    /**
     * Calculates and sets fuel stop positions based on route distance and fuel stop count. These stops are spaced evenly
     * along the route.
     */
    public void generateFuelStops() {
        int routeFuelStops = selectedRoute.getRouteFuelStops();
        int routeDistance = selectedRoute.getRouteDistance();
        int fuelStopDistance = routeDistance / (routeFuelStops + 1);
        for (int i = 0; i < routeFuelStops; i++) {
            fuelStops.add((i + 1) * fuelStopDistance);
        }
    }

    /**
     * Starts the race in a separate thread, updating cars each hour and running until all cars finish, the player runs
     * out of time or the player is out of fuel.
     * Updates the GUI via the raceScreenController during the race.
     */
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


    /**
     * Updates the status of an opponent car for one hour of racing, including movement, fuel stops and finish or DNF
     * conditions.
     *
     * @param car The opponent car to update.
     * @return true if the car is at a fuel stop or finished/DNF this hour; false otherwise.
     */
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
            finishPositions.add(car);
            carHours.put(car, carHours.get(car) + 1);
            return true;
        }

        if (car.getCarFuelAmount() <= 0) {
            carStatus.put(car, RaceStatus.DNF);
            return true;
        }

        return false;
    }

    /**
     * Updates the player's car, handling fuel stops, events, and distance.
     * Handles triggering of random events
     *
     * @return true if at a fuel stop or finished/DNF this hour; false otherwise.
     */
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

    /**
     * Blocks until the player clicks continue to proceed to the next race hour.
     */
    private synchronized void waitForPlayerContinue() {
        try {
            wait();
        }  catch (InterruptedException e) {
            logger.warning("Thread was interrupted while waiting: " + e.getMessage());
            Thread.currentThread().interrupt();
        }

    }

    /**
     * Called by UI when player clicks continue during a race to notify the race thread that the player has clicked continue,
     * allowing the race simulation to proceed.
     */
    public synchronized void playerClickedContinue() {
        notifyAll();
    }

    /**
     * Randomly triggers a car performance-related issue (breakdown or malfunction - both are random events)
     */
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

    /**
     * Random chance of triggering a car breakdown based on the reliability of the player's car. The higher the reliability,
     * the less likely the car will be to breakdown during the race.
     */
    public void triggerCarBreakdown() {
        Random random = new Random();
        if (random.nextInt(1, 101) > (playerCar.getCarReliability() + 20)) {
            Platform.runLater(raceScreenController::onPlayerBreakdown);
        }
    }

    /**
     * Random chance of triggering a car malfunction based on handling.
     */
    public void triggerCarMalfunction() {
        Random random = new Random();
        if (random.nextInt(1, 101) > (playerCar.getCarHandling() + 20)) {
            racePenalties.put(playerCar, racePenalties.getOrDefault(playerCar, 0) + 10);
            Platform.runLater(raceScreenController::onPlayerMalfunction);
        }
    }

    /**
     * Randomly triggers a random race event based on difficulty.
     */
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

    /**
     * Applies hitchhiker/stranded traveller event to player car. Applies a time penalty and rewards money
     */
    public void handleHitchhiker() {
        String infoText = "Pick up a hitchhiker. This costs you time, but they pay you $50!";
        racePenalties.put(playerCar, racePenalties.getOrDefault(playerCar, 0) + 20);
        Platform.runLater(() -> raceScreenController.onHitchhikerEvent(infoText));
    }

    /**
     * Applies severe weather event which forces all cars to retire from the race.
     */
    public void handleSevereWeatherEvent() {
        String alertText = "Severe weather! All cars forced to retire from the race!";
        carStatus.replaceAll((c, v) -> RaceStatus.DNF);
        Platform.runLater(() -> raceScreenController.onSevereWeatherEvent(alertText));
    }

    /**
     * Updates finishing positions with penalties and sorts by race time and distance.
     */
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
    }

    /**
     * Handles the player car failing to finish the race (DNF) and updates GUI.
     */
    public void playerDNF() {
        Platform.runLater(raceScreenController::onPlayerDNF);
    }

    /**
     *Handles scenario when a player retires from the race after a car breakdown random event.
     */
    public void playerRetired(){
        Platform.runLater(raceScreenController::onPlayerRetired);
    }

    /**
     * Handles scenario where player does not finish within race time.
     */
    private void playerOutOfTime() {
        Platform.runLater(raceScreenController::onPlayerOutOfTime);
    }

    /**
     * Handles end-of-race logic when the player finishes.
     */
    private void playerFinished() {
        updateFinishPositions();

        int playerPosition = finishPositions.indexOf(playerCar) + 1;
        playerFinishPositions.add(playerPosition); //list to keep track of player positions throughout race
        int prizeMoney = calculatePrizeMoney(playerPosition);
        playerTotalPrizeMoney += prizeMoney;
        Platform.runLater(() -> raceScreenController.onPlayerFinished(playerPosition, prizeMoney));
    }

    /**
     * Calculates the average placing of the player over all races.
     *
     * @return Average placing value.
     */
    public double getPlayerAveragePlacing() {
        if (playerFinishPositions.isEmpty()) {
            return 0;
        }
        int playerAveragePlacing = 0;
        for (Integer playerFinishPosition : playerFinishPositions) {
            playerAveragePlacing += playerFinishPosition;
        }
        playerAveragePlacing /= playerFinishPositions.size();
        return playerAveragePlacing;
    }

    /**
     * Returns total prize money earned by the player.
     *
     * @return Total prize money.
     */
    public int getPlayerTotalPrizeMoney() {
        return playerTotalPrizeMoney;
    }

    /**
     * Checks if any cars are completely non-functional.
     *
     * @param cars The list of cars to check.
     * @return true if any car has 0 in speed, reliability, or fuel economy.
     */
    public boolean noCarsFunctioning(List<Car> cars) {
        for (Car car : cars) {
            if (car.getCarSpeed() == 0 || car.getCarReliability() == 0 || car.getCarFuelEconomy()==0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates prize money based on final position in the race.
     *
     * @param position Player's final position.
     * @return Calculated prize money.
     */
    private int calculatePrizeMoney(int position) {
        int prizeMoney = race.getRacePrizeMoney();
        switch (position) {
            case 1:
                break;
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







