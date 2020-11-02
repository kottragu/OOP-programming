package Transports.AirTransports;

import vehicle.AirTransport;

import java.util.ArrayList;

public class MagicCarpet extends AirTransport {

    public MagicCarpet() {
        super( "ковёр-самолёт", 10,
                new ArrayList<Double>() {{
                    add(0.0);
                    add(0.03);
                    add(0.1);
                    add(0.05);
                }});

    }

    @Override
    public double getTime(double dist) {
        double reduced = 0.0D;
        while (true) {
            if (dist <= 1000) {
                reduced += super.getDistanceReducer().get(super.findIndex(0)) *
                        (dist);
                break;
            } else if (dist > 1000 && dist <= 5000) {
                reduced += super.getDistanceReducer().get(super.findIndex(1)) *
                        (dist - 1000);
                dist = 1000;
            } else if (dist > 5000 && dist <= 10000) {
                reduced += super.getDistanceReducer().get(super.findIndex(2)) *
                        (dist - 5000);
                dist = 5000;
            } else if (dist > 10000) {
                reduced += super.getDistanceReducer().get(super.findIndex(3)) *
                        (dist - 10000);
                dist = 10000;
            }
        }
        return (dist-reduced)/(super.getSpeed());
    }
}
