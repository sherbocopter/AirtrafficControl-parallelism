package airtrafficcontrol;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author SHerbocopter
 */
public class Airport extends Thread {
    private static final Airport instance = new Airport();
    private Airport() { }
    public static Airport getInstance() {
        return instance;
    }
    
    enum AirportOpType {
        TAKEOFF,
        LAND
    }
    
    private final Lock internalLock = new ReentrantLock(true);
    private final LinkedList<WaitingEntity> waitingQueue = new LinkedList<>();
    private boolean aircraftOnTrack = false;
    private boolean shouldCloseAirport = false;
    
    private final Condition emptyTrack = internalLock.newCondition();
    
    public void simulateDelay(AirportOpType opType) throws Exception {
        int minTime = 0;
        int maxSpan = 0;
        
        switch (opType) {
            case TAKEOFF: {
                minTime = 3000;
                maxSpan = 0;
            } break;
            case LAND: {
                minTime = 6000;
                maxSpan = 0;
            } break;
            default: { }
        }
        
        Thread.sleep((int) (minTime + Math.random() * maxSpan));
    }
    
    public void openOp(AirportOpType opType) throws Exception {
        WaitingEntity waitingEntity = new WaitingEntity(opType);
        
        internalLock.lock();
            waitingQueue.add(waitingEntity);
        internalLock.unlock();

        //waitingEntity.canProceed.await();
        waitingEntity.canProceed.acquire();
    }
    
    public void closeOp() {
        internalLock.lock();
            emptyTrack.signal();
            aircraftOnTrack = false;
        internalLock.unlock();
    }
    
    public void closeAirport() {
        shouldCloseAirport = true;
    }
    
    @Override
    public void run() {
        while (true) {
            if (shouldCloseAirport) {
                break;
            }
            
            if (waitingQueue.isEmpty()) {
                try {
                    Thread.sleep(1);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                continue;
            }
            
            internalLock.lock();
                if (aircraftOnTrack) {
                    try { emptyTrack.await(); }
                    catch (Exception ex) { System.out.println(ex.getMessage()); }
                }
                
                WaitingEntity waitingEntity = waitingQueue.removeFirst();
                //waitingEntity.canProceed.signal();
                aircraftOnTrack = true;
                waitingEntity.canProceed.release();
            internalLock.unlock();
        }
    }
}
