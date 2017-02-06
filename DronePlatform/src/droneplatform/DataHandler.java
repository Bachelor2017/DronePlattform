package droneplatform;

import java.util.Arrays;

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
        batteryStation1 = new BatteryStation();
        batteryStation2 = new BatteryStation();
        batteryStation1.setBatteryStationLocation(100, 10, 10);
        batteryStation2.setBatteryStationLocation(50, 50, 50);
        System.out.println("location battery 1: " + Arrays.toString(batteryStation1.getBatteryStationLocation()));
        System.out.println("location battery 2: " + Arrays.toString(batteryStation2.getBatteryStationLocation()));
    }

}
