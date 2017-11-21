package impl.algorithms.genetics.controllers;

import impl.algorithms.genetics.entities.NetworkAllele;
import impl.algorithms.genetics.operators.NetworkSupplier;
import impl.controllers.BusinessController;
import impl.controllers.NetworkController;
import impl.entities.*;
import org.jenetics.AnyChromosome;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GeneticControllerTest {
    private GeneticController geneticController;
    private NetworkController networkController;
    private BusinessController businessController;

    private Network network;
    private Company company;
    private Aircraft aircraft;
    private City moscowCity;
    private City londonCity;
    private City berlinCity;

    @Before
    public void setUp() throws Exception {
        this.geneticController = new GeneticController();
        this.networkController = new NetworkController();
        this.businessController = new BusinessController();

        company = businessController.createCompany();
        aircraft = businessController.createAircraft(company, 100, 1);

        moscowCity = businessController.createCity("Moscow", 0, 0);
        londonCity = businessController.createCity("London", 200, 100);
        berlinCity = businessController.createCity("Berlin", 100, 200);
        network = networkController.createNetwork(3, Arrays.asList(moscowCity, londonCity, berlinCity));
    }

    @Test
    public void test_canGetUpdatedNetwork() throws Exception {
        // Arrange
        List<Route> routes = new ArrayList<>();
        routes.add(businessController.createRoute(moscowCity, berlinCity, aircraft));
        routes.add(businessController.createRoute(moscowCity, londonCity, aircraft));

        // Act
        Network updatedNetwork = geneticController.getUpdatedNetwork(network, routes);

        // Assert
        assertNotNull(updatedNetwork);
    }

    @Test
    public void test_canGetListOfRoutes() throws Exception {
        // Arrange
        AnyChromosome<NetworkAllele> chromosome = AnyChromosome.of(new NetworkSupplier(network), company.getAircraft().size());
        NetworkAllele allele = chromosome.getGene().getAllele();
        allele.setUsed(true);
        allele.setPosition(2);

        // Act
        List<Route> routes = geneticController.getListOfRoutes(network, company, chromosome);
        Route route = routes.get(0);

        // Assert
        assertEquals(moscowCity, route.getCityA());
        assertEquals(berlinCity, route.getCityB());
    }

}