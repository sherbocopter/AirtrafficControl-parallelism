package airtrafficcontrol;

import airtrafficcontrol.Airport.AirportOpType;

/**
 *
 * @author SHerbocopter
 */
public class AirTrafficControl {
    public static void main(String[] args) throws Exception {
        Airport airport = Airport.getInstance();
        
        airport.start();
        
        int numAircrafts = 10;
        Aircraft[] ducks = new Aircraft[numAircrafts];
        
        for (int i = 0; i < numAircrafts; ++i) {
            boolean coinToss = Math.random() < 0.5;
            AirportOpType opType;
            if (coinToss) {
                opType = AirportOpType.TAKEOFF;
            }
            else {
                opType = AirportOpType.LAND;
            }
            ducks[i] = new Aircraft(i, opType);
        }
        
        for (int i = 0; i < numAircrafts; ++i) { ducks[i].start(); }
        
        for (int i = 0; i < numAircrafts; ++i) { ducks[i].join(); }
        
        airport.closeAirport();
    }
}
