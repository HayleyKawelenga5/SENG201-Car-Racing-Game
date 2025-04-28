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
        List<Car> availableCars = testCarService.generateRandomCars();
        assertEquals(5, availableCars.size());

        for (Car car : availableCars) {
            assertTrue(car.getSpeed() >= 1 && car.getSpeed() <= 10);
            assertTrue(car.getHandling() >= 1 && car.getHandling() <= 10);
            assertTrue(car.getReliability() >= 1 && car.getReliability() <= 10);
            assertTrue(car.getFuelEconomy() >= 1 && car.getFuelEconomy() <= 10);
            assertTrue(car.getCost() == ((car.getSpeed() + car.getHandling() + car.getReliability() + car.getFuelEconomy()) * 10));
        }
    }

}