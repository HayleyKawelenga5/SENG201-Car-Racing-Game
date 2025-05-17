package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seng201.team0.models.Upgrade;
import seng201.team0.services.UpgradeService;

import java.util.Set;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UpgradeServiceTest {

    private UpgradeService upgradeService;

    @BeforeEach
    public void setup() {
        upgradeService = new UpgradeService();
    }

    @Test
    public void testGenerateRandomUpgrades() {
        List<Upgrade> upgrades = upgradeService.generateRandomUpgrades();

        assertEquals(3, upgrades.size());

        Set<String> validNames = Set.of("Turbocharger", "Offroad Tires", "Lightweight Chassis", "Air Suspension", "Eco Tuner");

        for (Upgrade upgrade : upgrades) {
            assertTrue(validNames.contains(upgrade.getUpgradeName()));

            assertTrue(upgrade.getUpgradeSpeed() % 10 == 0 && upgrade.getUpgradeSpeed() >= 0 && upgrade.getUpgradeSpeed() <= 30);
            assertTrue(upgrade.getUpgradeHandling() % 10 == 0 && upgrade.getUpgradeHandling() >= 0 && upgrade.getUpgradeHandling() <= 30);
            assertTrue(upgrade.getUpgradeReliability() % 10 == 0 && upgrade.getUpgradeReliability() >= 0 && upgrade.getUpgradeReliability() <= 30);
            assertTrue(upgrade.getUpgradeFuelEconomy() % 10 == 0 && upgrade.getUpgradeFuelEconomy() >= 0 && upgrade.getUpgradeFuelEconomy() <= 30);

            int expectedCost = (upgrade.getUpgradeSpeed()
                    + upgrade.getUpgradeHandling()
                    + upgrade.getUpgradeReliability()
                    + upgrade.getUpgradeFuelEconomy()) * 4;
            assertEquals(expectedCost, upgrade.getUpgradeCost());
        }
    }

}