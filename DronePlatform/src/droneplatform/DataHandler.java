package droneplatform;

import java.util.Arrays;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
////////////PROTOCOLLS///////////////
//Data from GUI to TEENSY Controller   - dataToTeensy//
//
//byte[0] - 101    (flagbyte)
//byte[1] - 0-1    (auto/manual mode)
//byte[2] - 1-10   (motor number)
//byte[3] - 0-2    (Motor direction)    0 = idle, 1 = rev , 2 = forward
//byte[4] - 0-100  (speed)
//Data from Teensy controller to GUI   - dataFromTeensy//
//
//byte[0] - 101    (flagbyte)
//byte[1] - 0-1    (auto/manual mode)
//byte[2] - 1-10   (motor number)
//byte[3] - 0-2    (Motor direction)    0 = idle, 1 = rev , 2 = forward
//byte[4] - 0-100  (speed)
/////Data from arduino controller to GUI   - dataFromArduino//
//
//byte[0] - 0-15   (BatteryNumber)
//
//Repeat x16 batteries  total of byte[176]
//byte[1] - 0-100% (Relative charge)
//byte[2] - Voltage
//byte[3] - Voltage descimal
//byte[4] - Minutes remaining to full
//byte[5] - Temperature
//byte[6] - Temperature descimal
//byte[7] - cycle count
//byte[8] - cycle count Descimal
//byte[9] - Battery Status
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
        dataFromTeensy[1] = 1;
        return dataFromTeensy;
    }

    /**
     * setting the data from the teensyController
     *
     * @param newData the data updating the dataFromTeensy byte[]
     */
    public void setDataFromTeensy(byte[] newData) {
        this.dataFromTeensy = newData;

    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////FROM GUI////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    /**
     * Setting the value of the spesific stepper engine
     *
     * @param motorNumber the number of the motor (1-5)
     * @param status The status of the motor (true/false)
     * @param direction The direction/funcion of the engine (int:0=idle, 1 =
     * rev, 2=forward)
     */
    public void motorStatus(int motorNumber, boolean status, int direction) {
        int modeNumber = dataToTeensy[1];
        if (modeNumber == 1) {

            //the number of the motor to be activated
            dataToTeensy[2] = (byte) motorNumber;    //Setting engine number to the bytearray
            if (status) {
                System.out.println("lift activated");
                dataToTeensy[3] = (byte) direction;    //setting the direction to byte 3 in  the bytearray
            } else if (!status) {
                dataToTeensy[3] = (byte) direction;
                System.out.println("lift deactivated");
            }
        } else {

        }

        System.out.println(Arrays.toString(dataToTeensy));
    }

    /**
     * Setting the running mode of platform. true = auto , false = manual
     *
     * @param value boolean value , true =auto , false = manual
     */
    public void setPlatformMode(boolean value) {
        if (value) {
            dataToTeensy[1] = 0;      //auto mode
        } else if (!value) {

            dataToTeensy[1] = 1;      //Manuel mode
        }
    }

    /**
     * Setting the speed value of the engine
     *
     * @param speed 0-100, (percentage)
     */
    public void setSpeedValue(int speed) {
        dataToTeensy[4] = (byte) speed;
        System.out.println(Arrays.toString(dataToTeensy));
    }

    /**
     * get the data to be sendt to the teensy controller
     *
     * @return retuns a byte[] to be sendt to the teensy controller
     */
    public byte[] getDataToTeensy() {
        dataToTeensy[0] = (101);   //Flag byte

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

}
