package impl.algorithms.genetics.operators.alterer.types;

import org.jenetics.Alterer;

public interface IAltererType {
    double probability = Alterer.DEFAULT_ALTER_PROBABILITY;

    double getProbability();

    IAltererType withProbability(double probability);
}
