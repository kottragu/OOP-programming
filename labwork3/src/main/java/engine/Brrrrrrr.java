package engine;

import javafx.util.Pair;
import vehicle.AirTransport;
import vehicle.GroundTransport;
import vehicle.Vehicle;

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

    private int findIndex(GroundTransport groundTransport, int index) {
        if (groundTransport.getRestDuration().size() > index){
            return index;
        } else {
            return findIndex(groundTransport, index-1);
        }
    }

    private double calculateDistance(GroundTransport groundTransport, double dist) {
        double temp = 0.0D;
        double time = (dist/groundTransport.getSpeed()); //чистое время, которое он потратит на бег
        temp += time;
        int countOfRest = (int) (time/groundTransport.getRestInterval());
        for (int i=0; i < countOfRest; i++) {
            temp += groundTransport.getRestDuration().get(findIndex(groundTransport, i));
        }
        return temp;
    }

    private int findIndex(AirTransport airTransport, int index) {
        if (airTransport.getDistanceReducer().size() > index) {
            return index;
        } else {
            return findIndex(airTransport, index-1);
        }
    }

    private double calculateDistance(AirTransport airTransport, double dist) {
        if (airTransport.getName().equals("метла")) { // костыли ван лав
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
            return (dist-пролёт)/airTransport.getSpeed();
        } else {
            double reduced = 0.0D;
            while (true) {
                if (dist <= 1000) {
                    reduced += airTransport.getDistanceReducer().get(findIndex(airTransport,0)) *
                            (dist);
                    break;
                } else if (dist > 1000 && dist <= 5000) {
                    reduced += airTransport.getDistanceReducer().get(findIndex(airTransport,1)) *
                            (dist - 1000);
                    dist = 1000;
                } else if (dist > 5000 && dist <= 10000) {
                    reduced += airTransport.getDistanceReducer().get(findIndex(airTransport,2)) *
                            (dist - 5000);
                    dist = 5000;
                } else if (dist > 10000) {
                    reduced += airTransport.getDistanceReducer().get(findIndex(airTransport,3)) *
                            (dist - 10000);
                    dist = 10000;
                }

            }
            return (dist-reduced)/(airTransport.getSpeed());
        }

    }

    private Pair<String, Double> theFastestGround() {
        ArrayList<GroundTransport> groundTransports = new ArrayList<>();
        for (int i=0; i < transport.size(); i++) {
            if (transport.get(i).getClass().getName().equals("vehicle.GroundTransport")) {
                groundTransports.add((GroundTransport) transport.get(i));
            }
        }
        double самыйБыстрыйЗемля = Double.MAX_VALUE;
        String fastGround = null;
        double tempGround;
        for (GroundTransport ground: groundTransports) {
            tempGround = calculateDistance(ground,distance);
            if (tempGround < самыйБыстрыйЗемля) {
                самыйБыстрыйЗемля = tempGround;
                fastGround = ground.getName();
            }
        }
        return new Pair<>(fastGround,самыйБыстрыйЗемля);
    }

    private Pair<String, Double> theFastestAir(){
        ArrayList<AirTransport> airTransports = new ArrayList<>();
        for (int i=0; i < transport.size(); i++) {
            if (transport.get(i).getClass().getName().equals("vehicle.AirTransport")) {
                airTransports.add((AirTransport) transport.get(i));
            }
        }
        double самыйБыстрыйВоздух = Double.MAX_VALUE;
        String fastAir = null;
        double tempAir;
        if(!airTransports.isEmpty()) {
            for (AirTransport air : airTransports) {
                tempAir = calculateDistance(air, distance);
                if (tempAir < самыйБыстрыйВоздух) {
                    самыйБыстрыйВоздух = tempAir;
                    fastAir = air.getName();
                }
            }
        }
        return new Pair<>(fastAir,самыйБыстрыйВоздух);
    }


    public String run() {
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

