import engine.Brrrrrrr;
import vehicle.AirTransport;
import vehicle.GroundTransport;
import vehicle.Vehicle;

import java.util.ArrayList;

public class TransportManager {
    private ArrayList<? super Vehicle> transport;
    private Double distance;

    TransportManager() {
        transport = new ArrayList<>();
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public void createCamel() {
        transport.add(new GroundTransport( "двугорбый верблюд", 10, 30,
                new ArrayList<Double>() {{
                    add(5.0);
                    add(8.0);
                }}));
    }

    public void createSpeedCamel() {
        transport.add(new GroundTransport( "верблюд-быстроход", 40, 10,
                new ArrayList<Double>() {{
                    add(5.0);
                    add(6.5);
                    add(8.0);
                }}));
    }

    public void createCentaur() {
        transport.add(new GroundTransport( "кентавр", 15, 8,
                new ArrayList<Double>() {{
                    add(2.0);
                }}));
    }

    public void createBoots() {
        transport.add(new GroundTransport( "ботинки-вездеходы", 6, 60,
                new ArrayList<Double>() {{
                    add(10.0);
                    add(5.0);
                }}));
    }

    public void createMagicCarpet() {
        transport.add(new AirTransport( "ковёр-самолёт", 10,
                new ArrayList<Double>() {{
                    add(0.0);
                    add(0.03);
                    add(0.1);
                    add(0.05);
                }}));
    }

    public void createStupa() {
        transport.add(new AirTransport( "ступа", 8,
                new ArrayList<Double>() {{
                    add(0.06);
                }}));
    }

    public void createBroom() {
        transport.add(new AirTransport( "метла", 20,
                new ArrayList<Double>() {{
                    add(0.01); //переделать?, тк последвательно спадает
                }}));
    }

    private String run(String type) throws Exception {
        if (distance == 0.0D)
            throw new Exception("Distance can't be zero (ноль)");
        Brrrrrrr br = new Brrrrrrr(transport, type, distance);
        return br.run();
    }

    public String runGroundRace() throws Exception {
        return run("ground");
    }

    public String runAirRace() throws Exception{
        return run("air");
    }

    public String runRace() throws Exception {
        return run("all");
    }

}