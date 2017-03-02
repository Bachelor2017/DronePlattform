package droneplatform;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * an observer updating the fields in the GUI
 *
 */
public class GUIObservable extends Observable {

    private FaultHandler faultHandler;
    private BatteryStationLogic batteryStationLogic;
    private SystemLogic events;
    // private EventStates events;
    private ArrayList<BatteryStation> batteries;
    private String[] faultArray;
    private String[] eventArray;
    ArrayList<String> faultList;
    ArrayList<String> eventList;
    private Thread t;
    private boolean limitSwitch;
    private int timeLeft;
    private int timeLeftCyclus;

   // public GUIObservable(FaultHandler faultHandler, BatteryStationLogic batteryStationLogic, EventStates events) {
    public GUIObservable(FaultHandler faultHandler, BatteryStationLogic batteryStationLogic, SystemLogic events) {
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
        faultList = faultHandler.getFaultList();
        batteries = batteryStationLogic.getArrayListBatteries();
        timeLeft = events.timeLeftOfBatteryChange();
        timeLeftCyclus = events.getLastEventTimeSyclus();
        setChanged();
        notifyObservers();
    }

    public int getTimeLeft() {
        return timeLeft;
    }
    
      public int getTimeLeftCyclus() {
        return timeLeftCyclus;
    }

    ////////////////////////FAULT HANDLING/////////////////////////
    /**
     * get the faultmessages
     *
     * @param x the message number in the list
     * @return the message as string
     */
    public String getLastErrorMessage(){
        return faultHandler.lastFaultMessage();
    }

    /////////////////////////EVENT HANDLING////////////////////////
    /**
     * get the faultmessages
     *
     * @param x the message number in the list
     * @return the message as string
     */
 

    public String getLastEventState() {
        return events.getLastEventState();
    }

    public int testGetXvalue() {
        return events.getTotalTimeUsed();
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
     * Returns battery temperatire
     *
     * @param x the number of the battery
     * @return the int of the temperature level
     */
    public float getSpescificBatteryTempertureLevel(int x) {
        return this.batteryStationLogic.getActiveBatteryTemperature(x);
    }

    /**
     * Return battery cycle count.
     *
     * @param x the number of the battery
     * @return the int of the cychlus level
     */
    public float getSpescificBatteryCyclecount(int x) {
        return this.batteryStationLogic.getBatteryChargingCycle(x);
    }

    /**
     * Returns the predicted time to full charge in minutes.
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

    public boolean getSpesificLimitSwitch(int x) {
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
     * Sets a spesific battery to docking
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

    public void addFaultToList() {
        faultHandler.addFault();
    }
    
    public String getTimeStamp(){
        return events.getTimeStamp();
    }
    
    
    
    
   

}
