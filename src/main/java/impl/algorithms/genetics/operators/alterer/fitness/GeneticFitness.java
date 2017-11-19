package impl.algorithms.genetics.operators.alterer.fitness;

import impl.algorithms.genetics.controllers.GeneticController;
import impl.algorithms.genetics.entities.NetworkAllele;
import impl.controllers.MatrixController;
import impl.entities.Company;
import impl.entities.Network;
import impl.entities.Route;
import org.jenetics.AnyGene;
import org.jenetics.Genotype;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class GeneticFitness {
    protected double weight1;
    protected double weight2;

    private Network network;
    private Company company;

    @Autowired
    private MatrixController matrixController;
    @Autowired
    private GeneticController geneticController;

    GeneticFitness(Network network, Company company) {
        this.network = network;
        this.company = company;
    }

    public Double eval(final Genotype<AnyGene<NetworkAllele>> gt) {
        calculateWeights();
        List<Route> routes = geneticController.getListOfRoutes(network, company, gt.getChromosome());
        Network updatedNetwork = geneticController.getUpdatedNetwork(network, routes);

        double estradaCoefficient = matrixController.calculateEstradaCoeff(updatedNetwork);
        double cost = normalizeCost(matrixController.calculateCost(routes));

        return weight1 * estradaCoefficient + weight2 * cost;
    }

    protected void calculateWeights() {
    }

    private double normalizeCost(double cost) {
        return cost / company.getOverallCost();
    }
}
