package droneplatform;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * this class does all the logic and calculations
 *
 */
public class SystemLogic implements Runnable {

    private int xValue;
    private int zValue;
    private int yValue;
    private Thread t;
    private DataHandler dataHandler;
    private byte[] dataFromArduino;
    private int caseScenario;
    private Semaphore semaphore;
    private EventStates eventStates;
    boolean value = false;

    public SystemLogic(DataHandler dh, Semaphore semaphore, EventStates eventStates) {
        this.dataHandler = dh;
        this.semaphore = semaphore;
        this.eventStates = eventStates;
    }

    /**
     * start a new thread containing the logic
     */
    public void start() {
        t = new Thread(this, "controller thread");
        t.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                semaphore.acquire();
                switchCases();
                semaphore.release();
            } catch (InterruptedException ex) {
                Logger.getLogger(SystemLogic.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    protected void switchCases() {
        switch (this.getState()) {
            case (0):
                case1();
                //System.out.println("Case 0");
                break;
            case (1):
                case2();
               // System.out.println("Case 1");
                break;
            case (2):
                case3();
                //System.out.println("Case 2");
                break;
            case (3):
                case4();
                //System.out.println("Case 3");
                break;

        }
    }

    /**
     * searc the datafromArduino for case number. can be trigged by limitswitch?
     *
     * @return
     */
    public int getState() {
        dataFromArduino = dataHandler.getDataFromArduino();
        caseScenario = dataFromArduino[1];
        return caseScenario;
    }

    public void case1() {

    }

    public void case2() {

    }

    public void case3() {

    }

    public void case4() {

    }

    public void case5() {

    }

    public void case6() {

    }

}
