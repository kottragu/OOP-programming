import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Tests {

    @Test
    public void test1() {
        TransportManager transportManager= new TransportManager();
        transportManager.setDistance(1000.0);
        transportManager.createCamel();
        transportManager.createBoots();
        transportManager.createBroom();
        transportManager.createCentaur();
        transportManager.createMagicCarpet();
        transportManager.createSpeedCamel();
        transportManager.createStupa();
        Throwable exception = assertThrows(Exception.class, transportManager::runGroundRace);
        assertEquals("Invalid type of transport to ground", exception.getMessage());
    }

    @Test
    public void test2() throws Exception {
        TransportManager transportManager= new TransportManager();
        transportManager.setDistance(1000.0);
        transportManager.createCamel();
        transportManager.createBoots();
        transportManager.createBroom();
        transportManager.createCentaur();
        transportManager.createMagicCarpet();
        transportManager.createSpeedCamel();
        transportManager.createStupa();
        assertEquals(transportManager.runRace(),"верблюд-быстроход");
    }

    @Test
    public void test3() throws Exception {
        TransportManager transportManager= new TransportManager();
        transportManager.setDistance(10000.0);
        transportManager.createBroom();
        transportManager.createMagicCarpet();
        transportManager.createStupa();
        assertEquals(transportManager.runRace(),"ковёр-самолёт");
    }
}