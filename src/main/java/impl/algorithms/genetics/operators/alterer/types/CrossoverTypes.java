package impl.algorithms.genetics.operators.alterer.types;

public enum CrossoverTypes implements IAltererType {
    SINGLE_POINT_CROSSOVER,
    MULTI_POINT_CROSSOVER,
    PARTIALLY_MATCHED_CROSSOVER,
    UNIFORM_CROSSOVER;

    private double probability;

    CrossoverTypes() {
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
