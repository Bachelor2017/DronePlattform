package droneplatform;

import java.io.IOException;
import java.util.Arrays;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
 * Creates the new SerialCom class. creates a new Semaphore wich is shared
 * between serial read and send to make sure on is running at the time
 *x
 */
public class SerialComArduino implements Runnable {

    private SerialPort serialPort;
    Semaphore semaphore = new Semaphore(1, true);
    private Thread reader; // reads from arduino
    private Thread sender;  // writes to arduino
    public byte[] dataFromArduino = new byte[23];
    public byte[] dataToArduino = new byte[6];
    DataHandler dataHandler;
    int increment;
    private Thread t;

    /**
     *
     * @param comPort the serialcommunication port
     * @param dataHandler the datahandler
     */
    public SerialComArduino(String comPort, DataHandler dataHandler, Semaphore semaphore) {
        serialPort = new SerialPort(comPort); //"/dev/ttyUSB0"
        connect();
        this.dataHandler = dataHandler;
        this.semaphore = semaphore;
    }

    /**
     * start a new thread containing the batterystationLogic
     */
    public void start() {
        t = new Thread(this, "BatteryStationLogic thread");
        t.start();
    }

    /**
     * Creats and starts the threads read and send.
     */
    public void connect() {
        try {
            if (!serialPort.isOpened()) {
                serialPort.openPort();
                getSerialPort().setParams(9600, 8, 1, 0);
                // reader = new Thread(new SerialReadArduino(this, semaPhore, serialPort, dataHandler));         
                // reader.start();
            }
        } catch (SerialPortException e) {
            System.out.println("No Port Found On: " + System.getProperty("os.name"));
        }
    }

    @Override
    public void run() {
        try {
           
            while (true) {
                
                byte[] data = serialPort.readBytes(1);
               // System.out.println(data[0]);
            
                if (data[0] == -128) {
                    byte[] dataFromArduionoToDH = serialPort.readBytes(176);
                    /*
                    System.out.println("Starting array: ");
                   System.out.println(dataFromArduionoToDH[110]);
                   System.out.println(dataFromArduionoToDH[111]);
                    System.out.println(dataFromArduionoToDH[112]);
                   System.out.println(dataFromArduionoToDH[113]);
                    System.out.println(dataFromArduionoToDH[114]);
                    System.out.println(dataFromArduionoToDH[115]);
                   System.out.println(dataFromArduionoToDH[116]);
                   System.out.println(dataFromArduionoToDH[117]);
                   System.out.println(dataFromArduionoToDH[118]);
                  System.out.println(dataFromArduionoToDH[119]);
                    System.out.println(dataFromArduionoToDH[120]);
*/
                    // increment++;
                    semaphore.acquire();
                    dataHandler.setDataFromArduino(dataFromArduionoToDH);
                    semaphore.release();
            //       System.out.println("Read Arranged " + Arrays.toString(dataFromArduionoToDH));
                 }
            }

           

        } catch (SerialPortException ex) {
            System.out.println("SerialPortException i SerialRead");
        } catch (InterruptedException ex) {
            Logger.getLogger(SerialComArduino.class.getName()).log(Level.SEVERE, null, ex);
        }
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  
    public SerialPort getSerialPort() {
        return this.serialPort;
    }

    public String[] getPortList() {
        String[] portNames = SerialPortList.getPortNames();

        if (portNames.length == 0) {
            System.out.println("There are no serial-ports :( You can use an emulator, such ad VSPE, to create a virtual serial port.");
            System.out.println("Press Enter to exit...");
            try {
                System.in.read();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        for (int i = 0; i < portNames.length; i++) {
            System.out.println(portNames[i]);
        }
        return portNames;
    }

}
