package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.Car;
import seng201.team0.services.GameInitializer;

import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameInitializerTest {

    private GameInitializer gameInitializer;

    @BeforeEach
    public void setup() { gameInitializer = new GameInitializer(); }

    // Name tests

    @Test
    public void testValidName() {
        assertDoesNotThrow(() -> gameInitializer.selectName("ABCDE"));
    }

    @Test
    public void testInvalidNameTooShort() {
        assertThrows(InvalidNameException.class, () -> gameInitializer.selectName("AB"));
    }

    @Test
    public void testInvalidNameTooLong() {
        assertThrows(InvalidNameException.class, () -> gameInitializer.selectName("ABCDEFGHIJKLMNOPQ"));
    }

    @Test
    public void testInvalidNameWithSpecialCharacters() {
        assertThrows(InvalidNameException.class, () -> gameInitializer.selectName("Player_!"));
    }

    // Season length tests

    @Test
    public void testValidSeasonLength() {
        assertDoesNotThrow(() -> gameInitializer.selectSeasonLength(10));
    }

    @Test
    public void testInvalidSeasonLengthTooShort() {
        assertThrows(IllegalArgumentException.class, () -> gameInitializer.selectSeasonLength(4));
    }

    @Test
    public void testInvalidSeasonLengthTooLong() {
        assertThrows(IllegalArgumentException.class, () -> gameInitializer.selectSeasonLength(16));
    }

    // Difficulty tests

    @Test
    public void testSelectEasyDifficulty() {
        gameInitializer.selectDifficulty("EASY");
        assertEquals(600, gameInitializer.getMoney());
    }

    @Test
    public void testSelectHardDifficulty() {
        gameInitializer.selectDifficulty("HARD");
        assertEquals(400, gameInitializer.getMoney());
    }

    @Test
    public void testInvalidDifficulty() {
        assertThrows(IllegalArgumentException.class, () -> gameInitializer.selectDifficulty("MEDIUM"));
    }

    // Player car management tests

    @Test
    public void testAddCarWithEnoughMoney() {
        gameInitializer.setMoney(600);
        Car car = new Car(50, 50, 50, 50, 200, new ArrayList<>());
        assertTrue(gameInitializer.addCar(car));
        assertEquals(1, gameInitializer.getPlayerCars().size());
        assertEquals(400, gameInitializer.getMoney());
    }

    @Test
    public void testAddCarNotEnoughMoney() {
        gameInitializer.setMoney(100);
        Car car = new Car(50, 50, 50, 50, 200, new ArrayList<>());
        assertFalse(gameInitializer.addCar(car));
        assertEquals(0, gameInitializer.getPlayerCars().size());
    }

    @Test
    public void testAddSameCarTwice() {
        gameInitializer.setMoney(600);
        Car car = new Car(50, 50, 50, 50, 200, new ArrayList<>());
        assertTrue(gameInitializer.addCar(car));
        assertFalse(gameInitializer.addCar(car));
        assertEquals(1, gameInitializer.getPlayerCars().size());
    }

    @Test
    public void testAddMoreThanMaxCars() {
        gameInitializer.setMoney(1000);
        Car car1 = new Car(50, 50, 50, 50, 200, new ArrayList<>());
        Car car2 = new Car(50, 50, 50, 50, 200, new ArrayList<>());
        Car car3 = new Car(50, 50, 50, 50, 200, new ArrayList<>());
        Car car4 = new Car(50, 50, 50, 50, 200, new ArrayList<>());
        assertTrue(gameInitializer.addCar(car1));
        assertTrue(gameInitializer.addCar(car2));
        assertTrue(gameInitializer.addCar(car3));
        assertFalse(gameInitializer.addCar(car4));
        assertEquals(3, gameInitializer.getPlayerCars().size());
    }

    @Test
    public void testDeleteCarThatExists() {
        gameInitializer.setMoney(600);
        Car car = new Car(50, 50, 50, 50, 200, new ArrayList<>());
        gameInitializer.addCar(car);
        assertTrue(gameInitializer.deleteCar(car));
        assertEquals(0, gameInitializer.getPlayerCars().size());
        assertEquals(600, gameInitializer.getMoney());
    }

    @Test
    public void testDeleteCarThatDoesNotExist() {
        gameInitializer.setMoney(600);
        Car car = new Car(50, 50, 50, 50, 200, new ArrayList<>());
        assertFalse(gameInitializer.deleteCar(car));
        assertEquals(0, gameInitializer.getPlayerCars().size());
        assertEquals(600, gameInitializer.getMoney());
    }

    @Test
    public void testGetAvailableCarsGeneratesIfEmpty() {
        List<Car> availableCars = gameInitializer.getAvailableCars();
        assertFalse(availableCars.isEmpty());
        assertEquals(5, availableCars.size());
    }

}
