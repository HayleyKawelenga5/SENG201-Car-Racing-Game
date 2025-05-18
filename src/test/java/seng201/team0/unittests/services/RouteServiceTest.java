package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.Route;
import seng201.team0.models.Route.RouteType;
import seng201.team0.services.RouteService;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

public class RouteServiceTest {

    private RouteService routeService = new RouteService();

    @BeforeEach
    public void setup() { routeService = new RouteService(); }

    @Test
    public void testGetRandomRouteType() {
        for (int i = 0; i < 10; i++) {
            RouteType routeType = routeService.getRandomRouteType();
            assertNotNull(routeType);
            assertTrue(EnumSet.allOf(RouteType.class).contains(routeType));
        }
    }

    @Test
    public void testGenerateRandomRoute() {
        for (int i = 0; i < 10; i++) {
            Route route = routeService.generateRandomRoute();
            assertNotNull(route);

            assertTrue(EnumSet.allOf(RouteType.class).contains(route.getRouteDescription()));

            RouteType type = route.getRouteDescription();
            int distance = route.getRouteDistance();
            double difficulty = route.getRouteDifficultyMultiplier();
            int fuelStops = route.getRouteFuelStops();

            assertTrue(fuelStops >= 1 && fuelStops <= 4);

            switch (type) {
                case FLAT:
                    assertTrue(distance >= 60 && distance <= 150);
                    assertTrue(difficulty >= 1.0 && difficulty <= 1.4);
                    break;
                case HILLY:
                    assertTrue(distance >= 60 && distance <= 150);
                    assertTrue(difficulty >= 1.2 && difficulty <= 1.6);
                    break;
                case OFFROAD:
                    assertTrue(distance >= 60 && distance <= 150);
                    assertTrue(difficulty >= 1.2 && difficulty <= 1.6);
                    break;
                case WINDY:
                    assertTrue(distance >= 60 && distance <= 150);
                    assertTrue(difficulty >= 1.2 && difficulty <= 1.6);
                    break;
                case BEACH:
                    assertTrue(distance >= 60 && distance <= 150);
                    assertTrue(difficulty >= 1.4 && difficulty <= 1.8);
                    break;
            }
        }
    }

}