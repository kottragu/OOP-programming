import Transports.AirTransports.Broom;
import Transports.AirTransports.MagicCarpet;
import Transports.AirTransports.Stupa;
import Transports.GroundTransports.Boots;
import Transports.GroundTransports.Camel;
import Transports.GroundTransports.Centaur;
import Transports.GroundTransports.SpeedCamel;
import engine.Brrrrrrr;
import vehicle.Vehicle;

import java.util.ArrayList;

public class TransportManager {
    private ArrayList<? super Vehicle> transports;
    private Double distance;

    TransportManager() {
        transports = new ArrayList<>();
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public void createCamel() {
        transports.add(new Camel());
    }

    public void createSpeedCamel() {
        transports.add(new SpeedCamel());
    }

    public void createCentaur() {
        transports.add(new Centaur());
    }

    public void createBoots() {
        transports.add(new Boots());
    }

    public void createMagicCarpet() {
        transports.add(new MagicCarpet());
    }

    public void createStupa() {
        transports.add(new Stupa());
    }


    public void createBroom() {
        transports.add(new Broom());
    }

    private String run(String type) throws Exception {
        if (distance == 0.0D)
            throw new Exception("Distance can't be zero (ноль)");
        Brrrrrrr go = new Brrrrrrr(transports, type, distance);
        return go.brrr();
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