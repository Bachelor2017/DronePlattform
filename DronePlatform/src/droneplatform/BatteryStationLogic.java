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
    private float batteryStationTemperature;
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
                semaphore.release();
              //  System.out.println("inne i run metode batteryStationLogic");
                updateBatteryInformation(dataFromArduino);
                

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
    public float getActiveBatteryTemperature(int x) {
        batteryStationTemperature = batteries.get(x).getTemperature();
        return batteryStationTemperature;
    }

    /**
     * sets the temperature of the battery in station
     *
     * @param x the battery in position x
     * @param temperature the temperature of the battery
     */
    public void setActiveBatteryTemperature(int x, float temperature) {
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
    public float getBatteryChargingCycle(int x) {
        return this.batteries.get(x).getNumberOfChargingCycles();
    }

    /**
     * setting the battery charging cychle
     *
     * @param x the number of the battery
     */
    public void setBatteryChargingCycle(int x, float cycles) {
        this.batteries.get(x).setBatteryCycles(cycles);
    }
    
    
    public void setSpesificChargingVoltage(int x, float voltage)
    {
        this.batteries.get(x).setChargingVoltage(voltage);
    }
    
    public float getSpesificChargingVoltage(int x)
    {
        return this.batteries.get(x).getVoltageChargingLevel();
    }

    /**
     * gets the data read from the arduino and adds the information to the
     * spesific battery Adds the temperature, batterycychle,timetomax and
     * percentage information
     */
    public void updateBatteryInformation(byte[] incomingDataFromArduino) {
       // System.out.println("Updating battery information:");
        // dataFromArduino = dh.getDataFromArduino();
        //  int batteryNumber = dataFromArduino[0];
        int i = 1;
        //  float number = 0.0f;
        for (int x = 0; x < 16; x++) {
            //setting percentage of charge
            batteries.get(x).setPercentageCharged(incomingDataFromArduino[i]);
            i = i + 1;

            //Setting the charging voltage
            float voltage = incomingDataFromArduino[i];
            i = i + 1;
            float voltageDesc = incomingDataFromArduino[i];
            i = i + 1;
            batteries.get(x).setChargingVoltage(voltage + (voltageDesc / 100));

            //Setting minutest to fully charged
            batteries.get(x).setTimeToMaxBattery(incomingDataFromArduino[i]);
            //setTimeToMaxChargingLevel(x, dataFromArduino[i]);
            i++;

            //Setting Temperatur on station
            float temperature = incomingDataFromArduino[i];
            i = i + 1;
            float tempDesc = incomingDataFromArduino[i];
            batteries.get(x).setTemperature((temperature + (tempDesc / 100)));
            //setActiveBatteryTemperature(x, dataFromArduino[i]);
            i = i + 1;

            //setting cycle count on batteries
            float cyclecount = incomingDataFromArduino[i];
            i = i + 1;
            float cycleDesc = incomingDataFromArduino[i];
            batteries.get(x).setBatteryCycles((cyclecount + (cycleDesc / 100))*100);
            i = i + 1;

            batteries.get(x).setBatteryStatus(incomingDataFromArduino[i]);
            i = i + 1;
            
            boolean limitSwitch = false;
            if (incomingDataFromArduino[i]==1)
            {
                limitSwitch = true;
            }
            else
            {
                limitSwitch = false;
            }
                
             batteries.get(x).setLimiSwitch(limitSwitch);
            i = i + 1;
             i = i + 1;
        }
        //System.out.println("ute av update battery");

    }

    public int getBatteriesStatus(int x) {
        return batteries.get(x).getBatteryStatus();
    }

    public void setBatteriesStatus(int x, int value) {
        batteries.get(x).setBatteryStatus(value);
    }
    
    
    public boolean getBatteryLimitSwitchValue(int x)
    {
        return  batteries.get(x).returnLimitSwitch();
    }
    
    public void setLimitSwitcxhValue(int x,boolean value)
    {
         batteries.get(x).setLimiSwitch(value);
    }

   

}
