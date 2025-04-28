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

    public String getName() { return name; }

    public int getSpeedUpgrade() { return speedUpgrade; }

    public int getHandlingUpgrade() { return handlingUpgrade; }

    public int getReliabilityUpgrade() { return reliabilityUpgrade; }

    public int getFuelEconomyUpgrade() { return fuelEconomyUpgrade; }

    public int getCost() { return cost; }

}
