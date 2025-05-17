package seng201.team0.models;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a car in the game with the attributes speed, handling,
 * reliability, fuel economy, cost, name and fuel amount.
 */
public class Car {

    private int speed;
    private int handling;
    private int reliability;
    private int fuelEconomy;
    private int cost;
    List<Upgrade> upgrades;
    private String name;
    private int fuelAmount;

    /**
     * Constructor to create a Car object with the specified attributes.
     *
     * @param speed       The average velocity of the car in km/h (20-100).
     * @param handling    The handling ability of the car (20-100).
     * @param reliability The reliability measure of the car (20-100).
     * @param fuelEconomy The fuel economy of the car (20-100).
     * @param cost        The cost of the car.
     * @param upgrades    A list of the tuning parts installed on the car (if any)
     */
    public Car(int speed, int handling, int reliability, int fuelEconomy, int cost, List<Upgrade> upgrades) {
        this.speed = speed;
        this.handling = handling;
        this.reliability = reliability;
        this.fuelEconomy = fuelEconomy;
        this.cost = cost;
        this.upgrades = upgrades;
        this.fuelAmount = fuelEconomy;
    }

    /**
     * Gets the speed of the car in km/h.
     * This is the average velocity achievable on a flat straight track.
     * @return the speed of the car.
     */
    public int getCarSpeed() { return speed; }

    /**
     * Gets the handling ability of the car.
     * This influences how well the car can navigate turns and maintain control.
     * @return the handling ability of the car.
     */
    public int getCarHandling() { return handling; }

    /**
     * Gets the reliability measure of the car as percentage.
     * @return the reliability measure of the car
     */
    public int getCarReliability() { return reliability; }

    /**
     * Gets the car's fuel economy.
     * This is the car's maximum distance in km achievable with a full tank of fuel on a
     * flat straight track before needing to refuel.
     * @return the car's fuel economy
     */
    public int getCarFuelEconomy() { return fuelEconomy; }

    /**
     * Gets the cost of the car.
     * @return the cost of the car
     */
    public int getCarCost() { return cost; }

    /**
     * Gets the upgrades installed on the car.
     * @return the list of upgrades installed on the car
     */
    public List<Upgrade> getCarUpgrades() { return upgrades; }

    /**
     * Gets a comma-separated string describing the upgrades installed on the car.
     * @return a comma-separated list of upgrade names, or "None" if no upgrades installed
     */
    public String getUpgradeDescription(){
        if (upgrades == null || upgrades.isEmpty()) {
            return "None";
        }
        return upgrades.stream()
                .map(Upgrade::getUpgradeName)
                .collect(Collectors.joining(", "));
    }
    /**
     * Gets the name of the car.
     * If the name is not set or is blank, returns "Unnamed Car".
     *
     * @return The name of the car, or "Unnamed Car" if none is set.
     */
    public String getCarName(){ return name == null || name.isBlank() ? "Unnamed Car" : name; }

    /**
     * Gets the car's fuel amount.
     * @return the car's fuel amount
     */
    public int getCarFuelAmount() { return fuelAmount; }

    /**
     * Sets the speed of the car.
     * @param speed speed of the car in km/h to be set
     */
    public void setCarSpeed(int speed) { this.speed = speed; }

    /**
     * Sets the car's handling ability.
     * @param handling handling ability of the car to be set
     */
    public void setCarHandling(int handling) { this.handling = handling; }

    /**
     * Sets the car's reliability.
     * @param reliability reliability percentage to be set
     */
    public void setCarReliability(int reliability) { this.reliability = reliability; }

    /**
     * Sets the car's fuel economy.
     * @param fuelEconomy fuel economy amount to be set
     */
    public void setCarFuelEconomy(int fuelEconomy) { this.fuelEconomy = fuelEconomy; }

    /**
     * Sets the car's cost value.
     * @param cost cost value to be set
     */
    public void setCarCost(int cost) { this.cost = cost; }

    /**
     * Sets the car's upgrades
     * @param upgrade the list of upgrade installed on the car
     */
    public void addToCarUpgrades(Upgrade upgrade) { this.upgrades.add(upgrade);}

    /**
     * Sets the name of the user's car.
     * @param name name to be set
     */
    public void setCarName(String name){
        this.name = name;
    }

    /**
     * Sets the fuel amount for the car.
     * @param fuelAmount fuel amount to be set
     */
    public void setCarFuelAmount(int fuelAmount){
        this.fuelAmount = fuelAmount;
    }

    /**
     * Checks if this car is equal to another object.
     * Two cars are equal if they have the same attributes.
     *
     * @param obj The object to compare with.
     * @return true if the cars have the same attributes, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Car car = (Car) obj;
        return speed == car.speed &&
                handling == car.handling &&
                reliability == car.reliability &&
                fuelEconomy == car.fuelEconomy &&
                cost == car.cost &&
                upgrades == car.upgrades;
    }
}