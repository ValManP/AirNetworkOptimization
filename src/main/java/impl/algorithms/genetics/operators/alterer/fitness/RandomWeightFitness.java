package impl.algorithms.genetics.operators.alterer.fitness;

import impl.entities.Company;
import impl.entities.Network;

import java.util.Random;

public class RandomWeightFitness extends GeneticFitness {
    public RandomWeightFitness(Network network, Company company) {
        super(network, company);
    }

    @Override
    protected void calculateWeights() {
        Random random = new Random();
        weight1 = random.nextDouble();
        weight2 = 1 - weight1;
    }
}
