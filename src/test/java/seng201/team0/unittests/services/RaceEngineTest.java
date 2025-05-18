package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seng201.team0.models.Car;
import seng201.team0.models.Race;
import seng201.team0.models.Route;
import seng201.team0.models.Route.RouteType;
import seng201.team0.services.RaceEngine;
import seng201.team0.gui.RaceScreenController;

import seng201.team0.GameManager;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RaceEngineTest {

    private Race race;
    private Route route;
    private Car playerCar;
    private RaceEngine raceEngine;
    private RaceScreenController raceScreenController;
    private GameManager gameManager;

    @BeforeEach
    public void setup() {
        gameManager = new GameManager();
        List<Route> routes = List.of(
                new Route(RouteType.FLAT, 100, 2, 1.2),
                new Route(RouteType.BEACH, 100, 2, 1.6),
                new Route(RouteType.WINDY, 100, 2, 1.4)
        );
        race = new Race(6, routes, 6, 480);
        playerCar = new Car(90, 80, 70, 60, 300, new ArrayList<>());
        route = new Route(RouteType.FLAT, 100, 2, 1.2);
        raceEngine = new RaceEngine(gameManager, race, route, playerCar, "EASY", raceScreenController);
        raceScreenController = new RaceScreenController(gameManager);
    }

    @Test
    public void testApplyRouteMultipliersFlat() {
        Car car = new Car(90, 80, 70, 60, 300, new ArrayList<>());
        Route route = new Route(RouteType.FLAT, 100, 2, 1.2);
        RaceEngine engine = new RaceEngine(gameManager, race, route, car, "EASY", raceScreenController);
        raceEngine.applyRouteMultipliers();
        assertEquals(90, car.getCarSpeed());
        assertEquals(80, car.getCarHandling());
        assertEquals(70, car.getCarReliability());
        assertEquals(60, car.getCarFuelEconomy());
    }

    @Test
    public void testApplyRouteMultipliersBeach() {
        Car car = new Car(90, 80, 70, 60, 300, new ArrayList<>());
        Route route = new Route(RouteType.BEACH, 100, 2, 1.6);
        RaceEngine engine = new RaceEngine(gameManager, race, route, car, "EASY", raceScreenController);
        engine.applyRouteMultipliers();
        assertEquals(56, car.getCarSpeed());
        assertEquals(50, car.getCarHandling());
        assertEquals(43, car.getCarReliability());
        assertEquals(37, car.getCarFuelEconomy());
    }

    @Test
    public void testApplyRouteMultipliersWindy() {
        Car car = new Car(90, 80, 70, 60, 300, new ArrayList<>());
        Route route = new Route(RouteType.WINDY, 100, 2, 1.4);
        RaceEngine engine = new RaceEngine(gameManager, race, route, car, "EASY", raceScreenController);
        engine.applyRouteMultipliers();
        assertEquals(64, car.getCarSpeed());
        assertEquals(57, car.getCarHandling());
        assertEquals(70, car.getCarReliability());
        assertEquals(60, car.getCarFuelEconomy());
    }

    @Test
    public void testApplyRouteMultipliersHilly() {
        Car car = new Car(90, 80, 70, 60, 300, new ArrayList<>());
        Route route = new Route(RouteType.HILLY, 100, 2, 1.4);
        RaceEngine engine = new RaceEngine(gameManager, race, route, car, "EASY", raceScreenController);
        engine.applyRouteMultipliers();
        assertEquals(64, car.getCarSpeed());
        assertEquals(80, car.getCarHandling());
        assertEquals(70, car.getCarReliability());
        assertEquals(42, car.getCarFuelEconomy());
    }

    @Test
    public void testApplyRouteMultipliersOffroad() {
        Car car = new Car(90, 80, 70, 60, 300, new ArrayList<>());
        Route route = new Route(RouteType.OFFROAD, 100, 2, 1.4);
        RaceEngine engine = new RaceEngine(gameManager, race, route, car, "EASY", raceScreenController);
        engine.applyRouteMultipliers();
        assertEquals(90, car.getCarSpeed());
        assertEquals(57, car.getCarHandling());
        assertEquals(50, car.getCarReliability());
        assertEquals(60, car.getCarFuelEconomy());
    }

    @Test
    public void testConsumeFuel() {
        playerCar.setCarFuelAmount(50);
        playerCar.setCarSpeed(20);
        raceEngine.consumeFuel(playerCar);
        assertEquals(40, playerCar.getCarFuelAmount());
    }

    @Test
    public void testRefuel() {
        playerCar.setCarFuelAmount(10);
        playerCar.setCarFuelEconomy(60);
        raceEngine.refuel(playerCar);
        assertEquals(60, playerCar.getCarFuelAmount());
        assertEquals(10, raceEngine.getRacePenalties().get(playerCar));
    }

    @Test
    public void testBreakdownContinueAddsPenalty() {
        raceEngine.breakdownContinue();
        raceEngine.breakdownContinue();
        assertEquals(40, raceEngine.getRacePenalties().get(playerCar));
    }

    @Test
    public void testGenerateFuelStops() {
        Route route = new Route(RouteType.FLAT, 100, 2, 1.2);
        raceEngine.generateFuelStops();
        List<Integer> fuelStops = raceEngine.getFuelStops();
        assertEquals(2, fuelStops.size());
        assertEquals(33, fuelStops.get(0));
        assertEquals(66, fuelStops.get(1));
    }

    @Test
    public void testGetPlayerAveragePlacingEmpty() {
        assertEquals(0, raceEngine.getPlayerAveragePlacing());
    }

    @Test
    public void testNoCarsFunctioningTrue() {
        Car car1 = new Car(0, 50, 0, 50, 50, new ArrayList<>());
        Car car2 = new Car(60, 60, 60, 0, 60, new ArrayList<>());
        Car car3 = new Car(0, 70, 70, 0, 70, new ArrayList<>());
        assertTrue(raceEngine.noCarsFunctioning(List.of(car1, car2, car3)));
    }

    @Test
    public void testNoCarsFunctioningFalse() {
        Car car1 = new Car(50, 50, 0, 0, 100, new ArrayList<>());
        Car car2 = new Car(0, 60, 0, 0, 60, new ArrayList<>());
        Car car3 = new Car(50, 50, 50, 50, 140, new ArrayList<>());
        assertFalse(raceEngine.noCarsFunctioning(List.of(car1, car2, car3)));
    }

    @Test
    void testUpdateFinishPositions() {

        Car car1 = new Car(50, 50, 50, 50, 200, new ArrayList<>());
        Car car2 = new Car(50, 50, 50, 50, 200, new ArrayList<>());
        Car car3 = new Car(50, 50, 50, 50, 200, new ArrayList<>());

        raceEngine.setFinishPositions(new ArrayList<>(List.of(car1, car2, car3)));

        raceEngine.setCarDistances(new HashMap<>(Map.of(
                car1, 100,
                car2, 110,
                car3, 105
        )));

        raceEngine.setCarHours(new HashMap<>(Map.of(
                car1, 3,
                car2, 2,
                car3, 3
        )));

        raceEngine.setRacePenalties(new HashMap<>(Map.of(
                car1, 5,
                car2, 0,
                car3, 10
        )));

        raceEngine.updateFinishPositions();

        assertEquals(95, raceEngine.getCarDistances().get(car1));
        assertEquals(110, raceEngine.getCarDistances().get(car2));
        assertEquals(95, raceEngine.getCarDistances().get(car3));

        List<Car> expectedOrder = List.of(car2, car1, car3);
        assertEquals(expectedOrder, raceEngine.getFinishPositions());
    }

    @Test
    void testReduceCurrentCarStats() {
        Car car = new Car(50, 60, 70, 80, 260, new ArrayList<>());

        raceEngine.reduceCurrentCarStats(car);

        assertEquals(40, car.getCarSpeed());
        assertEquals(50, car.getCarHandling());
        assertEquals(60, car.getCarReliability());
        assertEquals(70, car.getCarFuelEconomy());
        assertEquals(220, car.getCarCost());
    }

    @Test
    void testCalculatePrizeMoney() {
        race.setRacePrizeMoney(1000);

        assertEquals(1000, raceEngine.calculatePrizeMoney(1));
        assertEquals(800, raceEngine.calculatePrizeMoney(2));
        assertEquals(600, raceEngine.calculatePrizeMoney(3));
        assertEquals(400, raceEngine.calculatePrizeMoney(4));
        assertEquals(200, raceEngine.calculatePrizeMoney(5));
        assertEquals(0, raceEngine.calculatePrizeMoney(6));
    }

}

