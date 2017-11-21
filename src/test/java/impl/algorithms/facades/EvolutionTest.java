package impl.algorithms.facades;

import impl.algorithms.genetics.operators.alterer.types.*;
import impl.controllers.BusinessController;
import impl.controllers.NetworkController;
import impl.entities.City;
import impl.entities.Company;
import impl.entities.Network;
import org.jenetics.Phenotype;
import org.jenetics.TournamentSelector;
import org.jenetics.engine.EvolutionResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class EvolutionTest {
    private Network network;
    private Company company;
    private FitnessTypes fitness;

    private NetworkController networkController;
    private BusinessController businessController;

    @Before
    public void setUp() throws Exception {
        this.networkController = new NetworkController();
        this.businessController = new BusinessController();

        company = businessController.createCompany();
        businessController.createAircraft(company, 100, 1);

        City moscowCity = businessController.createCity("Moscow", 0, 0);
        City londonCity = businessController.createCity("London", 200, 100);
        City berlinCity = businessController.createCity("Berlin", 100, 200);
        network = networkController.createNetwork(3, Arrays.asList(moscowCity, londonCity, berlinCity));

        fitness = FitnessTypes.CONSTANT_WEIGHT_FITNESS;
    }

    @Test
    public void test_canCreateEvolution() throws Exception {
        // Act&Assert
        Assert.assertNotNull(Evolution.create(network, company, fitness));
    }

    @Test
    public void test_canBuildEngine() throws Exception {
        // Arrange
        Evolution evolution = Evolution.create(network, company, fitness);

        // Act
        evolution.builder().buildEngine();

        // Assert
        assertNotNull(evolution.getEngine());
    }

    @Test
    public void test_canAddAlterer() throws Exception {
        // Arrange
        Evolution evolution = Evolution.create(network, company, fitness);
        IAltererType crossover = CrossoverTypes.SINGLE_POINT_CROSSOVER.withProbability(0.4);
        IAltererType mutator = MutatorTypes.MUTATOR.withProbability(0.1);

        // Act
        evolution.builder().alterer(crossover, mutator);

        // Assert
        assertNotNull(evolution.getBuilder().getAlterers());
    }

    @Test
    public void test_canAddSelector() throws Exception {
        // Arrange
        Evolution evolution = Evolution.create(network, company, fitness);
        SelectorTypes selector = SelectorTypes.TOURNAMENT_SELECTOR;

        // Act
        evolution.builder().selector(selector);

        // Assert
        assertTrue(evolution.getBuilder().getSurvivorsSelector() instanceof TournamentSelector);
    }

    @Test
    public void test_canSetInitialPopulation() throws Exception {
        // Arrange
        Evolution evolution = Evolution.create(network, company, fitness);

        // Act
        int populationSize = 100;
        evolution.builder().initialPopulation(populationSize);

        // Assert
        assertEquals(populationSize, evolution.getBuilder().getPopulationSize());
    }

    @Test
    public void test_canCompileBuilder() throws Exception {
        // Arrange
        Evolution evolution = Evolution.create(network, company, fitness);

        // Act
        evolution.builder();

        // Assert
        assertNotNull(evolution.getBuilder());
    }

    @Test
    public void iterate() throws Exception {
        // Arrange
        Evolution evolution = createTestEvolution();

        // Act
        int numOfIteration = 3;
        EvolutionResult evolutionResult = evolution.iterate(numOfIteration);

        // Assert
        assertNotNull(evolutionResult);
    }

    @Test
    public void executors() throws Exception {
        // Arrange
        Evolution evolution = createTestEvolution();

        // Act
        int numOfIteration = 3;
        EvolutionResult evolutionResult = evolution.iterate(numOfIteration);

        // Assert
        assertNotNull(evolutionResult);
    }

    @Test
    public void evolveToBestPhenotype() throws Exception {
        // Arrange
        Evolution evolution = createTestEvolution();

        // Act
        int numOfIteration = 3;
        Phenotype phenotype = evolution.evolveToBestPhenotype(numOfIteration);

        // Assert
        assertNotNull(phenotype);
    }

    @Test
    public void evolve() throws Exception {
        // Arrange
        Evolution evolution = createTestEvolution();

        // Act
        int generations = 3;
        EvolutionResult evolutionResult = evolution.evolve(generations);

        // Assert
        assertNotNull(evolutionResult);
    }

    @Test
    public void evolveBySteadyFitness() throws Exception {
        // Arrange
        Evolution evolution = createTestEvolution();

        // Act
        int steadyFitnessLimit = 3;
        EvolutionResult evolutionResult = evolution.evolveBySteadyFitness(steadyFitnessLimit);

        // Assert
        assertNotNull(evolutionResult);
    }

    private Evolution createTestEvolution() {
        Evolution evolution = Evolution.create(network, company, fitness);

        IAltererType crossover = CrossoverTypes.SINGLE_POINT_CROSSOVER.withProbability(0.4);
        IAltererType mutator = MutatorTypes.MUTATOR.withProbability(0.1);
        SelectorTypes selector = SelectorTypes.TOURNAMENT_SELECTOR;
        int populationSize = 100;

        return evolution.builder()
                .alterer(crossover, mutator)
                .selector(selector)
                .initialPopulation(populationSize)
                .buildEngine();
    }
}