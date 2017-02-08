package droneplatform;

import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 *
 */
public class BatteryStationLogic implements Runnable {

    private ArrayList<BatteryStation> batteries;
    private BatteryStation battery;
    private byte[] batteriStationLocation;
    private final Semaphore semaphore;
    private int batteryStationTemperature;
    private int timeToMaxChargingLevel;
    private int batteryStationNumberPossition;
    private int batteryStationYPossition;
    private int batteryStationChargingLevel;
    private byte[] dataFromArduino;
    private DataHandler dh;
    private Thread t;
    ///
    private java.util.Timer timer;
    private TimerTask tTask;
    public int secondsPassed;
    ///

    /**
     * create the batterystationlogic, and fill inn batteries to atrraylist
     */
    public BatteryStationLogic(DataHandler dh, Semaphore semaphore) {
        this.semaphore = semaphore;
        batteries = new ArrayList<>();
        batteryStationNumberPossition = 0;
        this.dh = dh;
        fillList();
        byte[] dataFromArduino = new byte[10];
        //testing();
    }

    /**
     * start a new thread containing the batterystationLogic
     */
    public void start() {
        t = new Thread(this, "BatteryStationLogic thread");
        t.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                semaphore.acquire();
                dataFromArduino = dh.getDataFromArduino();
                //System.out.println("dette er test");
                setDataFromArduino();
                semaphore.release();
            } catch (InterruptedException ex) {
                Logger.getLogger(BatteryStationLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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

    /**
     * get the complete list of all batterys
     *
     * @return an arraylist of batteryStation
     */
    public ArrayList getArrayListBatteries() {
        return batteries;
    }

    /**
     * setting spesific battery to docking
     *
     * @param x
     * @param value
     */
    public void setBatteriesDocking(int x, boolean value) {
        batteries.get(x).setDocked(value);
    }

    /**
     * checking if battery is in docking station
     *
     * @param x number of the batteryStation
     * @return the boolean value if the battery is docked at station
     */
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

    /**
     * setting the chargingpercentage of the spesific battery
     *
     * @param x the battery number on station
     * @param percentage the percentage of the battery
     */
    public void setBatteryChargingPercentage(int x, int percentage) {
        batteries.get(x).setPercentageCharged(percentage);
    }

    /**
     * get the charging percentage of the battery
     *
     * @param x the battery numbert
     * @return the battery charging presentage
     */
    public int getBatteryChargingPercentage(int x) {
        return batteries.get(x).getChargedPercentage();
    }

    /**
     * gets the charging cychlus amount
     *
     * @param x the battery number
     * @return the cyclus amount in int
     */
    public int getBatteryChargingCycle(int x) {
        return this.batteries.get(x).getNumberOfChargingCycles();
    }

    /**
     * setting the battery charging cychle
     *
     * @param x the number of the battery
     */
    public void setBatteryChargingCycle(int x) {
        this.batteries.get(x).setBatteryCycles(x);
    }

    /**
     * gets the data read from the arduino and adds the information to the
     * spesific battery Adds the temperature, batterycychle,timetomax and
     * percentage information
     */
    public void setDataFromArduino() {
        dataFromArduino = dh.getDataFromArduino();
        int batteryNumber = dataFromArduino[0];
        batteries.get(batteryNumber).setTemperature(dataFromArduino[1]);
        batteries.get(batteryNumber).setBatteryCycles(dataFromArduino[2]);
        batteries.get(batteryNumber).setTimeToMaxBattery(dataFromArduino[3]);
        batteries.get(batteryNumber).setPercentageCharged(dataFromArduino[4]);
            batteries.get(batteryNumber).setTemperature(dataFromArduino[5]);
        batteries.get(batteryNumber).setBatteryCycles(dataFromArduino[6]);
        batteries.get(batteryNumber).setTimeToMaxBattery(dataFromArduino[7]);
        batteries.get(batteryNumber).setPercentageCharged(dataFromArduino[8]);
            batteries.get(batteryNumber).setTemperature(dataFromArduino[9]);
  


    }

    /**
     * gets the data read from the arduino and adds the information to the
     * spesific battery Adds the temperature, batterycychle,timetomax and
     * percentage information
     */
    public void setDataFromArduinoTest() {
        dataFromArduino = dh.getDataFromArduino();
        int batteryNumber = dataFromArduino[0];
        int i = 1;
        for (int x = 0; x < 16; x++) {
            batteries.get(x).setTemperature(dataFromArduino[i]);
            //setActiveBatteryTemperature(x, dataFromArduino[i]);
            i++;
            batteries.get(x).setBatteryCycles(dataFromArduino[i]);
            //setTimeToMaxChargingLevel(x, dataFromArduino[i]);
            i++;
            batteries.get(x).setTimeToMaxBattery(dataFromArduino[i]);
            //setTimeToMaxChargingLevel(x, dataFromArduino[i]);
            i++;
            batteries.get(x).setPercentageCharged(dataFromArduino[i]);
            //setBatteryChargingPercentage(x, dataFromArduino[i]);
            i++;

        }
        i = 0;
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
