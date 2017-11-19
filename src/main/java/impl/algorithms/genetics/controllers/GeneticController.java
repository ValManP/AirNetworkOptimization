package impl.algorithms.genetics.controllers;

import impl.algorithms.genetics.entities.NetworkAllele;
import impl.algorithms.genetics.operators.ChromosomeFactory;
import impl.algorithms.genetics.operators.alterer.factories.FitnessFactory;
import impl.algorithms.genetics.operators.alterer.fitness.GeneticFitness;
import impl.algorithms.genetics.operators.alterer.types.FitnessTypes;
import impl.controllers.BusinessController;
import impl.controllers.NetworkController;
import impl.entities.*;
import org.jenetics.AnyGene;
import org.jenetics.Chromosome;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;

@Controller
public class GeneticController {
    @Autowired
    private NetworkController networkController;
    @Autowired
    private BusinessController businessController;

    public Network getUpdatedNetwork(Network network, List<Route> routes) {
        Network updatedNetwork = networkController.createNetwork(network.getSize(), network.getCities());
        networkController.addRoutes(updatedNetwork, routes);
        return updatedNetwork;
    }

    public Engine.Builder compileBuilder(Network network, Company company, FitnessTypes fitness) {
        GeneticFitness fitnessFunction = FitnessFactory.getFitness(fitness, network, company);
        return Engine.builder(fitnessFunction::eval, new ChromosomeFactory(network, company));
    }

    public Object evolve(Engine engine, Predicate limit, Collector collector) {
        return engine
                .stream()
                .limit(limit)
                .collect(collector);
    }

    public EvolutionResult<AnyGene<NetworkAllele>, Double> iterate(Engine engine, int generation) {
        EvolutionResult<AnyGene<NetworkAllele>, Double> result = null;
        Iterator<EvolutionResult<AnyGene<NetworkAllele>, Double>> iterator = engine.iterator();
        for (int i = 0; i < generation; i++) {
            result = iterator.next();
        }
        return result;
    }

    public List<Route> getListOfRoutes(Network network, Company company, Chromosome<AnyGene<NetworkAllele>> chromosome) {
        List<Route> routes = new ArrayList<>();

        for (int index = 0; index < chromosome.length(); index++) {
            AnyGene<NetworkAllele> gene = chromosome.getGene(index);
            NetworkAllele allele = gene.getAllele();

            Aircraft aircraft = company.getAircraft().get(index);
            City cityA = network.getCities().get(findRow(allele.getPosition(), network.getSize()));
            City cityB = network.getCities().get(findCol(allele.getPosition(), network.getSize()));

            routes.add(businessController.createRoute(cityA, cityB, aircraft));
        }

        return routes;
    }

    private int findRow(int position, int size) {
        int row = -1;
        int temp = position;
        while (temp > 0) {
            row++;
            temp = temp - size + row + 1;
        }

        return row;
    }

    private int findCol(int position, int size) {
        int row = findRow(position, size);
        int temp = position;
        for (int i = 0; i < row; i++) {
            temp += i + 2;
        }

        return temp / size;
    }
}
