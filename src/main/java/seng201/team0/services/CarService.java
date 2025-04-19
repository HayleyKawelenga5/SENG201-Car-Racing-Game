package seng201.team0.services;

import seng201.team0.models.Car;

import java.util.ArrayList;
import java.util.Random;

public class CarService {

    private ArrayList<Car> availableCars = new ArrayList<>();
    private ArrayList<Car> selectedCars = new ArrayList<>();
    private int money;

    public CarService(String difficulty) {
        generateRandomCar();
    }

    /**
     * Generates a random car with random stats within the given range.
     * The cost is calculated based on the sum of the car's attributes.
     *
     * @return A new Car object with randomly generated stats.
     */
    public void generateRandomCar() {
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            int speed = random.nextInt(1, 11);
            int handling = random.nextInt(1, 11);
            int reliability = random.nextInt(1, 11);
            int fuelEconomy = random.nextInt(1, 11);
            int cost = (speed + handling + reliability + fuelEconomy) * 10;
            availableCars.add(new Car(speed, handling, reliability, fuelEconomy, cost));
        }
    }

    public ArrayList<Car> getAvailableCars() { return availableCars; }

    public ArrayList<Car> getSelectedCars() { return selectedCars; }

    public int getMoney() { return money; }

    public boolean addCar(Car car) {
        if (!selectedCars.contains(car) && selectedCars.size() < 3) {
            selectedCars.add(car);
            return true;
        }
        return false;
    }

    public void deleteCar(Car car) {
        selectedCars.remove(car);
    }
}
