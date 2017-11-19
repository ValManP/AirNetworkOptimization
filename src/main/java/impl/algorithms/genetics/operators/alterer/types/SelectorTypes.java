package impl.algorithms.genetics.operators.alterer.types;

public enum SelectorTypes {
    ROULETTE_WHEEL_SELECTOR,
    TOURNAMENT_SELECTOR,
    BOLTZMANN_SELECTOR,
    EXPONENTIAL_RANK_SELECTOR,
    LINEAR_RANK_SELECTOR,
    STOCHASTIC_UNIVERSAL_SELECTOR,
    MONTE_CARLO_SELECTOR,
    TRUNCATION_SELECTOR;

    public final static double SELECTION_VARIABLE = -1;
    private double selectionVariable;

    SelectorTypes() {
        this.selectionVariable = SELECTION_VARIABLE;
    }

    public SelectorTypes withSelectionVariable(double selectionVariable) {
        this.selectionVariable = selectionVariable;
        return this;
    }

    public double getSelectionVariable() {
        return selectionVariable;
    }
}
