package engine;

import javafx.util.Pair;
import vehicle.*;

import java.util.ArrayList;

public class Brrrrrrr {
    private ArrayList<? super Vehicle> transport;
    private double distance;
    private String raceType;

    public Brrrrrrr(ArrayList<? super Vehicle> newTransport, String typeOfRace, Double distance) throws Exception {
        transport = newTransport;
        raceType = typeOfRace;
        if (!(checkType(typeOfRace) || typeOfRace.equals("all"))) // не нужно, но пусть будет
            throw new Exception("Invalid type of transport to " + typeOfRace);
        this.distance = distance;
    }

    private boolean checkType(String type) {
        return transport.stream().allMatch(x -> ((Vehicle)x).getType().equals(type));
    }

    private int findIndex(AirTransport airTransport, int index) {
        if (airTransport.getDistanceReducer().size() > index) {
            return index;
        } else {
            return findIndex(airTransport, index-1);
        }
    }

    private Pair<String, Double> theFastestGround() {
        ArrayList<GroundTransport> groundTransports = new ArrayList<>();
        for (int i=0; i < transport.size(); i++) {
            if (transport.get(i).getClass().getName().contains("Transports.GroundTransports")) {
                groundTransports.add((GroundTransport) transport.get(i));
            }
        }
        double самыйБыстрыйЗемля = Double.MAX_VALUE;
        String fastGround = null;
        double tempGround;
        for (GroundTransport ground: groundTransports) {
            tempGround = ground.getTime(distance);
            if (tempGround < самыйБыстрыйЗемля) {
                самыйБыстрыйЗемля = tempGround;
                fastGround = ground.getName();
            }
        }
        return new Pair<>(fastGround,самыйБыстрыйЗемля);
    }

    private Pair<String, Double> theFastestAir() {
        ArrayList<AirTransport> airTransports = new ArrayList<>();
        for (int i=0; i < transport.size(); i++) {
            System.out.println(transport.get(i).getClass().getName());
            if (transport.get(i).getClass().getName().contains("Transports.AirTransports")) {
                airTransports.add((AirTransport) transport.get(i));
            }
        }
        double самыйБыстрыйВоздух = Double.MAX_VALUE;
        String fastAir = null;
        double tempAir;
        if(!airTransports.isEmpty()) {
            for (AirTransport air : airTransports) {
                tempAir = air.getTime(distance);
                if (tempAir < самыйБыстрыйВоздух) {
                    самыйБыстрыйВоздух = tempAir;
                    fastAir = air.getName();
                }
            }
        }
        return new Pair<>(fastAir,самыйБыстрыйВоздух);
    }


    public String brrr() {
        if (raceType.equals("ground")){
            return theFastestGround().getKey();
        } else if (raceType.equals("air")){
            return theFastestAir().getKey();
        } else {
            Pair<String, Double> fastGround = theFastestGround();
            Pair<String, Double> fastAir = theFastestAir();

            if (fastAir.getValue() < fastGround.getValue()) {
                return fastAir.getKey();
            } else if (fastGround.getValue() < fastAir.getValue()) {
                return fastGround.getKey();
            } else
                return "Мир, дружба, жвачка";
        }
    }

}

