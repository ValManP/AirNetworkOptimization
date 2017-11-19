package impl.entities;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private double overallCost;
    private List<Aircraft> aircraft;

    public Company() {
        overallCost = 0;
        aircraft = new ArrayList<>();
    }

    public void addAircraft(Aircraft aircraft) {
        overallCost += aircraft.getCost();
        this.aircraft.add(aircraft);
    }

    public List<Aircraft> getAircraft() {
        return aircraft;
    }

    public double getOverallCost() {
        return overallCost;
    }
}
