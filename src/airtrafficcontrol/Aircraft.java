package airtrafficcontrol;

import airtrafficcontrol.Airport.AirportOpType;
import java.util.Date;

/**
 *
 * @author SHerbocopter
 */
public class Aircraft extends Thread {
    private int id;
    private AirportOpType opType;
    
    public Aircraft(int id, AirportOpType opType) {
        this.id = id;
        this.opType = opType;
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep((long) (100 + Math.random() * 15000));
            
            Airport airport = Airport.getInstance();
            String opString = "";
            
            switch (opType) {
                case TAKEOFF: {
                    opString = "TAKEOFF";
                } break;
                case LAND: {
                    opString = "LAND";
                }
                default: { }
            }
            
            System.out.println(getDate() + " : " + id + "\twants\t\t" + opString);
            airport.openOp(opType);
            System.out.println(getDate() + " : " + id + "\tstarted\t\t" + opString);
            airport.simulateDelay(opType);
            airport.closeOp();
            System.out.println(getDate() + " : " + id + "\tfinished\t" + opString);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private String getDate() {
        return (new Date()).toString();
    }
}
