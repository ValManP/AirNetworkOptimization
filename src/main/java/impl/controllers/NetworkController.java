package impl.controllers;

import Jama.Matrix;
import impl.entities.City;
import impl.entities.Network;
import impl.entities.Route;
import impl.tools.CommonTools;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class NetworkController {
    private static final Logger LOGGER = Logger.getLogger(NetworkController.class);

    public Network createNetwork(int size, List<City> cityList) {
        if (!CommonTools.isEqual(size, cityList.size())) {
            throw new IllegalArgumentException("Size of lists should be equal to matrix size");
        }
        return new Network(size, cityList);
    }

    public void addCity(Network network, City city) {
        int size = network.getSize();
        Matrix newMatrix = new Matrix(size + 1, size + 1);
        newMatrix.setMatrix(0, size - 1, 0, size - 1, network.getAdjacencyMatrix());
        network.setAdjacencyMatrix(newMatrix);
        network.addCity(city);
    }

    public void addRoute(Network network, Route route, boolean isDuplex) {
        List<City> cityList = network.getCities();
        if (!cityList.containsAll(Arrays.asList(route.getCityA(), route.getCityB()))) {
            throw new IllegalArgumentException();
        }

        int indexA = cityList.indexOf(route.getCityA());
        int indexB = cityList.indexOf(route.getCityB());
        double power = route.getAircraft().getPower();

        network.getAdjacencyMatrix().set(indexA, indexB, power);
        if (isDuplex) {
            network.getAdjacencyMatrix().set(indexB, indexA, power);
        }
    }

    public void addRoutes(Network network, List<Route> routes) {
        routes.forEach(route -> addRoute(network, route, true));
    }
}
