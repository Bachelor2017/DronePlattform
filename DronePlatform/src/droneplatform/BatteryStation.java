package droneplatform;

import java.util.TimerTask;
import java.util.Timer;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Batteristation for batteri charging
 *
 */
public class BatteryStation {

    private boolean dockingStatus;
    private int batteryLevel;
    private int batteryStatus;
    private int xValue;
    private int yValue;
    private int zValue;
    private byte[] stationLocation;
    private int secondsPassed;
    private Timer timer;
    private TimerTask tTask;
    private int batteryPossition;
    private int chargingTimeToMax;
    private int temperature;
    private int timeToMaxBattery;
    private int chargedPercentage;

    public BatteryStation(int batteryPossition) {

        this.batteryPossition = batteryPossition;
        batteryStatus = 0;
        batteryLevel = 0;
        dockingStatus = true;
        setDocked(dockingStatus);
        this.stationLocation = new byte[3];
        chargingTimeToMax = 100;   //3600 oringalt

    }

    /**
     * Sets the batterycharged value. makes sure the value uios not below 0 or
     * above 100
     *
     * @param value the value of batterycharging status
     */
    public void setBatteryLevel(int value) {
        if (value < 0) {
            batteryLevel = 0;
        } else if (value > 100) {
            batteryLevel = 100;
        } else {
            batteryLevel = value;

        }
    }

    /**
     * The value is calculated from the soconds in docking to seconds to full
     * carge ratio
     *
     * @return the value of the battery in persentage
     */
    public int getBatteryLevel() {
        batteryLevel = (secondsPassed / chargingTimeToMax) * 100;
        return batteryLevel;
    }

    /**
     * Check if there is battery docked to station
     *
     * @return boolean signal that staes if there is battery in station (true)
     * or not (false)
     *
     */
    public boolean isDocked() {
        return dockingStatus;
    }

    /**
     * Setting the docking status of the station If battery is docked, the timer
     * starts running. of the battery is out of docking, the timer pstops and
     * resets
     *
     * @param value boolean value , true if battery is in docking, and false if
     * station is empty
     */
    public void setDocked(boolean value) {
        if (value == true) {
            tTask = new TimerTask() {
                public void run() {
                    secondsPassed++;
                    System.out.println("Battery :" + batteryPossition + " ,Seconds Passed: " + secondsPassed);
                }
            };

            timer = new Timer();
            timer.scheduleAtFixedRate(tTask, 1000, 1000);
        } else {
            timer.cancel();
            timer.purge();
            secondsPassed = 0;
        }
        dockingStatus = value;
    }

    /**
     * get the batterystatus from the station
     *
     * @return
     */
    public int getBatteryStatus() {
        return batteryStatus;
    }

    /**
     * Setting the batterystatus to the station
     *
     * @param value a integer for the batterystatus
     */
    public void setBatteryStatus(int value) {
        batteryStatus = value;
    }

    /**
     * Returning the X-Value of the charger
     *
     * @return xValue of the charger
     */
    public int getxValue() {
        return xValue;
    }

    /**
     * Setting the X-Value of the station
     *
     * @param xValue setting xValue of station
     */
    public void setxValue(int xValue) {
        this.xValue = xValue;
    }

    /**
     * Returning the Y-Value of the charger
     *
     * @return yValue of the charger
     */
    public int getyValue() {
        return yValue;
    }

    /**
     * Setting the Y-Value of the station
     *
     * @param yValue setting xValue of station
     */
    public void setyValue(int yValue) {
        this.yValue = yValue;
    }

    /**
     * Returning the Z-Value of the charger
     *
     * @return zValue of the charger
     */
    public int getzValue() {
        return zValue;
    }

    /**
     * Setting the Z-Value of the station
     *
     * @param zValue setting xValue of station
     */
    public void setzValue(int zValue) {
        this.zValue = zValue;
    }

    /**
     * returns the location in X,Y and Z direction of the station
     *
     * @return stationLocation
     */
    public byte[] getBatteryStationLocation() {
        stationLocation[0] = (byte) xValue;
        stationLocation[1] = (byte) yValue;
        stationLocation[2] = (byte) zValue;
        return stationLocation;
    }

    /**
     * Setting the location of the batteryStation
     *
     * @param newXValue, xvalue of the station
     * @param newYValue yvalue of the station
     * @param newZValue zvalue of the station
     */
    public void setBatteryStationLocation(int newXValue, int newYValue, int newZValue) {
        xValue = (byte) newXValue;
        yValue = (byte) newYValue;
        zValue = (byte) newZValue;
    }

   
    /**
     * sett the temperature of the battery
     * @param temp of battery
     */
    public void setTemperature(int temp)
    {
        temperature = temp;
    }
    
    /**
     * returning the battery temperature
     * @return battery temperature int
     */
    public int getTemperature()
    {
        return temperature;
    }
    
    
    /**
     * Setting the time to battery is fully charged
     * @param time to battery is fully charged int
     */
    public void setTimeToMaxBattery(int time)
    {
        timeToMaxBattery = time;
    }
    
    /**
     * returns the time for the battery is fully charged
     * @return timeToMaxBattery
     */
    public int returnTimeToMaxBattery()
    {
        return timeToMaxBattery;
    }
    
    /**
     * returns the battery charging percentage
     * @param percentage of charged battery
     */
    public void setPercentageCharged(int percentage)
    {
        this.chargedPercentage = percentage;
    }
    
    /**
     * returns the percentage of the charged battery
     * @return percetage of battery
     */
    public int getChargedPercentage()
    {
        return chargedPercentage;
    }
    
    
    
    
    
    
    
    
    
    
    /**
     * Returns the number of seconds the batterystation has batteri on charging
     * returns 0 if batterDocking is false
     */
    public int getNumberOfSecondsCharged() {
        return secondsPassed;
    }

}
