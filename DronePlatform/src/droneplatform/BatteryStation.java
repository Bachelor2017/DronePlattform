package droneplatform;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *Batteristation for batteri charging
 * 
 */
public class BatteryStation {

    private boolean isDocked;
    private int batteryLevel;
    private int batteryStatus;

    
    
    public BatteryStation() {
        batteryStatus = 0;
        batteryLevel = 0;
        isDocked = false;
    }

    
    /**
     * Sets the batterycharged value. makes sure the value uios not below 0 
     * or above 100 
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
     * 
     * @return the value of the battery 
     */
    public int getBatteryLevel() {
        return batteryLevel;
    }

    /**
     * Check if there is battery docked to station
     * @return boolean signal that staes if there is battery in station (true) 
     * or not (false)
     * 
     */
    public boolean isDocked() {
        return isDocked;
    }

    /**
     * Setting the docking status of the station
     * @param value boolean value , true if battery is in docking, 
     * and false if staion is empty
     */
    public void setDocked(boolean value) {
        isDocked = value;
    }

    /**
     * get the batterystatus from the station
     * @return 
     */
    public int getBatteryStatus() {
        return batteryStatus;
    }

    /**
     * Setting the batterystatus to the station
     * @param value a integer for the batterystatus
     */
    public void setBatteryStatus(int value) {
        batteryStatus = value;
    }

}
