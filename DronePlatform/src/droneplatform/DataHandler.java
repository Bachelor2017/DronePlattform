package droneplatform;

import java.util.Arrays;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 *
 */
public class DataHandler {

    private byte[] dataFromArduino;     //The byteArray retrieved from Arduino 
    private byte[] dataToTeensy;        //The byteArray to be sendt to Teensy 
    private byte[] dataFromTeensy;      //The byteArray retrieved from Teensy 

    /// for testing
    private java.util.Timer timer;
    private TimerTask tTask;
    public int secondsPassed;
    /// testing slutt

    public DataHandler() {

        dataFromArduino = new byte[176];
        dataToTeensy = new byte[5];
        dataFromTeensy = new byte[10];
    }

    /**
     * get the data retrieved from the arduino controller
     *
     * @return retuns a byte[] retrieved from the mikrocontroller
     */
    public byte[] getDataFromArduino() {
        return dataFromArduino;
    }

    /**
     * setting the data from the arduinoController
     *
     * @param newData the data updating the dataFromArduino byte[]
     */
    public void setDataFromArduino(byte[] newData) {
        this.dataFromArduino = newData;
    }

    /**
     * get the data retrieved from the teensy controller
     *
     * @return retuns a byte[] retrieved from the mikrocontroller
     */
    public byte[] getDataFromTeensy() {
        return dataFromTeensy;
    }

    /**
     * setting the data from the teensyController
     *
     * @param newData the data updating the dataFromTeensy byte[]
     */
    public void setDataFromTeensy(byte[] newData) {
        this.dataFromTeensy = newData;
        //System.out.println("Data from teensy:" + Arrays.toString(newData));
    }

    /**
     * get the data to be sendt to the teensy controller
     *
     * @return retuns a byte[] to be sendt to the teensy controller
     */
    public byte[] getDataToTeensy() {
        dataToTeensy[0] = (101);   //Flag byte
        dataToTeensy[1] = 1;      //Manuel or auto mode
        //dataToTeensy[2] = 100;   // Manuel motor number
        // dataToTeensy[3] = 1;    // Manuel motor direction  0-reverse , 1, idle, 2 forward
        dataToTeensy[4] = 100;    //Manuel speed

        return dataToTeensy;
    }

    /**
     * setting the data to the TeensyController
     *
     * @param newData the data updating the dataToTeensy byte[]
     */
    public void setDataToTeensy(byte[] newData) {
        this.dataToTeensy = newData;
    }

    ////////////////////////////////////////////////////////////
    ////////////////ALT UNDER BARE TIL TEST. KAN TAS BORT SENERE
    ////////////////////////////////////////////////////////////
    /**
     * starts a timer to change the data to se if GUI reacts
     */
    public void testing() {
        byte[] testByte = new byte[160];
        this.dataFromArduino = testByte;
        // secondsPassed = 0;

        tTask = new TimerTask() {
            public void run() {
                for (int x = 0; x < 15; x++) {

                    dataFromArduino[0] = (byte) x;
                    dataFromArduino[1] = (byte) x;
                    dataFromArduino[2] = (byte) x;
                    dataFromArduino[3] = (byte) x;
                    dataFromArduino[4] = (byte) x;
                    dataFromArduino[5] = (byte) x;
                    dataFromArduino[6] = (byte) x;
                    dataFromArduino[7] = (byte) x;
                    dataFromArduino[8] = (byte) x;
                    dataFromArduino[9] = (byte) x;

                }
            }
        };
        timer = new java.util.Timer();
        timer.scheduleAtFixedRate(tTask, 1000, 1000);
    }

    /**
     * sets anb start array to se if the gui gets the data
     */
    public void settingTestArray() {
        //byte[] testByte = new byte[160];
        // this.dataFromArduino = testByte;
        for (int x = 0; x < 160; x++) {
            dataFromArduino[x] = (byte) x;
        }
    }

    ////////////////////////////FROM GUI////////////////////
   
    /**
     * setting the value of the lift status
     * @param status the status of the lift
     */
    public void motorStatus(int motorNumber,boolean status,int direction) {
        
        //the number of the motor to be activated
        dataToTeensy[2] = (byte) motorNumber;    //Setting engine number
        if (status) {
            System.out.println("lift activated");
            dataToTeensy[3] = (byte) direction;    //Activated up 
        } else if (!status) {
            dataToTeensy[3] = (byte) direction;    // deactivated up
            System.out.println("lift deactivated");
        }

    }

    
}
