package impl.algorithms.genetics.controllers;

import impl.algorithms.genetics.entities.NetworkAllele;
import impl.controllers.BusinessController;
import impl.controllers.NetworkController;
import impl.entities.*;
import org.jenetics.AnyGene;
import org.jenetics.Chromosome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GeneticController {
    @Autowired
    private NetworkController networkController;
    @Autowired
    private BusinessController businessController;

    public Network getUpdatedNetwork(Network network, List<Route> routes) {
        Network updatedNetwork = networkController.createNetwork(network.getSize(), network.getCities());
        networkController.addRoutes(updatedNetwork, routes);
        return updatedNetwork;
    }

    public List<Route> getListOfRoutes(Network network, Company company, Chromosome<AnyGene<NetworkAllele>> chromosome) {
        List<Route> routes = new ArrayList<>();

        for (int index = 0; index < chromosome.length(); index++) {
            AnyGene<NetworkAllele> gene = chromosome.getGene(index);
            NetworkAllele allele = gene.getAllele();

            Aircraft aircraft = company.getAircraft().get(index);
            City cityA = network.getCities().get(findRow(allele.getPosition(), network.getSize()));
            City cityB = network.getCities().get(findCol(allele.getPosition(), network.getSize()));

            routes.add(businessController.createRoute(cityA, cityB, aircraft));
        }

        return routes;
    }

    private int findRow(int position, int size) {
        int row = -1;
        int temp = position;
        while (temp > 0) {
            row++;
            temp = temp - size + row + 1;
        }

        return row;
    }

    private int findCol(int position, int size) {
        int row = findRow(position, size);
        int temp = position;
        for (int i = 0; i < row; i++) {
            temp += i + 2;
        }

        return temp / size;
    }
}
