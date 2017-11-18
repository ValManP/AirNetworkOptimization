package impl.entities;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private List<Aircraft> aircraft;

    public Company() {
        aircraft = new ArrayList<>();
    }

    public void addAircraft(Aircraft aircraft) {
        this.aircraft.add(aircraft);
    }

    public List<Aircraft> getAircraft() {
        return aircraft;
    }
}
