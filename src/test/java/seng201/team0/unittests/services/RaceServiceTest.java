package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seng201.team0.models.Car;
import seng201.team0.models.Race;
import seng201.team0.services.RaceService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RaceServiceTest {

    private RaceService raceService;

    @BeforeEach
    public void setUp() {
        raceService = new RaceService();
    }

    @Test
    public void testGenerateRandomRaceEasy() {
        Race race = raceService.generateRandomRace("EASY");
        assertNotNull(race);
        assertTrue(!race.getAvailableRoutes().isEmpty() && race.getAvailableRoutes().size() <= 3);
        assertTrue(race.getRaceEntries() >= 3 && race.getRaceEntries() <= 8);
        assertTrue(race.getRaceHours() >= 3 && race.getRaceHours() <= 8);
        assertEquals(race.getRacePrizeMoney(), (race.getRaceEntries() + race.getRaceHours()) * 40);
    }

    @Test
    public void testGenerateRandomRaceHard() {
        Race race = raceService.generateRandomRace("HARD");
        assertNotNull(race);
        assertTrue(!race.getAvailableRoutes().isEmpty() && race.getAvailableRoutes().size() <= 3);
        assertTrue(race.getRaceEntries() >= 3 && race.getRaceEntries() <= 8);
        assertTrue(race.getRaceHours() >= 3 && race.getRaceHours() <= 8);
        assertEquals(race.getRacePrizeMoney(), (race.getRaceEntries() + race.getRaceHours()) * 20);
    }

    @Test
    public void testGenerateRacesCount() {
        List<Race> races = raceService.generateRaces(5, "EASY");
        assertEquals(5, races.size());
        for (Race race : races) {
            assertNotNull(race.getAvailableRoutes());
        }
    }

    @Test
    public void testPreviewMultiplier() {
        Car car = new Car(50, 50, 50, 50, 200, new ArrayList<>());
        Car preview = raceService.previewMultiplier(car, 2.0);
        assertEquals(25, preview.getCarSpeed());
        assertEquals(25, preview.getCarHandling());
        assertEquals(25, preview.getCarReliability());
        assertEquals(25, preview.getCarFuelEconomy());

        assertEquals(50, car.getCarSpeed());
        assertEquals(50, car.getCarHandling());
        assertEquals(50, car.getCarReliability());
        assertEquals(50, car.getCarFuelEconomy());
    }

    @Test
    public void testCopyCar() {
        Car original = new Car(90, 80, 70, 60, 300, new ArrayList<>());
        original.setCarName("Speed");

        Car copy = raceService.copyCar(original);

        assertEquals(original.getCarSpeed(), copy.getCarSpeed());
        assertEquals(original.getCarHandling(), copy.getCarHandling());
        assertEquals(original.getCarReliability(), copy.getCarReliability());
        assertEquals(original.getCarFuelEconomy(), copy.getCarFuelEconomy());
        assertEquals(original.getCarName(), copy.getCarName());
        assertEquals(original.getCarUpgrades(), copy.getCarUpgrades());

        assertNotSame(original, copy);
    }

}