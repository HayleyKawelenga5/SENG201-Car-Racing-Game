package seng201.team0.models;

import java.util.UUID;
import java.util.concurrent.CancellationException;

/**
 * Represents a car in the game with the attributes speed, handling,
 * reliability, fuel economy, cost, ID and name.
 */
public class Car {
    private int speed;
    private int handling;
    private int reliability;
    private int fuelEconomy;
    private int cost;
    private String id;
    private String name;

    /**
     * Constructor to create a Car object with the specified attributes.
     *
     * @param speed       The speed of the car (1-10).
     * @param handling    The handling ability of the car (1-10).
     * @param reliability The reliability percentage of the car (1-10).
     * @param fuelEconomy The fuel economy of the car (1-10).
     * @param cost        The cost of the car.
     * @param id          The ID of the car.
     * @param name        The name of the car.
     */
    public Car(int speed, int handling, int reliability, int fuelEconomy, int cost) {
        this.id = UUID.randomUUID().toString().toString();
        this.speed = speed;
        this.handling = handling;
        this.reliability = reliability;
        this.fuelEconomy = fuelEconomy;
        this.cost = cost;
    }

    /**
     * Gets the speed of the car.
     *
     * @return The speed of the car.
     */
    public int getSpeed() { return speed; }

    /**
     * Gets the handling of the car.
     *
     * @return The handling of the car.
     */
    public int getHandling() { return handling; }

    /**
     * Gets the reliability of the car.
     *
     * @return The reliability of the car.
     */
    public int getReliability() { return reliability; }

    /**
     * Gets the fuel economy of the car.
     *
     * @return The fuel economy of the car.
     */
    public int getFuelEconomy() { return fuelEconomy; }

    /**
     * Gets the cost of the car.
     *
     * @return The cost of the car.
     */
    public int getCost() { return cost; }

    /**
     * Gets the ID of the car.
     *
     * @return The ID of the car.
     */
    public String getID(){
        return id;
    }

    /**
     * Gets the name of the car.
     * If the name is not set or is blank, returns "Unnamed Car".
     *
     * @return The name of the car, or "Unnamed Car" if none is set.
     */
    public String getName(){ return name == null || name.isBlank() ? "Unnamed Car" : name; }

    /**
     * Sets the speed of the car.
     *
     * @param speed The speed to set.
     */
    public void setSpeed(int speed) { this.speed = speed; }

    /**
     * Sets the handling of the car.
     *
     * @param handling The handling to set.
     */
    public void setHandling(int handling) { this.handling = handling; }

    /**
     * Sets the reliability of the car.
     *
     * @param reliability The reliability to set.
     */
    public void setReliability(int reliability) { this.reliability = reliability; }

    /**
     * Sets the fuel economy of the car.
     *
     * @param fuelEconomy The fuel economy to set.
     */
    public void setFuelEconomy(int fuelEconomy) { this.fuelEconomy = fuelEconomy; }

    /**
     * Sets the cost of the car.
     *
     * @param cost The cost to set.
     */
    public void setCost(int cost) { this.cost = cost; }

    /**
     * Sets the name of the car.
     *
     * @param name The name to set.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Checks if this car is equal to another object.
     * Two cars are equal if they have the same ID.
     *
     * @param obj The object to compare with.
     * @return true if the cars have the same ID, false otherwise.
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
        return id.equals(car.id);
    }

    /**
     * Returns the hash code of this car.
     * Based on the car's unique ID.
     *
     * @return The hash code of the car.
     */
    @Override
    public int hashCode(){
        return id.hashCode();
    }
}