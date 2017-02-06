package droneplatform;

import java.util.Arrays;
import java.util.TimerTask;
import java.util.Timer;

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
    private BatteryStation batteryStation1;
    private BatteryStation batteryStation2;
    private BatteryStation batteryStation3;
    private BatteryStation batteryStation4;

    private int secondsPassed;
    private java.util.Timer timer;
    private TimerTask tTask;

    public DataHandler() {

        testBatteryStation();
        serialCom = new SerialCom("/dev/ttyUSB0", this);
        serialCom = new SerialCom("COM7", this);
        //  serialCom.connect();

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

    public void testBatteryStation() {
        batteryStation1 = new BatteryStation(1);
        batteryStation2 = new BatteryStation(2);
        batteryStation3 = new BatteryStation(3);
        batteryStation4 = new BatteryStation(4);
        batteryStation1.setBatteryStationLocation(100, 10, 10);
        batteryStation2.setBatteryStationLocation(50, 50, 50);
        System.out.println("location battery 1: " + Arrays.toString(batteryStation1.getBatteryStationLocation()));
        System.out.println("location battery 2: " + Arrays.toString(batteryStation2.getBatteryStationLocation()));

        batteryStation1.setDocked(true);

        Timer timer = new Timer();
        tTask = new TimerTask() {
            public void run() {
                secondsPassed++;
                //System.out.println("Seconds her Passed: " + secondsPassed);
                if (secondsPassed == 5) {
                    batteryStation1.setDocked(false);
                } else if (secondsPassed == 8) {
                    batteryStation1.setDocked(true);

                } else if (secondsPassed == 10) {
                    batteryStation2.setDocked(true);

                } else if (secondsPassed == 12) {
                    batteryStation1.setDocked(false);
                    batteryStation2.setDocked(false);
                    batteryStation1.setDocked(true);
                    batteryStation2.setDocked(true);
                    batteryStation3.setDocked(true);
                    batteryStation4.setDocked(true);

                } else {

                }
            }
        };
        timer.scheduleAtFixedRate(tTask, 1000, 1000);

    }

}
