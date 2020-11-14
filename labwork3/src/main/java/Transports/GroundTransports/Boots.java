package Transports.GroundTransports;

import vehicle.GroundTransport;

import java.util.ArrayList;

public class Boots extends GroundTransport {

    public Boots() {
        super("ботинки-вездеходы", 6, 60,
                new ArrayList<Double>() {{
                    add(10.0);
                    add(5.0);
                }});
    }

    public double getTime(double dist) {
        return super.getTime(dist);
    }
}
