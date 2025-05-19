package seng201.team0.utils;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import seng201.team0.models.Car;
import seng201.team0.models.Race;
import seng201.team0.models.Route;
import seng201.team0.models.Upgrade;

import java.util.List;

/**
 * Utility class for updating GUI labels and buttons with game-related data.
 * This class contains static methods that help populate and clear GUI elements
 * such as labels and buttons based on {@link Car}, {@link Upgrade}, {@link Race}, and {@link Route} objects.
 */
public class ScreenUpdater {

    /**
     * Updates the car statistics labels with the values from the given {@link Car} object.
     * If the car is {@code null}, clears the labels.
     *
     * @param car                 The car to display stats for. (Type Car)
     * @param carSpeedLabel       Label to display car speed.
     * @param carHandlingLabel    Label to display car handling.
     * @param carReliabilityLabel Label to display car reliability.
     * @param carFuelEconomyLabel Label to display car fuel economy.
     * @param carUpgradesLabel    Label to display installed upgrades.
     */
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

    /**
     * Updates the upgrade statistics labels with values from the given {@link Upgrade} object.
     * If the upgrade is {@code null}, clears the labels.
     *
     * @param upgrade                 The upgrade to display stats for. (Type Upgrade)
     * @param upgradeSpeedLabel       Label to display upgrade speed.
     * @param upgradeHandlingLabel    Label to display upgrade handling.
     * @param upgradeReliabilityLabel Label to display upgrade reliability.
     * @param upgradeFuelEconomyLabel Label to display upgrade fuel economy.
     */
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

    /**
     * Updates the race statistics labels with values from the given {@link Race} object.
     * If the race is {@code null}, clears the labels.
     *
     * @param race                 The race to display stats for. (Type Race)
     * @param raceHoursLabel       Label to display race duration in hours.
     * @param raceRoutesLabel      Label to display associated routes.
     * @param raceEntriesLabel     Label to display number of race entries.
     * @param racePrizeMoneyLabel  Label to display prize money.
     */
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

    /**
     * Updates the general game info such as money, remaining races, and season length.
     *
     * @param money              The current amount of player money. (int)
     * @param racesRemaining     The number of races remaining in the season. (int)
     * @param seasonLength       The total length of the season. (int)
     * @param moneyLabel         Label to display money.
     * @param racesRemainingLabel Label to display races remaining.
     * @param seasonLengthLabel   Label to display season length.
     */
    public static void updateGameInfo(int money, int racesRemaining, int seasonLength, Label moneyLabel, Label racesRemainingLabel, Label seasonLengthLabel){
        moneyLabel.setText("Money: $" + money);
        racesRemainingLabel.setText("Races Remaining: " + racesRemaining);
        seasonLengthLabel.setText("Season Length: " + seasonLength);
    }

    /**
     * Updates the route statistics labels with values from the given {@link Route} object.
     * If the route is {@code null}, clears the labels.
     *
     * @param route                   The route to display stats for. (Type Route)
     * @param routeDescriptionLabel   Label to display route description.
     * @param routeDistanceLabel      Label to display route distance.
     * @param routeFuelStopsLabel     Label to display number of fuel stops.
     * @param routeDifficultyLabel    Label to display route difficulty multiplier.
     */
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

    /**
     * Updates a list of route selection buttons with descriptions of the available {@link Route} objects.
     * If fewer routes are available than buttons, the extra buttons are cleared.
     *
     * @param availableRouteButtons The list of buttons to update. (List<Button>)
     * @param availableRoutes       The list of available routes. (List<Route>)
     */
    public static void updateRouteButtons(List<Button> availableRouteButtons, List<Route> availableRoutes) {
        for (int i = 0; i < availableRouteButtons.size(); i++) {
            if (i < availableRoutes.size()) {
                availableRouteButtons.get(i).setText(availableRoutes.get(i).getRouteDescription().toString());
            } else {
                availableRouteButtons.get(i).setText("");
            }
        }
    }

    /**
     * Updates a list of upgrade selection buttons with the names of the available {@link Upgrade} objects.
     * If fewer upgrades are available than buttons, the extra buttons are cleared.
     *
     * @param buttons  The list of buttons to update. (List<Button>)
     * @param upgrades The list of available upgrades. (List<Upgrade>)
     */
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
