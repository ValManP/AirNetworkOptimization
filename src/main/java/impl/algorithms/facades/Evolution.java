package impl.algorithms.facades;

import impl.algorithms.genetics.controllers.GeneticController;
import impl.algorithms.genetics.entities.NetworkAllele;
import impl.algorithms.genetics.operators.alterer.factories.AltererFactory;
import impl.algorithms.genetics.operators.alterer.factories.SelectorFactory;
import impl.algorithms.genetics.operators.alterer.types.FitnessTypes;
import impl.algorithms.genetics.operators.alterer.types.IAltererType;
import impl.algorithms.genetics.operators.alterer.types.SelectorTypes;
import impl.entities.Company;
import impl.entities.Network;
import org.jenetics.AnyGene;
import org.jenetics.Phenotype;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.jenetics.engine.EvolutionResult.toBestEvolutionResult;
import static org.jenetics.engine.EvolutionResult.toBestPhenotype;
import static org.jenetics.engine.limit.byFixedGeneration;
import static org.jenetics.engine.limit.bySteadyFitness;

public class Evolution {
    private Network network;
    private Company company;
    private FitnessTypes fitness;
    private Engine engine;
    private Engine.Builder builder;

    private GeneticController geneticController;

    private Evolution(Network network, Company company, FitnessTypes fitness) {
        this.network = network;
        this.company = company;
        this.fitness = fitness;

        this.geneticController = new GeneticController();
    }

    public static Evolution create(Network network, Company company, FitnessTypes fitness) {
        return new Evolution(network, company, fitness);
    }

    public Evolution buildEngine() {
        engine = builder.build();
        return this;
    }

    public Evolution alterer(IAltererType crossover, IAltererType mutator) {
        builder.alterers(AltererFactory.getAlterer(crossover), AltererFactory.getAlterer(mutator));
        return this;
    }

    public Evolution selector(SelectorTypes selectorType) {
        builder.selector(SelectorFactory.createSelector(selectorType));
        return this;
    }

    public Evolution initialPopulation(int populationSize) {
        builder.populationSize(populationSize);
        return this;
    }

    public Evolution builder() {
        builder = geneticController.compileBuilder(network, company, fitness);
        return this;
    }

    public EvolutionResult iterate(int generations) {
        return geneticController.iterate(engine, generations);
    }

    public Evolution executors(int numberOfThreads) {
        final ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        builder.executor(executor);
        return this;
    }

    public Phenotype evolveToBestPhenotype(int generations) {
        return (Phenotype<AnyGene<NetworkAllele>, Double>)
                geneticController.evolve(engine, byFixedGeneration(generations), toBestPhenotype());
    }

    public EvolutionResult evolve(int generations) {
        return (EvolutionResult<AnyGene<NetworkAllele>, Double>)
                geneticController.evolve(engine, byFixedGeneration(generations), toBestEvolutionResult());
    }

    public EvolutionResult evolveBySteadyFitness(int steadyFitnessLimit) {
        return (EvolutionResult<AnyGene<NetworkAllele>, Double>)
                geneticController.evolve(engine, bySteadyFitness(steadyFitnessLimit), toBestEvolutionResult());
    }

    Engine.Builder getBuilder() {
        return builder;
    }

    Engine getEngine() {
        return engine;
    }
}
