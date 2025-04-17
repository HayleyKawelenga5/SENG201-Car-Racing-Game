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

    public String getName() { return name; }

    public int getSpeedUpgrade() { return speedUpgrade; }

    public int getHandlingUpgrade() { return handlingUpgrade; }

    public int getReliabilityUpgrade() { return reliabilityUpgrade; }

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
                "Reinforced Suspension",
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
                break;
            case "Offroad Tires":
                handling = random.nextInt(1, 4);
                reliability = random.nextInt(1, 3);
                break;
            case "Lightweight Chassis":
                speed = random.nextInt(1, 3);
                fuelEconomy = random.nextInt(1, 3);
                break;
            case "Reinforced Suspension":
                handling = random.nextInt(1, 3);
                reliability = random.nextInt(1, 4);
                break;
            case "Eco Tuner":
                fuelEconomy = random.nextInt(2, 5);
                break;
        }
        return new Upgrade(selectedName, speed, handling, reliability, fuelEconomy);
    }

}
