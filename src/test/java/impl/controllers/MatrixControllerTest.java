package impl.controllers;

import impl.TestAppConfig;
import impl.entities.Aircraft;
import impl.entities.City;
import impl.entities.Network;
import impl.entities.Route;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestAppConfig.class})
public class MatrixControllerTest {
    @Autowired
    private MatrixController matrixController;
    @Autowired
    private NetworkController networkController;

    @Test
    public void test_CalculateCost() throws Exception {
        // Arrange
        City city = Mockito.mock(City.class);
        Aircraft aircraft1 = new Aircraft(100, 20);
        Aircraft aircraft2 = new Aircraft(200, 20);
        Route route1 = new Route(city, city, aircraft1);
        Route route2 = new Route(city, city, aircraft2);

        // Act
        double actualCost = matrixController.calculateCost(Arrays.asList(route1, route2));

        // Assert
        double expectedCost = 300;
        Assert.assertEquals(expectedCost, actualCost, 0.001);
    }

    @Test
    public void test_CalculateEstradaCoeff() throws Exception {
        // Arrange
        int size = 3;
        Aircraft aircraft = new Aircraft(100, 1);
        City moscowCity = new City("Moscow", 0, 0);
        City londonCity = new City("London", 200, 100);
        City berlinCity = new City("Berlin", 100, 200);

        Network network = networkController.createNetwork(size, Arrays.asList(moscowCity, londonCity, berlinCity));
        networkController.addRoute(network, new Route(moscowCity, londonCity, aircraft), true);
        networkController.addRoute(network, new Route(berlinCity, londonCity, aircraft), true);

        // Act
        double actualEstradaCoeff = matrixController.calculateEstradaCoeff(network);

        // Assert
        double expectedEstradaCoeff = 1;
        Assert.assertEquals(expectedEstradaCoeff, actualEstradaCoeff, 0.001);
    }

}