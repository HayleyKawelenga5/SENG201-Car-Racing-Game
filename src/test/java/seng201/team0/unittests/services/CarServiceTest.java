package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.Car;
import seng201.team0.services.CarService;

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
            assertTrue(car.getCarSpeed() >= 1 && car.getCarSpeed() <= 10);
            assertTrue(car.getCarHandling() >= 1 && car.getCarHandling() <= 10);
            assertTrue(car.getCarReliability() >= 1 && car.getCarReliability() <= 10);
            assertTrue(car.getCarFuelEconomy() >= 1 && car.getCarFuelEconomy() <= 10);
            assertTrue(car.getCarCost() == ((car.getCarSpeed() + car.getCarHandling() + car.getCarReliability() + car.getCarFuelEconomy()) * 10));
        }
    }

}