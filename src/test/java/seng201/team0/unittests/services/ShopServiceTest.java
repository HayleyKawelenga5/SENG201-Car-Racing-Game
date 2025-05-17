package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.Car;
import seng201.team0.models.Upgrade;
import seng201.team0.services.ShopService;

import seng201.team0.GameManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ShopServiceTest {

    private ShopService shopService;
    private GameManager gameManager;

    @BeforeEach
    public void setup() {
        shopService = new ShopService();
        gameManager = new GameManager();
        gameManager.setMoney(1000);
        gameManager.setPlayerCars(new ArrayList<>());
        gameManager.setPlayerUpgrades(new ArrayList<>());
    }

    @Test
    public void testBuyCarWithEnoughMoneyAndSpace() {
        Car car = new Car(50, 50, 50, 50, 200, new ArrayList<>());
        boolean result = shopService.buyCar(car, gameManager);
        assertTrue(result);
        assertEquals(1, gameManager.getPlayerCars().size());
        assertEquals(800, gameManager.getMoney());
    }

    @Test
    public void testBuyCarNotEnoughMoney() {
        gameManager.setMoney(100);
        Car car = new Car(50, 50, 50, 50, 200, new ArrayList<>());
        boolean result = shopService.buyCar(car, gameManager);
        assertFalse(result);
        assertEquals(0, gameManager.getPlayerCars().size());
        assertEquals(100, gameManager.getMoney());
    }

    @Test
    public void testBuyCarAtMaxCapacity() {
        for (int i = 0; i < 5; i++) {
            gameManager.getPlayerCars().add(new Car(40, 40, 40, 40, 160, new ArrayList<>()));
        }
        Car newCar = new Car(50, 50, 50, 50, 200, new ArrayList<>());
        boolean result = shopService.buyCar(newCar, gameManager);
        assertFalse(result);
        assertEquals(5, gameManager.getPlayerCars().size());
    }

    @Test
    public void testSellCar() {
        Car car = new Car(60, 60, 60, 60, 240, new ArrayList<>());
        gameManager.getPlayerCars().add(car);
        boolean result = shopService.sellCar(car, gameManager);
        assertTrue(result);
        assertEquals(0, gameManager.getPlayerCars().size());
        assertEquals(1120, gameManager.getMoney());
    }

    @Test
    public void testSellCarNotOwned() {
        Car car = new Car(60, 60, 60, 60, 240, new ArrayList<>());
        boolean result = shopService.sellCar(car, gameManager);
        assertFalse(result);
        assertEquals(0, gameManager.getPlayerCars().size());
    }

    @Test
    public void testBuyUpgradeWithEnoughMoneyAndSpace() {
        Upgrade upgrade = new Upgrade("Turbocharger", 20, 0, 0, 0, 80);
        boolean result = shopService.buyUpgrade(upgrade, gameManager);
        assertTrue(result);
        assertEquals(1, gameManager.getPlayerUpgrades().size());
        assertEquals(920, gameManager.getMoney());
    }

    @Test
    public void testBuyUpgradeNotEnoughMoney() {
        gameManager.setMoney(50);
        Upgrade upgrade = new Upgrade("Turbocharger", 20, 0, 0, 0, 80);
        boolean result = shopService.buyUpgrade(upgrade, gameManager);
        assertFalse(result);
        assertEquals(0, gameManager.getPlayerUpgrades().size());
    }

    @Test
    public void testBuyUpgradeAtMaxCapacity() {
        for (int i = 0; i < 3; i++) {
            gameManager.getPlayerUpgrades().add(new Upgrade("Eco Tuner", 0, 0, 0, 40, 120));
        }
        Upgrade upgrade = new Upgrade("Air Suspension", 0, 10, 20, 0, 120);
        boolean result = shopService.buyUpgrade(upgrade, gameManager);
        assertFalse(result);
        assertEquals(3, gameManager.getPlayerUpgrades().size());
    }

    @Test
    public void testSellUpgrade() {
        Upgrade upgrade = new Upgrade("Eco Tuner", 0, 0, 0, 30, 120);
        gameManager.getPlayerUpgrades().add(upgrade);
        boolean result = shopService.sellUpgrade(upgrade, gameManager);
        assertTrue(result);
        assertEquals(0, gameManager.getPlayerUpgrades().size());
        assertEquals(1060, gameManager.getMoney());
    }

    @Test
    public void testSellUpgradeNotOwned() {
        Upgrade upgrade = new Upgrade("Eco Tuner", 0, 0, 0, 30, 120);
        boolean result = shopService.sellUpgrade(upgrade, gameManager);
        assertFalse(result);
        assertEquals(0, gameManager.getPlayerUpgrades().size());
    }

}