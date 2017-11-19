package impl.algorithms.genetics.operators.alterer.fitness;

import impl.entities.Company;
import impl.entities.Network;

public class ConstantWeightFitness extends GeneticFitness {
    public ConstantWeightFitness(Network network, Company company, double weight1) {
        super(network, company);
        this.weight1 = weight1;
        this.weight2 = 1 - weight1;
    }
}
