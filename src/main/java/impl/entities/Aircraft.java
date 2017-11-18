package impl.entities;

public class Aircraft {
    private double cost;
    private double power;

    public Aircraft(double cost, double power) {
        this.cost = cost;
        this.power = power;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }
}
