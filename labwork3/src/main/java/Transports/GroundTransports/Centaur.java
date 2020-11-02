package Transports.GroundTransports;

import vehicle.GroundTransport;

import java.util.ArrayList;

public class Centaur  extends GroundTransport {
    public Centaur(){
        super("кентавр", 15, 8,
                new ArrayList<Double>() {{
                    add(2.0);
                }});
    }

    public double getTime(double dist) {
        return super.getTime(dist);
    }
}
