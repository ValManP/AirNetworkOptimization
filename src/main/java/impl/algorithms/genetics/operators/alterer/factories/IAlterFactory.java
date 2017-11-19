package impl.algorithms.genetics.operators.alterer.factories;

import impl.algorithms.genetics.operators.alterer.types.IAltererType;
import org.jenetics.AbstractAlterer;
import org.jenetics.Gene;

public interface IAlterFactory<G extends Gene<?, G>, C extends Comparable<? super C>> {
    AbstractAlterer<G, C> createAlterer(IAltererType altererType);
}
