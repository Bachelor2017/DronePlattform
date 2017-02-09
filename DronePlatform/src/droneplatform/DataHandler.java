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

    private SerialCom serialCom;
    private byte[] dataFromArduino;
    private byte[] dataToArduino;
    private BatteryStationLogic batteryStationLogic;
    private SerialComArduino serialComArduino;

    ///
    private java.util.Timer timer;
    private TimerTask tTask;
    public int secondsPassed;
    ///

    public DataHandler() {
   
        dataFromArduino = new byte[160];
        dataToArduino = new byte[64];
       // settingTestArray();
                // settTestDataFromArduino();
        //testing();   //starts a timer to change the data to se if GUI reacts

    }

    /**
     * get the data retrieved from the arduino controller
     *
     * @return retuns a byte[] retrieved fr om the mikrocontroller
     */
    public byte[] getDataFromArduino() {
        return dataFromArduino;
    }

    /**
     * setting the data from the arduinoController
     *
     * @param newData
     */
    public void setDataFromArduino(byte[] newData) {
        this.dataFromArduino = newData;
    }

    public byte[] dataToArduino() {
        byte[] data = new byte[7];
        data[0] = 100;
        data[1] = 2;
        data[2] = 3;
        data[3] = 4;
        data[4] = 5;
        data[5] = 6;
        data[6] = 123;
        return data;
    }

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
