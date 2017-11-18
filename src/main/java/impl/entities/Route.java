package impl.entities;

public class Route {
    private City cityA;
    private City cityB;
    private Aircraft aircraft;

    public Route(City cityA, City cityB, Aircraft aircraft) {
        this.cityA = cityA;
        this.cityB = cityB;
        this.aircraft = aircraft;
    }

    public City getCityA() {
        return cityA;
    }

    public void setCityA(City cityA) {
        this.cityA = cityA;
    }

    public City getCityB() {
        return cityB;
    }

    public void setCityB(City cityB) {
        this.cityB = cityB;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }
}
