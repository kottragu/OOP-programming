package Transports.AirTransports;
import vehicle.AirTransport;

import java.util.ArrayList;

public class Broom extends AirTransport {

    public Broom() {
        super( "метла", 20,
                new ArrayList<Double>() {{
                    add(0.01); //переделать?, тк последвательно спадает
                }});
    }

    @Override
    public double getTime(double dist) {
        double пролёт = 0;
        boolean flag = false;
        int i = (int)(dist/1000);
        if (i*1000 < dist) {
            flag = true;
        }
        for (int j=0; j <= i; j++) {
            пролёт+= j*10;
        }
        if (flag) {
            double second = dist - i*1000;
            пролёт += second * (i+1) / 100;
        }
        return (dist-пролёт)/super.getSpeed();
    }
}
