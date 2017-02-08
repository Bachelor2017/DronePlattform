package droneplatform;

import java.io.IOException;
import java.util.Arrays;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import java.util.concurrent.Semaphore;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Creates the new SerialCom class. creates a new Semaphore wich is shared
 * between serial read and send to make sure on is running at the time
 *
 */
public class SerialComArduino implements Runnable {

    private SerialPort serialPort;
    Semaphore semaPhore = new Semaphore(1, true);
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
    public SerialComArduino(String comPort, DataHandler dataHandler) {
        serialPort = new SerialPort(comPort); //"/dev/ttyUSB0"
        connect();
        this.dataHandler = dataHandler;
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

    /**
     * get the data retrieved from the arduino controller
     *
     * @return bytearray dataFromArduino
     */
    public byte[] getDataFromArduino() {
        return this.dataFromArduino;
    }

    public void sendDataFromArduinoToDataHandler() {
        dataHandler.setDataFromArduino(dataFromArduino);
    }

    /**
     * The data to be sendt to the microcontroller
     *
     * @return the bytearray to be sendt to the mikrokontroller
     */
    public byte[] sendDataToArduino() {
        byte[] data = new byte[7];
        try {
            data = dataHandler.dataToArduino();

        } catch (NullPointerException e) {

        }
        return data;
    }

    /**
     *
     * @param data
     */
    public void setDataFromArduino(byte[] data) {
        this.dataFromArduino = data;
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

    public Semaphore getSemaphore() {
        return semaPhore;
    }

    @Override
    public void run() {
        try {
            while (true) {
               // semaphore.acquire();
               byte[] data = serialPort.readBytes(1);
               if (data[0]==-128)
               {
                     byte[] data1 = serialPort.readBytes(144);
                increment++;
                //System.out.println("Read Serial " + Arrays.toString(data));
                //if (data.length > 0) {
                   // byte[] arrangedData = checkDataArrangementTest(data);
                   // this.dataArduino = arrangedData;
                   // serialCom.dataFromArduino = arrangedData;
                  //  dataHandler.setDataFromArduino(arrangedData);
                    System.out.println("received: " + increment);
                    System.out.println("Read Arranged " + Arrays.toString(data1));
               // }

             //   semaphore.release();

            }
               }
              
        } catch (SerialPortException ex) {
            System.out.println("SerialPortException i SerialRead");
        }
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
