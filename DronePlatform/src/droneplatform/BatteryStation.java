package droneplatform;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
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

    public void setBatteryLevel(int value) {
        if (value < 0) {
            batteryLevel = 0;
        } else if (value > 100) {
            batteryLevel = 100;
        } else {
            batteryLevel = value;
        }
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public boolean isDocked() {
        return isDocked;
    }

    public void setDocked(boolean value) {
        isDocked = value;
    }

    public int getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(int value) {
        batteryStatus = value;
    }

}
