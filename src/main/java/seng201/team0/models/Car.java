package seng201.team0.models;

public class Car {
    private int speed;
    private int handling;
    private int reliability; //percentage between 0-100
    private int fuelEconomy;
    private int cost;
    //private ArrayList<Upgrade> upgrades; NEED TO IMPLEMENT UPGRADE CLASS FIRST

    public Car(int speed, int handling, int reliability, int fuelEconomy, int cost) {
        this.speed = speed;
        this.handling = handling;
        this.reliability = reliability;
        this.fuelEconomy = fuelEconomy;
        this.cost = cost;
}
    public int getSpeed() {
        return speed;
    }

    public int getHandling() {
        return handling;
    }

    public int getReliability() {
        return reliability;
    }

    public int getFuelEconomy() {
        return fuelEconomy;
    }

    public int getCost() {
        return cost;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setHandling(int handling) {
        this.handling = handling;
    }

    public void setReliability(int reliability) {
        this.reliability = reliability;
    }

    public void setFuelEconomy(int fuelEconomy) {
        this.fuelEconomy = fuelEconomy;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
