package seng201.team0.services;

import seng201.team0.models.Upgrade;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class UpgradeService {

    private List<Upgrade> availableUpgrades = new ArrayList<>();

    /**
     * Generates a random Upgrade from a predefined set.
     *
     * @return A randomly generated Upgrade.
     */
    public List<Upgrade> generateRandomUpgrades() {
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            String[] upgradeNames = {
                    "Turbocharger",
                    "Offroad Tires",
                    "Lightweight Chassis",
                    "Air Suspension",
                    "Eco Tuner"
            };

            String name = upgradeNames[random.nextInt(upgradeNames.length)];

            int speed = 0;
            int handling = 0;
            int reliability = 0;
            int fuelEconomy = 0;
            int cost = 0;

            switch (name) {
                case "Turbocharger":
                    speed = random.nextInt(1, 4) * 10;
                    cost = speed * 4;
                    break;
                case "Offroad Tires":
                    handling = random.nextInt(1, 4) * 10;
                    reliability = random.nextInt(1, 4) * 10;
                    cost = (handling + reliability) * 4;
                    break;
                case "Lightweight Chassis":
                    speed = random.nextInt(1, 4) * 10;
                    fuelEconomy = random.nextInt(1, 4) * 10;
                    cost = (speed + fuelEconomy) * 4;
                    break;
                case "Air Suspension":
                    handling = random.nextInt(1, 4) * 10;
                    reliability = random.nextInt(1, 4) * 10;
                    cost = (handling + reliability) * 4;
                    break;
                case "Eco Tuner":
                    fuelEconomy = random.nextInt(1, 4) * 10;
                    cost = fuelEconomy * 4;
                    break;
            }
            availableUpgrades.add(new Upgrade(name, speed, handling, reliability, fuelEconomy, cost));
        }
        return availableUpgrades;
    }

}
