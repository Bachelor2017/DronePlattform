package droneplatform;

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
        dataToTeensy = new byte[64];
        dataFromTeensy = new byte[64];
    }

    /**
     * get the data retrieved from the arduino controller
     * @return retuns a byte[] retrieved from the mikrocontroller
     */
    public byte[] getDataFromArduino() {
        return dataFromArduino;
    }
    
    

    /**
     * setting the data from the arduinoController
     * @param newData the data updating the dataFromArduino byte[]
     */
    public void setDataFromArduino(byte[] newData) {
        this.dataFromArduino = newData;
    }

    /**
     * get the data retrieved from the teensy controller
     * @return retuns a byte[] retrieved from the mikrocontroller
     */
    public byte[] getDataFromTeensy() {
        return dataFromTeensy;
    }
    
    /**
     * setting the data from the teensyController
     * @param newData the data updating the dataFromTeensy byte[]
     */
    public void setDataFromTeensy(byte[] newData) {
        this.dataFromTeensy = newData;
    }
    
     /**
     * get the data to be sendt to the teensy controller
     * @return retuns a byte[] to be sendt to the teensy controller
     */
    public byte[] getDataToTeensy() {
        return dataToTeensy;
    }
    
       /**
     * setting the data to the TeensyController
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
}
