package droneplatform;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    public SystemLogic(DataHandler dh, Semaphore semaphore) {
        this.dataHandler = dh;
        this.semaphore = semaphore;
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
                int caseNumber = getState();
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
                System.out.println("Case 0");
                break;
            case (5):
                System.out.println("Case 1");
                break;
            case (8):
                System.out.println("Case 2");
                break;
            case (11):
                System.out.println("Case 3");
                break;

        }
    }

    
    
    public int getState() {
        dataFromArduino = dataHandler.getDataFromArduino();
        caseScenario = dataFromArduino[1];
        return caseScenario;
    }

}
