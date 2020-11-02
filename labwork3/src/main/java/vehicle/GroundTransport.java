package vehicle;

import java.util.ArrayList;

public class GroundTransport extends Vehicle {
    private final double speed;
    private final double restInterval;
    private final ArrayList<Double> restDuration;

    public GroundTransport(String name, double speed, double restInterval, ArrayList<Double> restDuration) {
        super("ground", name);
        this.speed = speed;
        this.restInterval = restInterval;
        this.restDuration = restDuration;
    }

    public double getSpeed() {
        return speed;
    }

    public double getRestInterval() {
        return restInterval;
    }

    public String getName() {
        return super.name;
    }

    public ArrayList<Double> getRestDuration() {
        return restDuration;
    }
}
