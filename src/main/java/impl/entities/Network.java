package impl.entities;

import Jama.Matrix;

import java.util.ArrayList;
import java.util.List;

public class Network {
    private Matrix adjacencyMatrix;
    private List<City> cities;

    public Network(int size, List<City> cityList) {
        adjacencyMatrix = new Matrix(size, size);
        cities = new ArrayList<>(cityList);
    }

    public int getSize() {
        return cities.size();
    }

    public void addCity(City city) {
        cities.add(city);
    }

    public Matrix getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public void setAdjacencyMatrix(Matrix adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
