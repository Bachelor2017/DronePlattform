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

    private int batteryStationNumberPossition;
    private int batteryStationYPossition;
    private int batteryStationChargingLevel;

    ///
    private java.util.Timer timer;
    private TimerTask tTask;
    public int secondsPassed;
    ///

    
    /**
     * create the batterystationlogic, and fill inn batteries to atrraylist
     */
    public BatteryStationLogic() {
        batteries = new ArrayList<>();
        batteryStationNumberPossition = 0;
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

    public void setBatteriesDocking(int x, boolean value) {
        batteries.get(x).setDocked(value);
    }

    public boolean isBatteriesDocked(int x) {
        return batteries.get(x).isDocked();
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

    
    /**
     * find the next battery in line to be used
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
     * @param x X is the number of the batterystation in the list
     * @return a byte[] where first byte is X-value, 
     * second is Y-Value and last i z-Value
     */
    public byte[] getActiveBatteryXYZLocation(int x) {
        this.batteriStationLocation = new byte[3];
        batteriStationLocation = batteries.get(x).getBatteryStationLocation();
        return batteriStationLocation;
    }

    /**
     * get the charging level of the active battery
     * @param x the number of the batterystation
     * @return the int of the charging level in percentage
     */
    public int getActiveBatteryChargingLevel(int x) {
        batteryStationChargingLevel = batteries.get(x).getBatteryLevel();
        return batteryStationChargingLevel;
    }

    /**
     * setting the spesific battery to charging station
     * @param x the  number of the batterystation
     */
    public void settBatteryToChargeInStation(int x) {
        batteries.get(x).setDocked(true);
    }

}
