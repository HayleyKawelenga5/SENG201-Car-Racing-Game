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

    /**
     * Constructs a new Upgrade with a name and stat modifications.
     *
     * @param name                The name of the upgrade.
     * @param speedUpgrade        Value to increase car speed.
     * @param handlingUpgrade     Value to increase car handling.
     * @param reliabilityUpgrade  Value to increase car reliability.
     * @param fuelEconomyUpgrade  Value to increase car fuel economy.
     */
    public Upgrade(String name, int speedUpgrade, int handlingUpgrade, int reliabilityUpgrade, int fuelEconomyUpgrade) {
        this.name = name;
        this.speedUpgrade = speedUpgrade;
        this.handlingUpgrade = handlingUpgrade;
        this.reliabilityUpgrade = reliabilityUpgrade;
        this.fuelEconomyUpgrade = fuelEconomyUpgrade;
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

    /**
     * Generates a random Upgrade from a predefined set.
     *
     * @return A randomly generated Upgrade.
     */
    public Upgrade generateRandomUpgrade() {
        Random random = new Random();
        String[] upgradeNames = {
                "Turbocharger",
                "Offroad Tires",
                "Lightweight Chassis",
                "Air Suspension",
                "Eco Tuner"
        };

        String selectedName = upgradeNames[random.nextInt(upgradeNames.length)];

        int speed = 0;
        int handling = 0;
        int reliability = 0;
        int fuelEconomy = 0;

        switch (selectedName) {
            case "Turbocharger":
                speed = random.nextInt(1, 4);
                cost = speed * 4;
                break;
            case "Offroad Tires":
                handling = random.nextInt(1, 4);
                reliability = random.nextInt(1, 4);
                cost = (handling + reliability) * 4;
                break;
            case "Lightweight Chassis":
                speed = random.nextInt(1, 4);
                fuelEconomy = random.nextInt(1, 4);
                cost = (speed + fuelEconomy) * 4;
                break;
            case "Air Suspension":
                handling = random.nextInt(1, 4);
                reliability = random.nextInt(1, 4);
                cost = (handling + reliability) * 4;
                break;
            case "Eco Tuner":
                fuelEconomy = random.nextInt(1, 4);
                cost = fuelEconomy * 4;
                break;
        }
        return new Upgrade(selectedName, speed, handling, reliability, fuelEconomy);
    }

}
