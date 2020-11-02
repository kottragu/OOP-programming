package Transports.GroundTransports;

import vehicle.GroundTransport;

import java.util.ArrayList;

public class Camel extends GroundTransport {

    public Camel() {
        super("двугорбый верблюд", 10, 30, new ArrayList<>() {{
            add(5.0);
            add(8.0);
        }});
    }

    public double getTime(double dist) {
        return super.getTime(dist);
    }
}
