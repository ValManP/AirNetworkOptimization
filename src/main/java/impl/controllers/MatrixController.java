package impl.controllers;

import Jama.Matrix;
import impl.entities.Network;
import impl.entities.Route;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MatrixController {
    public double calculateCost(List<Route> routes) {
        double cost = 0;
        for (Route route : routes) {
            cost += route.getAircraft().getCost();
        }
        return cost;
    }

    public double calculateEstradaCoeff(Network network) {
        double estradaCoeff;
        double sumOfNegativeExp = 0;
        double sumOfExp = 0;

        Matrix adjacencyMatrix = network.getAdjacencyMatrix();
        double[] eigenvalues = adjacencyMatrix.eig().getRealEigenvalues();

        for (double eigenvalue : eigenvalues) {
            sumOfNegativeExp += Math.exp(-1 * eigenvalue);
            sumOfExp += Math.exp(eigenvalue);
        }

        estradaCoeff = sumOfNegativeExp / sumOfExp;
        return estradaCoeff;
    }
}
