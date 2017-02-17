package droneplatform;

import java.util.Arrays;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import java.util.concurrent.Semaphore;

/**
 *
 * her kan det skrives inn test
 */
public class SerialRead implements Runnable {

    SerialCom serialCom;
    Semaphore semaphore;
    SerialPort serialPort;
    public byte[] dataFromArduino = new byte[10];
    int increment;
    DataHandler dataHandler;

    /**
     * Setting ut the serialCommunication reading thread.
     *
     * @param serialCom the serialcommunicator class
     * @param s The semaphore added to the communication
     * @param sp the serial communication port
     */
    public SerialRead(SerialCom serialCom, Semaphore s, SerialPort sp, DataHandler dh) {
        this.semaphore = s;
        this.serialPort = sp;
        this.serialCom = serialCom;
        this.dataHandler = dh;

    }

    /**
     * aquires semaphore, reads data and stors it to bytearray data..
     */
    public void run() {
        try {
            while (true) {
                byte[] flagByte = serialPort.readBytes(1);
                if (flagByte[0] == -128) {
                    byte[] data = serialPort.readBytes(10);
                    semaphore.acquire();
                    dataHandler.setDataFromTeensy(data);
                    semaphore.release();
                }
            }
        } catch (SerialPortException ex) {
            System.out.println("SerialPortException i SerialRead");
        } catch (InterruptedException e) {
            System.out.println("InterruptedException i SerialRead");
        }
    }

}
