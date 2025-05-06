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
    private int fuel;

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
        this.speed = speed;
        this.handling = handling;
        this.reliability = reliability;
        this.fuelEconomy = fuelEconomy;
        this.cost = cost;
        this.id = UUID.randomUUID().toString().toString();
        this.name = name;
        this.fuel = 100;
    }

    public int getCarSpeed() { return speed; }

    public int getCarHandling() { return handling; }

    public int getCarReliability() { return reliability; }

    public int getCarFuelEconomy() { return fuelEconomy; }

    public int getCarCost() { return cost; }

    public String getCarID(){
        return id;
    }

    /**
     * Gets the name of the car.
     * If the name is not set or is blank, returns "Unnamed Car".
     *
     * @return The name of the car, or "Unnamed Car" if none is set.
     */
    public String getCarName(){ return name == null || name.isBlank() ? "Unnamed Car" : name; }

    public int getCarFuel() { return fuel; }

    public void setCarSpeed(int speed) { this.speed = speed; }

    public void setCarHandling(int handling) { this.handling = handling; }

    public void setCarReliability(int reliability) { this.reliability = reliability; }

    public void setCarFuelEconomy(int fuelEconomy) { this.fuelEconomy = fuelEconomy; }

    public void setCarCost(int cost) { this.cost = cost; }

    public void setCarName(String name){
        this.name = name;
    }

    public void setCarFuel(int fuel){
        this.fuel = fuel;
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
        return speed == car.speed &&
                handling == car.handling &&
                reliability == car.reliability &&
                fuelEconomy == car.fuelEconomy &&
                cost == car.cost;
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