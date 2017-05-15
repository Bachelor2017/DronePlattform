/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package droneplatform;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Olav Rune
 */
public class DronePlatform {
    //private SerialComArduino serialComArduino;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {

        Semaphore semaphore = new Semaphore(1, true);
        DataHandler dataHandler = new DataHandler();
        GUI gui = new GUI();
        gui.setVisible(true);
        gui.setHandler(dataHandler);

     
        //creating logic classes/threads
        BatteryStationLogic bsg = new BatteryStationLogic(dataHandler, semaphore);
        bsg.start();
        SystemLogic sysLog = new SystemLogic(dataHandler, semaphore);
        sysLog.start();
        FaultLogic faultLog = new FaultLogic(dataHandler, semaphore);
        faultLog.start();

        //Adding the observer
        GUIObservable observable = new GUIObservable(faultLog, bsg, sysLog,dataHandler,semaphore);
        observable.addObserver(gui);

        
     //Serial Communication batteries
        SerialComArduino serialComArduino = new SerialComArduino("COM7", dataHandler, semaphore);
        serialComArduino.start();
        //Serial Communication stepper controller
        SerialComMega serialComTeensy = new SerialComMega("COM4", dataHandler, semaphore);
        serialComTeensy.start();
       
        while (true) {
            observable.setData();
        }

    }

}
