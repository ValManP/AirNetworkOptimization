package impl.algorithms.genetics.operators;

import impl.algorithms.genetics.entities.NetworkAllele;
import impl.entities.Company;
import impl.entities.Network;
import org.jenetics.AnyChromosome;
import org.jenetics.AnyGene;
import org.jenetics.Genotype;
import org.jenetics.util.Factory;

public class ChromosomeFactory implements Factory<Genotype<AnyGene<NetworkAllele>>> {
    private final NetworkSupplier supplier;
    private final Company company;

    public ChromosomeFactory(Network network, Company company) {
        this.supplier = new NetworkSupplier(network);
        this.company = company;
    }

    @Override
    public Genotype<AnyGene<NetworkAllele>> newInstance() {
        AnyChromosome<NetworkAllele> chromosome = AnyChromosome.of(supplier, company.getAircraft().size());
        return Genotype.of(chromosome);
    }
}
