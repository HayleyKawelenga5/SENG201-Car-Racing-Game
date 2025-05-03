package seng201.team0.models;

import java.util.Random;

/**
 * Represents an upgrade that can be applied to a car to improve its stats.
 */
public class Upgrade {

    private String upgradeName;
    private int upgradeSpeed;
    private int upgradeHandling;
    private int upgradeReliability;
    private int upgradeFuelEconomy;
    private int upgradeCost;

    /**
     * Constructs a new Upgrade with a name and stat modifications.
     *
     * @param name                The name of the upgrade.
     * @param speedUpgrade        Value to increase car speed.
     * @param handlingUpgrade     Value to increase car handling.
     * @param reliabilityUpgrade  Value to increase car reliability.
     * @param fuelEconomyUpgrade  Value to increase car fuel economy.
     * @param cost                Cost of the upgrade.
     */
    public Upgrade(String upgradeName, int upgradeSpeed, int upgradeHandling, int upgradeReliability, int upgradeFuelEconomy, int upgradeCost) {
        this.upgradeName = upgradeName;
        this.upgradeSpeed = upgradeSpeed;
        this.upgradeHandling = upgradeHandling;
        this.upgradeReliability = upgradeReliability;
        this.upgradeFuelEconomy = upgradeFuelEconomy;
        this.upgradeCost = upgradeCost;
    }

    public String getUpgradeName() { return upgradeName; }

    public int getUpgradeSpeed() { return upgradeSpeed; }

    public int getUpgradeHandling() { return upgradeHandling; }

    public int getUpgradeReliability() { return upgradeReliability; }

    public int getUpgradeFuelEconomy() { return upgradeFuelEconomy; }

    public int getUpgradeCost() { return upgradeCost; }

}
