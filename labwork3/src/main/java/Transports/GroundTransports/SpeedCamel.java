package Transports.GroundTransports;

import vehicle.GroundTransport;
import java.util.ArrayList;

public class SpeedCamel extends GroundTransport {

    public SpeedCamel() {
        super("верблюд-быстроход", 40, 10,
        new ArrayList<Double>() {{
            add(5.0);
            add(6.5);
            add(8.0);
        }});
    }

    public double getTime(double dist) {
        return super.getTime(dist);
    }

}
