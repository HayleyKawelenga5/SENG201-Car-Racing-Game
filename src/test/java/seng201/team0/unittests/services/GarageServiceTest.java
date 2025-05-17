package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.Car;
import seng201.team0.models.Upgrade;
import seng201.team0.services.GarageService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GarageServiceTest {

    private GarageService garageService;
    private Car testCar;
    private Upgrade testUpgrade;
    private List<Upgrade> playerUpgrades;

    @BeforeEach
    public void setup() {
        garageService = new GarageService();
        testCar = new Car(90, 50, 50, 50, 240, new ArrayList<>());
        testUpgrade = new Upgrade("Lightweight Chassis", 20, 0, 0, 10, 120);
        playerUpgrades = new ArrayList<>();
        playerUpgrades.add(testUpgrade);
    }

    @Test
    public void testInstallUpgradeSuccessfully() {
        boolean result = garageService.installUpgrade(testCar, testUpgrade, playerUpgrades);
        assertTrue(result);
        assertEquals(100, testCar.getCarSpeed());
        assertEquals(50, testCar.getCarHandling());
        assertEquals(50, testCar.getCarReliability());
        assertEquals(60, testCar.getCarFuelEconomy());
        assertEquals(270, testCar.getCarCost());
        assertTrue(testCar.getCarUpgrades().contains(testUpgrade));
        assertFalse(playerUpgrades.contains(testUpgrade));
    }

    @Test
    public void testInstallUpgradeNotOwned() {
        playerUpgrades.clear();
        boolean result = garageService.installUpgrade(testCar, testUpgrade, playerUpgrades);
        assertFalse(result);
        assertEquals(90, testCar.getCarSpeed());
        assertTrue(testCar.getCarUpgrades().isEmpty());
    }

    @Test
    public void testInstallUpgradeWithNullCar() {
        boolean result = garageService.installUpgrade(null, testUpgrade, playerUpgrades);
        assertFalse(result);
    }

    @Test
    public void testInstallUpgradeWithNullUpgrade() {
        boolean result = garageService.installUpgrade(testCar, null, playerUpgrades);
        assertFalse(result);
    }

}