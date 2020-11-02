package vehicle;

import java.util.ArrayList;

public abstract class AirTransport extends Vehicle {
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

    public abstract double getTime(double dist);

    protected int findIndex(int index) {
        if (getDistanceReducer().size() > index) {
            return index;
        } else {
            return findIndex(index-1);
        }
    }

    public ArrayList<Double> getDistanceReducer() {
        return distanceReducer;
    }
}