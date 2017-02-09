/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package droneplatform;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
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

        DataHandler dataHandler = new DataHandler();

        GUI gui = new GUI();
        gui.setVisible(true);
        //
        //GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //GraphicsDevice screen = ge.getDefaultScreenDevice();
        //screen.setFullScreenWindow(gui);
//

        Semaphore semaphore = new Semaphore(1, true);
        BatteryStationLogic bsg = new BatteryStationLogic(dataHandler, semaphore);
        bsg.start();

        FaultHandler faultHandler = new FaultHandler();
        GUIObservable observable = new GUIObservable(faultHandler, bsg);

        observable.addObserver(gui);

        faultHandler.testing();
        //SystemLogic sysLog = new SystemLogic(dataHandler,semaphore);
        //sysLog.start();
        //Serial Communication
        //serialCom = new SerialCom("/dev/ttyUSB0", dataHandler);
        //serialCom = new SerialCom("COM3", this);
        //serialCom.connect();
        SerialComArduino serialComArduino = new SerialComArduino("COM3", dataHandler, semaphore);
        serialComArduino.start();

        while (true) {
            observable.setData();
//            faultHandler.testing();
        }

    }

}
