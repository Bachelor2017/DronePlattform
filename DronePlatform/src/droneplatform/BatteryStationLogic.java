package droneplatform;

import java.util.ArrayList;
import java.util.TimerTask;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 *
 */
public class BatteryStationLogic {

    private ArrayList<BatteryStation> batteries;
    private BatteryStation battery;
    private byte[] batteriStationLocation;

    private int batteryStationTemperature;
    private int timeToMaxChargingLevel;
    private int batteryStationNumberPossition;
    private int batteryStationYPossition;
    private int batteryStationChargingLevel;
    private byte[] dataFromArduino;
    private DataHandler dh;

    ///
    private java.util.Timer timer;
    private TimerTask tTask;
    public int secondsPassed;
    ///

    /**
     * create the batterystationlogic, and fill inn batteries to atrraylist
     */
    public BatteryStationLogic(DataHandler dh) {
        batteries = new ArrayList<>();
        batteryStationNumberPossition = 0;
        this.dh = dh;
        fillList();
        //testing();
    }

    /**
     * fill the batterystation with batterys
     */
    private void fillList() {
        for (int i = 0; i < 16; i++) {
            battery = new BatteryStation(i);
            batteries.add(battery);
        }
    }

    public ArrayList getArrayListBatteries() {
        return batteries;
    }

    public void setBatteriesDocking(int x, boolean value) {
        batteries.get(x).setDocked(value);
    }

    public boolean isBatteriesDocked(int x) {
        return batteries.get(x).isDocked();
    }

    /**
     * find the next battery in line to be used
     *
     * @return returns the number of the location to the next battery
     */
    public int getActiveBatteryPlacement() {
        batteryStationNumberPossition++;
        if (batteryStationNumberPossition > 15) {
            batteryStationNumberPossition = 0;
        }
        return batteryStationNumberPossition;
    }

    /**
     * gives the XYZ location of the batterystation
     *
     * @param x X is the number of the batterystation in the list
     * @return a byte[] where first byte is X-value, second is Y-Value and last
     * i z-Value
     */
    public byte[] getActiveBatteryXYZLocation(int x) {
        this.batteriStationLocation = new byte[3];
        batteriStationLocation = batteries.get(x).getBatteryStationLocation();
        return batteriStationLocation;
    }

    /**
     * get the charging level of the active battery
     *
     * @param x the number of the batterystation
     * @return the int of the charging level in percentage
     */
    public int getActiveBatteryChargingLevel(int x) {
        batteryStationChargingLevel = batteries.get(x).getBatteryLevel();
        return batteryStationChargingLevel;
    }

    /**
     * setting the spesific battery to charging station
     *
     * @param x the number of the batterystation
     */
    public void settBatteryToChargeInStation(int x) {
        batteries.get(x).setDocked(true);
    }

    /**
     * setting the spesific battery to charging station
     *
     * @param x the number of the batterystation
     */
    public void releaseBatteryFromChargeInStation(int x) {
        batteries.get(x).setDocked(false);
    }

    /**
     * get the temperature of active battery
     *
     * @param x the battery in x posistion
     * @return the temperature of battery x
     */
    public int getActiveBatteryTemperature(int x) {
        batteryStationTemperature = batteries.get(x).getTemperature();
        return batteryStationTemperature;
    }

    /**
     * sets the temperature of the battery in station
     *
     * @param x the battery in position x
     * @param temperature the temperature of the battery
     */
    public void setActiveBatteryTemperature(int x, int temperature) {
        batteries.get(x).setTemperature(temperature);
    }

    /**
     * returns the time to max charging level of the battery
     *
     * @param x the battery in possition x
     * @return the timetomax charging level of battery in possition x
     */
    public int getTimeToMaxChargingLevel(int x) {
        timeToMaxChargingLevel = batteries.get(x).returnTimeToMaxBattery();
        return timeToMaxChargingLevel;
    }

    /**
     * sett the time to max charging level of the battery
     *
     * @param x the battery in possition x
     * @param timeToMax the time to max charging level of the battery
     */
    public void setTimeToMaxChargingLevel(int x, int timeToMax) {
        batteries.get(x).setTimeToMaxBattery(timeToMax);
    }

    public void setBatteryChargingPercentage(int x, int percentage) {
        batteries.get(x).setPercentageCharged(percentage);
    }

    public int getBatteryChargingPercentage(int x) {
        return batteries.get(x).getChargedPercentage();
    }

