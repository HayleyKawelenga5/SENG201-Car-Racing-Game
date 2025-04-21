package seng201.team0.services;

import seng201.team0.models.Upgrade;

import java.util.Random;

public class UpgradeService {

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
        int cost = 0;

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
        return new Upgrade(selectedName, speed, handling, reliability, fuelEconomy, cost);
    }



}
