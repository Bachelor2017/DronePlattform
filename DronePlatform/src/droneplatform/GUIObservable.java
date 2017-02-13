package droneplatform;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

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
    private EventStates events;
    private ArrayList<BatteryStation> batteries;
    private String[] faultArray;
    private String[] eventArray;
    ArrayList<String> faultList;
    ArrayList<String> eventList;
    private Thread t;
    private boolean limitSwitch;

    public GUIObservable(FaultHandler faultHandler, BatteryStationLogic batteryStationLogic,EventStates events) {
        this.faultHandler = faultHandler;
        this.batteryStationLogic = batteryStationLogic;
        this.events = events;
        
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
                faultList = faultHandler.getFaultList();
                batteries = batteryStationLogic.getArrayListBatteries();
                eventArray = events.guiEventList();
                eventList = events.getEventList();
   
        setChanged();
        notifyObservers();
    }

    
    
    
    ////////////////////////FAULT HANDLING/////////////////////////
    /**
     * get the faultmessages
     *
     * @param x the message number in the list
     * @return the message as string
     */
    public String getFaultText(int x) {
        return faultArray[x];
    }

    public ArrayList<String> getFaultList() {
        return faultList;
    }
    
    
    
    /////////////////////////EVENT HANDLING////////////////////////
    /**
     * get the faultmessages
     *
     * @param x the message number in the list
     * @return the message as string
     */
    public String getEventText(int x) {
        return eventArray[x];
    }
    
     public ArrayList<String> getEventList() {
        return eventList;
    }

     public String getLastEventState()
     {
         return events.getLastEventState();
     }
     
     public int testGetXvalue()
     {
         return events.testGetXValue();
     }
     
     
    /////////////////////////BATTERY INFORMATION//////////////////////
    public boolean getBatteryStationDockingStatus(int x) {
        boolean isBatteryDockedInStation = false;
        isBatteryDockedInStation = batteries.get(x).isDocked();

        return isBatteryDockedInStation;
    }

   

    /**
     * retriesves the last docked battery
     *
     * @return the int of the last doicked battery
     */
    public int getLastDockedBattery() {
        return batteryStationLogic.getActiveBatteryPlacement();
    }

    /**
     * get spesifik battery cvhargingvalue
     *
     * @param x the number of the battery
     * @return the int of the charging level
     */
    public int getSpesificBatteryChargingLevel(int x) {
        return this.batteryStationLogic.getBatteryChargingPercentage(x);

    }

    /**
     * get spesifik battery temperatire
     *
     * @param x the number of the battery
     * @return the int of the temperature level
     */
    public float getSpescificBatteryTempertureLevel(int x) {
        return this.batteryStationLogic.getActiveBatteryTemperature(x);
    }

    /**
     * get spesifik battery cychlus
     *
     * @param x the number of the battery
     * @return the int of the cychlus level
     */
    public float getSpescificBatterySyclecount(int x) {
        return this.batteryStationLogic.getBatteryChargingCycle(x);
    }

    /**
     * get spesifik battery minutes to full charged
     *
     * @param x the number of the battery
     * @return the int of the miuntes to full level
     */
    public int getSpescificBatteryMinToFull(int x) {
        return this.batteryStationLogic.getTimeToMaxChargingLevel(x);
    }

    public float getSpesificChargingVoltage(int x) {
        return this.batteryStationLogic.getSpesificChargingVoltage(x);
    }

    public boolean getSpesificLimitSwitch(int x)
    {
        return batteryStationLogic.getBatteryLimitSwitchValue(x);
    }
    

    public int getBatteriesStatus(int x) {
        return batteryStationLogic.getBatteriesStatus(x);
    }
    
    
    ///////HAR LAGT DE INN,MEN TRENGER MULIGENS IKKE NOE SETTERE HER
    public void setSpesificChargingVoltage(int x, float voltage) {
        this.batteryStationLogic.getSpesificChargingVoltage(x);
    }

    public void setBatteriesStatus(int x, int value) {
        batteryStationLogic.setBatteriesStatus(x, value);
    }
    
     /**
     * sets a spesific battery to docking
     *
     * @param x
     */
    public void setSpesificBatteryToDocking(int x) {
        batteryStationLogic.settBatteryToChargeInStation(x);
    }

    /**
     * release a spesific battery from docking
     *
     * @param x the number of the battery
     */
    public void releaseSpesificBatteryFromDocking(int x) {

        batteryStationLogic.releaseBatteryFromChargeInStation(x);
    }
        
    public void addFaultToList()
    {
        faultHandler.addFault();
    }

}
