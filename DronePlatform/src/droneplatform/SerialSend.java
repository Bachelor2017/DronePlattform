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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
     * @param serialCom the serialcommunicator class
     * @param s The semaphore added to the communication
     * @param sp  the serial communication port 
     */
    public SerialSend(SerialCom serialCom, Semaphore s, SerialPort sp) {
        this.serialCom = serialCom;
        this.semaphore = s;
        this.serialPort = sp;
        dataHandler = new DataHandler();
    }

    
    /**
     * run method for thread. aquires semaphore and runs code, 
     * and release the code when finished
     */
    public void run() {
        try {
            while (true) {
                semaphore.acquire();
                increment++;
                //this.dataToArduino = serialCom.sendDataToArduino();
                byte[] dataToArduino = new byte[6];
                dataToArduino = serialCom.sendDataToArduino();
                //System.out.println("dataHandler data: " + Arrays.toString(serialCom.sendDataToArduino()));
//                dataToArduino = this.dataHandler.dataToArduino();
               System.out.println("SEND Serial " + Arrays.toString(dataToArduino));
                System.out.println("Sent:" + increment);
                serialPort.writeBytes(dataToArduino);

                semaphore.release();
            }
        } catch (SerialPortException ex) {
            System.out.println("SerialPortException i SerialSend");
        } catch (InterruptedException e) {
            System.out.println("InterruptedException i SerialSend");
        }

    }
}