    public void dataFromArduino() {
        dataFromArduino = dh.getDataFromArduino();
        setBatteryChargingPercentage(0, dataFromArduino[1]);
        setActiveBatteryTemperature(0, dataFromArduino[2]);
        setTimeToMaxChargingLevel(0, dataFromArduino[3]);
        //battery2
        setBatteryChargingPercentage(1, dataFromArduino[1]);
        setActiveBatteryTemperature(1, dataFromArduino[2]);
        setTimeToMaxChargingLevel(1, dataFromArduino[3]);
        //battery3
        setBatteryChargingPercentage(2, dataFromArduino[1]);
        setActiveBatteryTemperature(2, dataFromArduino[2]);
        setTimeToMaxChargingLevel(2, dataFromArduino[3]);
        //battery4
        setBatteryChargingPercentage(3, dataFromArduino[1]);
        setActiveBatteryTemperature(3, dataFromArduino[2]);
        setTimeToMaxChargingLevel(3, dataFromArduino[3]);
        //battery5
        setBatteryChargingPercentage(4, dataFromArduino[1]);
        setActiveBatteryTemperature(4, dataFromArduino[2]);
        setTimeToMaxChargingLevel(4, dataFromArduino[3]);
        //battery6
        setBatteryChargingPercentage(5, dataFromArduino[1]);
        setActiveBatteryTemperature(5, dataFromArduino[2]);
        setTimeToMaxChargingLevel(5, dataFromArduino[3]);
        //battery7
        setBatteryChargingPercentage(6, dataFromArduino[1]);
        setActiveBatteryTemperature(6, dataFromArduino[2]);
        setTimeToMaxChargingLevel(6, dataFromArduino[3]);
        //battery8
        setBatteryChargingPercentage(7, dataFromArduino[1]);
        setActiveBatteryTemperature(7, dataFromArduino[2]);
        setTimeToMaxChargingLevel(7, dataFromArduino[3]);
        //battery9
        setBatteryChargingPercentage(8, dataFromArduino[1]);
        setActiveBatteryTemperature(8, dataFromArduino[2]);
        setTimeToMaxChargingLevel(8, dataFromArduino[3]);
        //battery10
        setBatteryChargingPercentage(9, dataFromArduino[1]);
        setActiveBatteryTemperature(9, dataFromArduino[2]);
        setTimeToMaxChargingLevel(9, dataFromArduino[3]);
        //battery11
        setBatteryChargingPercentage(10, dataFromArduino[1]);
        setActiveBatteryTemperature(10, dataFromArduino[2]);
        setTimeToMaxChargingLevel(10, dataFromArduino[3]);
        //battery12
        setBatteryChargingPercentage(11, dataFromArduino[1]);
        setActiveBatteryTemperature(11, dataFromArduino[2]);
        setTimeToMaxChargingLevel(11, dataFromArduino[3]);
        //battery13
        setBatteryChargingPercentage(12, dataFromArduino[1]);
        setActiveBatteryTemperature(12, dataFromArduino[2]);
        setTimeToMaxChargingLevel(12, dataFromArduino[3]);
        //battery14
        setBatteryChargingPercentage(13, dataFromArduino[1]);
        setActiveBatteryTemperature(13, dataFromArduino[2]);
        setTimeToMaxChargingLevel(13, dataFromArduino[3]);
        //battery15
        setBatteryChargingPercentage(14, dataFromArduino[1]);
        setActiveBatteryTemperature(14, dataFromArduino[2]);
        setTimeToMaxChargingLevel(14, dataFromArduino[3]);
        //battery16
        setBatteryChargingPercentage(15, dataFromArduino[1]);
        setActiveBatteryTemperature(15, dataFromArduino[2]);
        setTimeToMaxChargingLevel(15, dataFromArduino[3]);
    }

    ///////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////
    //det under trenger vi ikke etter vi har satt inn i2C
    public int getBatteriesLevel(int x) {
        return batteries.get(x).getBatteryLevel();
    }

    public void setBatteriesLevel(int x, int value) {
        batteries.get(x).setBatteryLevel(value);
    }

    public int getBatteriesStatus(int x) {
        return batteries.get(x).getBatteryStatus();
    }

    public void setBatteriesStatus(int x, int value) {
        batteries.get(x).setBatteryStatus(value);
    }

    /*
    test of setting the batterylevel
     */
    public void test() {
        batteries.get(2).setBatteryLevel(20);
        batteries.get(4).setBatteryLevel(40);
        batteries.get(6).setBatteryLevel(60);
        batteries.get(8).setBatteryLevel(80);
        batteries.get(10).setBatteryLevel(100);
        for (int i = 0; i < 16; i++) {
            System.out.println("Bat Nr: " + i + " " + batteries.get(i).getBatteryLevel());
        }
    }

    /**
     * test function to start charging of several batterys usiong timer
     */
    public void testing() {
        secondsPassed = 0;
        BatteryStation batr1 = new BatteryStation(1);
        batteries.get(0).setDocked(true);
        tTask = new TimerTask() {
            public void run() {

                if (secondsPassed == 5) {
                    batteries.get(0).setDocked(false);
                }
                if (secondsPassed == 8) {
                    batteries.get(0).setDocked(true);
                    batteries.get(1).setDocked(true);
                }
                if (secondsPassed == 12) {
                    batteries.get(0).setDocked(false);
                    batteries.get(1).setDocked(false);
                    batteries.get(2).setDocked(true);
                }
                if (secondsPassed == 15) {
                    batteries.get(3).setDocked(true);
                    batteries.get(4).setDocked(true);
                    batteries.get(5).setDocked(true);
                    batteries.get(6).setDocked(true);
                    batteries.get(7).setDocked(true);
                }
                secondsPassed++;
            }
        };
        timer = new java.util.Timer();
        timer.scheduleAtFixedRate(tTask, 1000, 1000);
    }

}
