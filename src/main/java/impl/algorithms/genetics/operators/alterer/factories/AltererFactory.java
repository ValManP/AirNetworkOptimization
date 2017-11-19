package impl.algorithms.genetics.operators.alterer.factories;

import impl.algorithms.genetics.operators.alterer.types.CrossoverTypes;
import impl.algorithms.genetics.operators.alterer.types.IAltererType;
import impl.algorithms.genetics.operators.alterer.types.MutatorTypes;
import org.jenetics.Alterer;

public class AltererFactory {
    public static Alterer getAlterer(IAltererType type) {
        IAlterFactory alterFactory = getFactory(type);
        return alterFactory.createAlterer(type);
    }

    private static IAlterFactory getFactory(IAltererType altererType) {
        IAlterFactory factory = null;

        if (altererType instanceof CrossoverTypes) {
            factory = new CrossoverFactory();
        } else if (altererType instanceof MutatorTypes) {
            factory = new MutatorFactory();
        }

        return factory;
    }
}
