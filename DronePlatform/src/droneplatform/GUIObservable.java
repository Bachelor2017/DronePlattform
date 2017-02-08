package droneplatform;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * an observer updating the fields in the GUI
 *
 */
public class GUIObservable extends Observable {

    private FaultHandler faultHandler;
    private BatteryStationLogic batteryStationLogic;
    private ArrayList<BatteryStation> batteries;
    private String[] faultArray;

    public GUIObservable(FaultHandler faultHandler, BatteryStationLogic batteryStationLogic) {
        this.faultHandler = faultHandler;
        this.batteryStationLogic = batteryStationLogic;
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o); //To change body of generated methods, choose Tools | Templates.

    }

    /**
     * setting the data retrieved from system
     */
    public void setData() {

        faultArray = faultHandler.guiFaultList();
        batteries = batteryStationLogic.getArrayListBatteries();

        setChanged();
        notifyObservers();
    }

    public String getFaultText(int x) {
        return faultArray[x];
    }

    public String getFaultTextArea2() {
        return faultArray[1];
    }

    public String getFaultTextArea3() {
        return faultArray[2];
    }

    public String getFaultTextArea4() {
        return faultArray[3];
    }

    public String getFaultTextArea5() {
        return faultArray[4];
    }

    public String getFaultTextArea6() {

        return faultArray[5];
    }

    public String getFaultTextArea7() {

        return faultArray[6];
    }

    public String getFaultTextArea8() {

        return faultArray[7];
    }

    public String getFaultTextArea9() {

        return faultArray[8];
    }

    public String getFaultTextArea10() {

        return faultArray[9];
    }

    ///////////////////////////////////////////////////
    //to battery GUI
    public boolean getBatteryStationDockingStatus(int x) {
        boolean isBatteryDockedInStation = false;
        isBatteryDockedInStation = batteries.get(x).isDocked();

        return isBatteryDockedInStation;
    }

    public void setSpesificBatteryToDocking(int x) {
        batteryStationLogic.settBatteryToChargeInStation(x);
    }

    public void releaseSpesificBatteryFromDocking(int x) {

        batteryStationLogic.releaseBatteryFromChargeInStation(x);
    }

    public int getLastDockedBattery() {
        return batteryStationLogic.getActiveBatteryPlacement();
    }

    public int getSpesificBatteryChargingLevel(int x) {
        return this.batteryStationLogic.getBatteryChargingPercentage(x);

    }

    public int getSpescificBatteryTempertureLevel(int x) {
        return this.batteryStationLogic.getActiveBatteryTemperature(x);
    }

    public int getSpescificBatterySyclecount(int x) {
        return this.batteryStationLogic.getBatteryChargingCycle(x);
    }

    public int getSpescificBatteryMinToFull(int x) {
        return this.batteryStationLogic.getTimeToMaxChargingLevel(x);
    }

    ////////////////////////////////////
    //Utgår når vi tar i bruk I2C
    ////////////////////////////
    public int getBatteryLevel(int x) {
        int batteryChargingLevel = 0;
        batteryChargingLevel = batteries.get(x).getNumberOfSecondsCharged();
        String batteryLevelString = null;

        return batteryChargingLevel;
    }
}
