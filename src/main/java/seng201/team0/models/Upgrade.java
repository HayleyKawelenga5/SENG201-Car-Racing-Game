package seng201.team0.models;

import java.util.Random;

/**
 * Represents an upgrade that can be applied to a car to improve its stats.
 */
public class Upgrade {
    private String name;
    private int speedUpgrade;
    private int handlingUpgrade;
    private int reliabilityUpgrade;
    private int fuelEconomyUpgrade;
    private int cost;

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
    public Upgrade(String name, int speedUpgrade, int handlingUpgrade, int reliabilityUpgrade, int fuelEconomyUpgrade, int cost) {
        this.name = name;
        this.speedUpgrade = speedUpgrade;
        this.handlingUpgrade = handlingUpgrade;
        this.reliabilityUpgrade = reliabilityUpgrade;
        this.fuelEconomyUpgrade = fuelEconomyUpgrade;
        this.cost = cost;
    }

    /**
     * Gets the name of the upgrade.
     *
     * @return The name of the upgrade.
     */
    public String getName() { return name; }

    /**
     * Gets the speed improvement value of the upgrade.
     *
     * @return The value added to speed by this upgrade.
     */
    public int getSpeedUpgrade() { return speedUpgrade; }

    /**
     * Gets the handling improvement value of the upgrade.
     *
     * @return The value added to handling by this upgrade.
     */
    public int getHandlingUpgrade() { return handlingUpgrade; }

    /**
     * Gets the reliability improvement value of the upgrade.
     *
     * @return The value added to reliability by this upgrade.
     */
    public int getReliabilityUpgrade() { return reliabilityUpgrade; }

    /**
     * Gets the fuel economy improvement value of the upgrade.
     *
     * @return The value added to fuel economy by this upgrade.
     */
    public int getFuelEconomyUpgrade() { return fuelEconomyUpgrade; }

    public int getCost() { return cost; }

}
