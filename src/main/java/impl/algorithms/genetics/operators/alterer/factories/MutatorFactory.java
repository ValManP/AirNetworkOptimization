package impl.algorithms.genetics.operators.alterer.factories;

import impl.algorithms.genetics.operators.alterer.types.IAltererType;
import impl.algorithms.genetics.operators.alterer.types.MutatorTypes;
import org.jenetics.AbstractAlterer;
import org.jenetics.Mutator;
import org.jenetics.SwapMutator;

public class MutatorFactory implements IAlterFactory {
    @Override
    public AbstractAlterer createAlterer(IAltererType altererType) {
        AbstractAlterer result = null;
        double probability = altererType.getProbability();

        if (altererType == MutatorTypes.MUTATOR) {
            result = (IAltererType.probability == probability) ? new Mutator() : new Mutator(probability);
        } else if (altererType == MutatorTypes.SWAP_MUTATOR) {
            result = (IAltererType.probability == probability) ? new SwapMutator() : new SwapMutator(probability);
        }

        return result;
    }
}
