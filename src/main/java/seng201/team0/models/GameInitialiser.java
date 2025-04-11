package seng201.team0.models;

import javax.naming.InvalidNameException;
import java.util.ArrayList;

public class GameInitialiser {
    private String playerName;
    private int seasonLength;
    private String difficulty;
    private int startingMoney;
    private ArrayList<Car> selectedCars = new ArrayList<>();

    public void selectName(String name) throws InvalidNameException {
        if (name.length() < 3 || name.length() > 15 || !name.matches("[a-zA-Z0-9 ]+")){
            throw new InvalidNameException("Name must be 3-15 characters and must not contain special characters.");
        }
        this.playerName = name;
    }

    public void selectSeasonLength(int length) throws IllegalArgumentException {
        if (length < 5 || length > 15){
            throw new IllegalArgumentException("Season must be between 5 and 15 races");
        }
        this.seasonLength = length;
    }
}
