package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.Car;
import seng201.team0.models.Upgrade;
import seng201.team0.services.CarService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CarServiceTest {

    private CarService testCarService;

    /**
     * Sets up a new instance of CarService before each test is run.
     */
    @BeforeEach
    public void setupTest() {
        testCarService = new CarService();
    }

    /**
     * Tests that generateRandomCars() generates exactly 5 cars
     * and that each car's attributes (speed, handling, reliability, fuel economy)
     * are within the range [1, 10], inclusive. Also checks that each car's cost is
     * correctly calculated based on its stats.
     */
    @Test
    void testGenerateRandomCars() {
        List<Car> availableCars = testCarService.generateRandomCars(5);
        assertEquals(5, availableCars.size());

        for (Car car : availableCars) {
            assertTrue(car.getCarSpeed() >= 20 && car.getCarSpeed() <= 100);
            assertTrue(car.getCarHandling() >= 20 && car.getCarHandling() <= 100);
            assertTrue(car.getCarReliability() >= 20 && car.getCarReliability() <= 100);
            assertTrue(car.getCarFuelEconomy() >= 20 && car.getCarFuelEconomy() <= 100);
            assertTrue(car.getCarUpgrades().isEmpty());
            assertEquals(car.getCarCost(), (car.getCarSpeed() + car.getCarHandling() + car.getCarReliability() + car.getCarFuelEconomy()));
        }
    }

    @Test
    void testGetAvailableCars() {
        List<Car> generatedCars = testCarService.generateRandomCars(3);
        List<Car> retrievedCars = testCarService.getAvailableCars();

        assertEquals(generatedCars.size(), retrievedCars.size());
        assertIterableEquals(generatedCars, retrievedCars);
    }

    @Test
    void testEqualsMethod() {
        List<Upgrade> upgrades = new ArrayList<>();
        Car car1 = new Car(60, 60, 60, 60, 240, upgrades);
        Car car2 = new Car(60, 60, 60, 60, 240, upgrades);

        assertEquals(car1, car2);
        assertNotEquals(null, car1);
    }

}