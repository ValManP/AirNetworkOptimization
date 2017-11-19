package impl.algorithms.genetics.operators.alterer.types;

public enum MutatorTypes implements IAltererType {
    MUTATOR,
    SWAP_MUTATOR;

    private double probability;

    MutatorTypes() {
        this.probability = IAltererType.probability;
    }

    @Override
    public double getProbability() {
        return this.probability;
    }

    @Override
    public IAltererType withProbability(double probability) {
        this.probability = probability;
        return this;
    }
}
