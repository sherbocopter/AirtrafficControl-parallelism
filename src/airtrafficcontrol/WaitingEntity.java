package airtrafficcontrol;

import airtrafficcontrol.Airport.AirportOpType;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author SHerbocopter
 */
public class WaitingEntity {
    public AirportOpType opType;
    //private Lock internalLock = new ReentrantLock(true);
    //public Condition canProceed = internalLock.newCondition();
    public Semaphore canProceed = new Semaphore(0);
    
    public WaitingEntity(AirportOpType opType) {
        this.opType = opType;
    }
}
