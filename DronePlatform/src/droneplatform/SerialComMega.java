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
 *
 */
public class SerialComMega implements Runnable {

    private SerialPort serialPort;
    Semaphore semaphore = new Semaphore(1, true);
    private DataHandler dataHandler;
    private int increment1;
    private int increment2;
    private Thread t;
    private boolean hasReceived;
    private byte[] oldData = new byte[4];
    byte[] dataFromTeensynoToDH = new byte[15];
    private byte lastInt;
    private byte lastInt2;

    /**
     *
     * @param comPort the serialcommunication port
     * @param dataHandler the datahandler
     */
    public SerialComMega(String comPort, DataHandler dataHandler, Semaphore semaphore) {
        serialPort = new SerialPort(comPort); //"/dev/ttyUSB0"
        connect();
        this.dataHandler = dataHandler;
        this.semaphore = semaphore;
        hasReceived = true;

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
                if (hasReceived) {
                    byte[] dataToTeensy = new byte[8];
                    
                    semaphore.acquire();
                    dataToTeensy = dataHandler.getDataToMega();
                    //boolean flag = dataHandler.isMessageDeliveredToMega();
                    semaphore.release();
                   //System.out.println("Read Arranged MEGA " + Arrays.toString(dataToTeensy));
                    increment1++;
                    /*
                    String stringToSend = "<";
                    for(int i = 0; i<dataToTeensy.length; i++){
                        stringToSend = (stringToSend + (dataToTeensy[i]) + ",");
                        if(dataToTeensy[7] != 0){
                            System.out.println(dataToTeensy[7]);
                        }
                    }
                    stringToSend = stringToSend + ">";
*/
                   // if(!flag){
                    serialPort.writeBytes(dataToTeensy);
                    //dataHandler.setMessageDeliveredToMega(true);
                    //serialPort.writeString(stringToSend);
                  // System.out.println(" sent: " + stringToSend);
                
                    
                    hasReceived = false;
                    //}
                }
                

                if (!hasReceived) {
                    
                    byte[] data = serialPort.readBytes(1);
                    //System.out.print("From MEGA: " + Arrays.toString(data));
                    if (data[0] == 101) {
                        
                        byte[] dataFromTeensynoToDH = new byte[20];
                        
                        dataFromTeensynoToDH = serialPort.readBytes(15);
                      
                    
                     //   System.out.println("Read Arranged MEGA " + Arrays.toString(dataFromTeensynoToDH));
                    
                        semaphore.acquire();
                        dataHandler.setDataFromMega(dataFromTeensynoToDH);
                        semaphore.release();
                        
                       
                        
                       // System.out.println(" Recieved: " +dataFromTeensynoToDH[0] + ", "+dataFromTeensynoToDH[1] + ", "+dataFromTeensynoToDH[2] + ", "+dataFromTeensynoToDH[3] + ", "+dataFromTeensynoToDH[4] + ", "+dataFromTeensynoToDH[5] + ", "+dataFromTeensynoToDH[6] + ", "+dataFromTeensynoToDH[7] + ", "+dataFromTeensynoToDH[8] + ", " );
                     
                       
                        if(dataFromTeensynoToDH[14]!= lastInt) {
                            System.out.println(" debugnumber = " +dataFromTeensynoToDH[14]);
                            lastInt = dataFromTeensynoToDH[14];
                   //     System.out.println("Send Arranged MEGA" + Arrays.toString(dataFromTeensynoToDH));
                        }
                         if(dataFromTeensynoToDH[12]!= lastInt2) {
                            System.out.println(" ErrorState = " +dataFromTeensynoToDH[12]);
                            lastInt2 = dataFromTeensynoToDH[12];
                   //     System.out.println("Send Arranged MEGA" + Arrays.toString(dataFromTeensynoToDH));
                        }
                         
                        
                     

                        hasReceived = true;
                       

                    }
                 
                   // System.out.println(data[0]);

                //
                
        
                }

            }

        } catch (SerialPortException ex) {
            System.out.println("SerialPortException i SerialRead");
        } catch (InterruptedException ex) {
            Logger.getLogger(SerialComMega.class.getName()).log(Level.SEVERE, null, ex);
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

    private void makeString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
