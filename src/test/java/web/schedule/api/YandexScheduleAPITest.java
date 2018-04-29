package web.schedule.api;

import impl.algorithms.facades.Evolution;
import impl.algorithms.genetics.operators.alterer.types.*;
import impl.controllers.BusinessController;
import impl.controllers.NetworkController;
import impl.entities.Aircraft;
import impl.entities.City;
import impl.entities.Company;
import impl.entities.Network;
import org.jenetics.engine.EvolutionResult;
import org.junit.Assert;
import org.junit.Test;
import web.schedule.api.constants.AirportCode;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class YandexScheduleAPITest {
    private Network network;
    private Company company;

    @Test
    public void testGetCountOfFlight() throws ParseException {
        YandexScheduleAPI api = new YandexScheduleAPI();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String companyCode = "U2";
        Date date = df.parse("2018-06-20");
        int count = api.getCountOfFlight(AirportCode.BERLIN, AirportCode.PARIS, companyCode, date);

        int expected = 3;
        Assert.assertEquals(expected, count);
    }

    @Test
    public void largeExperiment() throws ParseException {
        network = createNetwork();

        FitnessTypes fitness = FitnessTypes.CONSTANT_WEIGHT_FITNESS;
        Evolution evolution = Evolution.create(network, company, fitness);

        IAltererType mutator = MutatorTypes.MUTATOR.withProbability(0.2);

        SelectorTypes selector = SelectorTypes.TOURNAMENT_SELECTOR;
        int populationSize = 100;

        double prob = 0.5;
        int generations = 10;
        for (IAltererType cross : CrossoverTypes.values()) {
            System.out.println(cross);
            prob = 0.5;
            for (int i = 0; i <= 5; i++) {
                IAltererType crossover = cross.withProbability(prob);
                evolution = evolution.builder()
                        .alterer(crossover, mutator)
                        .selector(selector)
                        .initialPopulation(populationSize)
                        .buildEngine();

                EvolutionResult result = evolution.evolveBySteadyFitness(generations);
                System.out.println("fit = " + result.getBestPhenotype().getFitness() + " gen= " + result.getGeneration());

                prob += 0.1;
            }
        }
    }

    private Network createNetwork() throws ParseException {
        YandexScheduleAPI api = new YandexScheduleAPI();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String companyCode = "U2";
        Date date = df.parse("2018-06-20");

        NetworkController networkController = new NetworkController();
        BusinessController businessController = new BusinessController();

        company = businessController.createCompany();

        for (int i = 0; i < 10; i++) {
            businessController.createAircraft(company, 230, 1);
        }

        Network network = networkController.createNetwork(0, Collections.emptyList());

        AirportCode[] airportCodes = AirportCode.values();

        for (AirportCode airportCode : airportCodes) {
            networkController.addCity(network, businessController.createCity(airportCode.name(), 0, 0));
        }

        int sum = 0;
        List<City> cities = network.getCities();
        for (int i = 0; i < cities.size() - 1; i++) {
            for (int j = i + 1; j < cities.size(); j++) {
                int power = api.getCountOfFlight(airportCodes[i], airportCodes[j], companyCode, date);
                if (power != 0) {
                    Aircraft aircraft = new Aircraft(power * 230, power);
                    networkController.addRoute(network, businessController.createRoute(cities.get(i), cities.get(j), aircraft), true);
                }
                sum += power;

            }
        }
        System.out.println("sum: " + sum);
        return network;
    }

    private Evolution createTestEvolution() {
        FitnessTypes fitness = FitnessTypes.CONSTANT_WEIGHT_FITNESS;
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