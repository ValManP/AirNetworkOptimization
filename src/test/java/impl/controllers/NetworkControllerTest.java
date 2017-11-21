package impl.controllers;

import impl.entities.Aircraft;
import impl.entities.City;
import impl.entities.Network;
import impl.entities.Route;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NetworkControllerTest {
    private NetworkController networkController;

    @Before
    public void setUp() throws Exception {
        this.networkController = new NetworkController();
    }

    @Test
    public void test_CreateNetwork() throws Exception {
        // Arrange
        int size = 3;
        List<City> cities = new ArrayList<>();
        cities.add(new City("Moscow", 0, 0));
        cities.add(new City("London", 200, 100));
        cities.add(new City("Paris", 100, 200));

        // Act
        Network network = networkController.createNetwork(size, cities);

        // Assert
        Assert.assertNotNull(network);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_tryCreateIncorrectNetwork() throws Exception {
        // Arrange
        int size = 3;
        List<City> cities = new ArrayList<>();
        cities.add(new City("Moscow", 0, 0));

        // Act & Assert
        networkController.createNetwork(size, cities);
    }

    @Test
    public void test_AddCity() throws Exception {
        // Arrange
        int newSize = 4;
        Network network = createTestNetwork();
        City newCity = new City("Dubai", -300, 200);

        // Act
        networkController.addCity(network, newCity);

        // Assert
        Assert.assertEquals(newSize, network.getSize());
    }

    @Test
    public void test_AddRoute() throws Exception {
        // Arrange
        Network network = createTestNetwork();
        Aircraft aircraft = new Aircraft(100, 1);
        Route newRoute = new Route(network.getCities().get(0), network.getCities().get(1), aircraft);

        // Act
        networkController.addRoute(network, newRoute, false);

        // Assert
        Assert.assertEquals(1, network.getAdjacencyMatrix().get(0, 1), 0.001);
    }

    @Test
    public void test_AddDuplexRoute() throws Exception {
        // Arrange
        Network network = createTestNetwork();
        Aircraft aircraft = new Aircraft(100, 1);
        Route newRoute = new Route(network.getCities().get(0), network.getCities().get(1), aircraft);

        // Act
        networkController.addRoute(network, newRoute, true);

        // Assert
        Assert.assertEquals(1, network.getAdjacencyMatrix().get(0, 1), 0.001);
        Assert.assertEquals(1, network.getAdjacencyMatrix().get(1, 0), 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_tryAddIncorrectRoute() throws Exception {
        // Arrange
        Network network = createTestNetwork();
        Aircraft aircraft = new Aircraft(100, 1);
        City unknownCity = new City("Berlin", 232, 232);
        Route newRoute = new Route(network.getCities().get(0), unknownCity, aircraft);

        // Act & Assert
        networkController.addRoute(network, newRoute, false);
    }

    @Test
    public void test_AddRoutes() throws Exception {
        // Arrange
        Network network = createTestNetwork();
        Aircraft aircraft = new Aircraft(100, 1);
        Route newRoute1 = new Route(network.getCities().get(0), network.getCities().get(1), aircraft);
        Route newRoute2 = new Route(network.getCities().get(1), network.getCities().get(2), aircraft);

        // Act
        networkController.addRoutes(network, Arrays.asList(newRoute1, newRoute2));

        // Assert
        Assert.assertEquals(1, network.getAdjacencyMatrix().get(0, 1), 0.001);
        Assert.assertEquals(1, network.getAdjacencyMatrix().get(1, 2), 0.001);
    }

    private Network createTestNetwork() {
        int size = 3;
        List<City> cities = new ArrayList<>();
        cities.add(new City("Moscow", 0, 0));
        cities.add(new City("London", 200, 100));
        cities.add(new City("Paris", 100, 200));

        return networkController.createNetwork(size, cities);
    }

}