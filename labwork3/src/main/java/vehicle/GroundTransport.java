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

    private int findIndex(int index) {
        if (getRestDuration().size() > index){
            return index;
        } else {
            return findIndex(index-1);
        }
    }

    public double getTime(double dist) {
        double temp = 0.0D;
        double time = (dist/getSpeed()); //чистое время, которое он потратит на бег
        temp += time;
        int countOfRest = (int) (time/getRestInterval());
        for (int i=0; i < countOfRest; i++) {
            temp += getRestDuration().get(findIndex(i));
        }
        return temp;
    }

    public ArrayList<Double> getRestDuration() {
        return restDuration;
    }
}
