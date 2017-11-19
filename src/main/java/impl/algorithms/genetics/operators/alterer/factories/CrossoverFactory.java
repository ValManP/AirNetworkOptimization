package impl.algorithms.genetics.operators.alterer.factories;

import impl.algorithms.genetics.operators.alterer.types.CrossoverTypes;
import impl.algorithms.genetics.operators.alterer.types.IAltererType;
import org.jenetics.*;

public class CrossoverFactory implements IAlterFactory {
    @Override
    public AbstractAlterer createAlterer(IAltererType altererType) {
        AbstractAlterer result = null;
        double probability = altererType.getProbability();

        if (altererType == CrossoverTypes.SINGLE_POINT_CROSSOVER) {
            result = (IAltererType.probability == probability) ? new SinglePointCrossover() : new SinglePointCrossover(probability);
        } else if (altererType == CrossoverTypes.MULTI_POINT_CROSSOVER) {
            result = (IAltererType.probability == probability) ? new MultiPointCrossover() : new MultiPointCrossover(probability);
        } else if (altererType == CrossoverTypes.UNIFORM_CROSSOVER) {
            result = (IAltererType.probability == probability) ? new UniformCrossover() : new UniformCrossover(probability);
        } else if (altererType == CrossoverTypes.PARTIALLY_MATCHED_CROSSOVER) {
            result = new PartiallyMatchedCrossover(probability);
        }

        return result;
    }
}
