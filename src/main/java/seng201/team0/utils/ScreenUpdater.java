package seng201.team0.utils;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import seng201.team0.models.Car;
import seng201.team0.models.Upgrade;

import java.util.List;

public class ScreenUpdater {

    public static void updateCarStats(Car car, Label carSpeedLabel, Label carHandlingLabel, Label carReliabilityLabel, Label carFuelEconomyLabel) {
        if (car == null) {
            carSpeedLabel.setText("Speed: ");
            carHandlingLabel.setText("Handling: ");
            carReliabilityLabel.setText("Reliability: ");
            carFuelEconomyLabel.setText("Fuel Economy: ");
        } else {
            carSpeedLabel.setText("Speed: " + car.getCarSpeed());
            carHandlingLabel.setText("Handling: " + car.getCarHandling());
            carReliabilityLabel.setText("Reliability: " + car.getCarReliability());
            carFuelEconomyLabel.setText("Fuel Economy: " + car.getCarFuelEconomy());
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
