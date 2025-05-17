package seng201.team0.utils;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import seng201.team0.models.Car;
import seng201.team0.models.Race;
import seng201.team0.models.Route;
import seng201.team0.models.Upgrade;

import java.util.List;

public class ScreenUpdater {

    public static void updateCarStats(Car car, Label carSpeedLabel, Label carHandlingLabel, Label carReliabilityLabel, Label carFuelEconomyLabel, Label carUpgradesLabel) {
        if (car == null) {
            carSpeedLabel.setText("Speed: ");
            carHandlingLabel.setText("Handling: ");
            carReliabilityLabel.setText("Reliability: ");
            carFuelEconomyLabel.setText("Fuel Economy: ");
            carUpgradesLabel.setText("Upgrades: ");
        } else {
            carSpeedLabel.setText("Speed: " + car.getCarSpeed());
            carHandlingLabel.setText("Handling: " + car.getCarHandling());
            carReliabilityLabel.setText("Reliability: " + car.getCarReliability());
            carFuelEconomyLabel.setText("Fuel Economy: " + car.getCarFuelEconomy());
            carUpgradesLabel.setText("Upgrades: " + car.getUpgradeDescription());
        }
    }

    public static void updateUpgradeStats(Upgrade upgrade, Label upgradeSpeedLabel, Label upgradeHandlingLabel, Label upgradeReliabilityLabel, Label upgradeFuelEconomyLabel) {
        if (upgrade == null) {
            upgradeSpeedLabel.setText("Speed: ");
            upgradeHandlingLabel.setText("Handling: ");
            upgradeReliabilityLabel.setText("Reliability: ");
            upgradeFuelEconomyLabel.setText("Fuel Economy: ");
        } else {
            upgradeSpeedLabel.setText("Speed: " + upgrade.getUpgradeSpeed());
            upgradeHandlingLabel.setText("Handling: " + upgrade.getUpgradeHandling());
            upgradeReliabilityLabel.setText("Reliability: " + upgrade.getUpgradeReliability());
            upgradeFuelEconomyLabel.setText("Fuel Economy: " + upgrade.getUpgradeFuelEconomy());
        }
    }

    public static void updateRaceStats(Race race, Label raceHoursLabel, Label raceRoutesLabel, Label raceEntriesLabel, Label racePrizeMoneyLabel){
        if (race == null) {
            raceHoursLabel.setText("Hours: ");
            raceRoutesLabel.setText("Routes: ");
            raceEntriesLabel.setText("Entries: ");
            racePrizeMoneyLabel.setText("Prize Money: ");
        } else {
            raceHoursLabel.setText("Hours: " + race.getRaceHours());
            raceRoutesLabel.setText("Routes: " + race.getRouteList());
            raceEntriesLabel.setText("Entries: " + race.getRaceEntries());
            racePrizeMoneyLabel.setText("Prize Money: $" + race.getRacePrizeMoney());
        }
    }

    public static void updateGameInfo(int money, int racesRemaining, int seasonLength, Label moneyLabel, Label racesRemainingLabel, Label seasonLengthLabel){
        moneyLabel.setText("Money: $" + money);
        racesRemainingLabel.setText("Races Remaining: " + racesRemaining);
        seasonLengthLabel.setText("Season Length: " + seasonLength);
    }

    public static void updateRouteStats(Route route, Label routeDescriptionLabel, Label routeDistanceLabel, Label routeFuelStopsLabel, Label routeDifficultyLabel) {
        if (route == null) {
            routeDescriptionLabel.setText("Description: ");
            routeDistanceLabel.setText("Distance: ");
            routeFuelStopsLabel.setText("Fuel Stops: ");
            routeDifficultyLabel.setText("Difficulty: ");
        } else {
            routeDescriptionLabel.setText("Description: " + route.getRouteDescription());
            routeDistanceLabel.setText("Distance: " + route.getRouteDistance() + "km");
            routeFuelStopsLabel.setText("Fuel Stops: " + route.getRouteFuelStops());
            routeDifficultyLabel.setText("Difficulty: " + String.format("%.2f", route.getRouteDifficultyMultiplier()));
        }
    }

    public static void updateRouteButtons(List<Button> availableRouteButtons, List<Route> availableRoutes) {
        for (int i = 0; i < availableRouteButtons.size(); i++) {
            if (i < availableRoutes.size()) {
                availableRouteButtons.get(i).setText(availableRoutes.get(i).getRouteDescription().toString());
            } else {
                availableRouteButtons.get(i).setText("");
            }
        }
    }

    public static void updateUpgradeButtons(List<Button> buttons, List<Upgrade> upgrades){
        for (int i = 0; i < buttons.size(); i++){
            if (i < upgrades.size()){
                buttons.get(i).setText(upgrades.get(i).getUpgradeName());
            } else {
                buttons.get(i).setText("");
            }
        }
    }
}
