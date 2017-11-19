package impl.algorithms.genetics.operators.alterer.factories;

import impl.algorithms.genetics.operators.alterer.fitness.ConstantWeightFitness;
import impl.algorithms.genetics.operators.alterer.fitness.GeneticFitness;
import impl.algorithms.genetics.operators.alterer.fitness.RandomWeightFitness;
import impl.algorithms.genetics.operators.alterer.types.FitnessTypes;
import impl.entities.Company;
import impl.entities.Network;

public class FitnessFactory {
    public static GeneticFitness getFitness(FitnessTypes fitnessType, Network network, Company company) {
        GeneticFitness result = null;
        double fitnessVariable = fitnessType.getFitnessVariable();

        if (fitnessType == FitnessTypes.CONSTANT_WEIGHT_FITNESS) {
            result = new ConstantWeightFitness(network, company, fitnessVariable);
        } else if (fitnessType == FitnessTypes.RANDOM_WEIGHT_FITNESS) {
            result = new RandomWeightFitness(network, company);
        }

        return result;
    }
}
