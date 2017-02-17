package droneplatform;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.concurrent.Semaphore;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

//import jssc.*;
/**
 * Serialcommunikation between Java and microcontroller
 *
 */
public class SerialSend implements Runnable {

    Semaphore semaphore;
    SerialPort serialPort;
    SerialCom serialCom;
    DataHandler dataHandler;
    int increment;

    /**
     * Setting ut the serialCommunication sending thread.
     *
     * @param serialCom the serialcommunicator class
     * @param s The semaphore added to the communication
     * @param sp the serial communication port
     */
    public SerialSend(SerialCom serialCom, Semaphore s, SerialPort sp,DataHandler dataHandler) {
        this.serialCom = serialCom;
        this.semaphore = s;
        this.serialPort = sp;
        this.dataHandler = dataHandler;
    }

    /**
     * run method for thread. aquires semaphore and runs code, and release the
     * code when finished
     */
    public void run() {
        try {
            while (true) {

                byte[] dataToTeensy = new byte[6];
                semaphore.acquire();
                dataToTeensy = dataHandler.getDataToTeensy();
                semaphore.release();
                serialPort.writeBytes(dataToTeensy);
            }
        } catch (SerialPortException ex) {
            System.out.println("SerialPortException i SerialSend");
        } catch (InterruptedException e) {
            System.out.println("InterruptedException i SerialSend");
        }

    }
}
