package impl.controllers;

import impl.entities.Aircraft;
import impl.entities.City;
import impl.entities.Company;
import impl.entities.Route;

public class BusinessController {
    public Company createCompany() {
        return new Company();
    }

    public Aircraft createAircraft(Company company, double cost, double power) {
        Aircraft aircraft = new Aircraft(cost, power);
        company.addAircraft(aircraft);
        return aircraft;
    }

    public Route createRoute(City cityA, City cityB, Aircraft aircraft) {
        return new Route(cityA, cityB, aircraft);
    }

    public City createCity(String name, double x, double y) {
        return new City(name, x, y);
    }
}
