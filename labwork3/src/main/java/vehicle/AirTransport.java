package vehicle;

import java.util.ArrayList;

public class AirTransport extends Vehicle {
    private final double speed;
    private final ArrayList<Double> distanceReducer;

    public AirTransport(String name, double speed, ArrayList<Double> distanceReducer) {
        super("air", name);
        this.speed = speed;
        this.distanceReducer = distanceReducer;
    }

    public double getSpeed() {
        return speed;
    }

    public String getName() {
        return super.name;
    }


    public ArrayList<Double> getDistanceReducer() {
        return distanceReducer;
    }
}