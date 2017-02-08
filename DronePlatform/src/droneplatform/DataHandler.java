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

    ///
    private java.util.Timer timer;
    private TimerTask tTask;
    public int secondsPassed;
    ///

    public DataHandler() {
        //serialCom = new SerialCom("/dev/ttyUSB0", this);
        serialCom = new SerialCom("COM7", this);
        serialCom.connect();
        byte[] dataFromArduino = new byte[64];
        byte[] dataToArduino = new byte[64];
        settTestDataFromArduino();
        testing();   //starts a timer to change the data to se if GUI reacts
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
     * setting a testread from the arduino controller to test the GUI data,
     * should read 1,2,3,4,5->65
     */
    public void settTestDataFromArduino() {
        byte[] testByte = new byte[65];
        this.dataFromArduino = testByte;
        for (int x = 0; x < 65; x++) {
            dataFromArduino[x] = (byte) x;
        }
    }

    /**
     * starts a timer to change the data to se if GUI reacts
     */
    public void testing() {
        secondsPassed = 0;
        tTask = new TimerTask() {
            public void run() {
                for(int x = 0; x<64;x++)
                {
                    dataFromArduino[x] = (byte) secondsPassed;
             
                }

                secondsPassed++;
            }
        };
        timer = new java.util.Timer();
        timer.scheduleAtFixedRate(tTask, 1000, 1000);
    }
}
