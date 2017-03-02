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
    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(1, true);
        DataHandler dataHandler = new DataHandler();
        GUI gui = new GUI();
        gui.setVisible(true);
        gui.setHandler(dataHandler);

        //GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //GraphicsDevice screen = ge.getDefaultScreenDevice();
        //screen.setFullScreenWindow(gui);

        //creating logic classes/threads
        BatteryStationLogic bsg = new BatteryStationLogic(dataHandler, semaphore);
        bsg.start();
        //EventStates events = new EventStates();
        //SystemLogic sysLog = new SystemLogic(dataHandler,semaphore,events);
        //sysLog.start();
         SystemLogic sysLog = new SystemLogic(dataHandler,semaphore);
        sysLog.start();

        FaultHandler faultHandler = new FaultHandler();

        //Adding the observer
        GUIObservable observable = new GUIObservable(faultHandler, bsg, sysLog);
        observable.addObserver(gui);

        //Serial Communication
        //serialCom = new SerialCom("/dev/ttyUSB0", dataHandler);
        //serialCom = new SerialCom("COM3", this);
        //serialCom.connect();
        //SerialComArduino serialComArduino = new SerialComArduino("COM4", dataHandler, semaphore);
        //serialComArduino.start();
    ////////////    SerialComTeensy serialComTeensy = new SerialComTeensy("COM5", dataHandler, semaphore);
    ////////////    serialComTeensy.start();
        //SerialCom serialComTeensy = new SerialCom("COM4",dataHandler);

        while (true) {
            observable.setData();
        }

    }

}
