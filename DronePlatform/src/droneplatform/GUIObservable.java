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

   // private FaultHandler faultHandler;
     private FaultLogic faultHandler;
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

  
   // public GUIObservable(FaultHandler faultHandler, BatteryStationLogic batteryStationLogic, SystemLogic events) {
        public GUIObservable(FaultLogic faultHandler, BatteryStationLogic batteryStationLogic, SystemLogic events) {
        this.faultHandler = faultHandler;
        this.batteryStationLogic = batteryStationLogic;
        this.events = events;

    }

    /**
     * adds a observer to class
     * @param o 
     */
    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o); //To change body of generated methods, choose Tools | Templates.

    }

    /**
     * setting the data retrieved from system
     */
    public void setData() {
      //  faultList = faultHandler.getFaultList();
        batteries = batteryStationLogic.getArrayListBatteries();
        setChanged();
        notifyObservers();
    }

 
    ///////////////////////////////////////////////////////////////
    ////////////////////////FAULT HANDLING/////////////////////////
    /**
     * get the faultmessages
     *
     * @param x the message number in the list
     * @return the message as string
     */
    public String getLastErrorMessage(){
        return faultHandler.getLastEventState();
    }

    
    ///////////////////////////////////////////////////////////////
    /////////////////////////EVENT HANDLING////////////////////////
  

    /**
     * get the last events state of the process
     * @return the eventName as String of the last event
     */
    public String getLastEventState() {
        return events.getLastEventState();
    }
    
    /**
     * get the total time used in the total process
     * @return the int value of the time used
     */
    public int getTotalTimeUsed() {
        return events.getTotalTimeUsed();
    }
    
    /**
     * get the total time left in the total process
     * @return the int value of the time left
     */
          public int getTimeLeft() {
        return events.getTotalTimeRemaining();
    }
    
          /**
     * get the total time used in the current cycle
     * @return the int value of the time used
     */
     public int getCycleTimeUsed() {
        return events.getCycleTimeUsed();
    }
     
     
           /**
     * get the total time left in the current cycle
     * @return the int value of the time left
     */
     public int getCycleTimeLeft()
     {
          return events.getCycleTimeRemaining();
        
     }
     
     
           /**
     * get the percentage progress in the total process
     * @return the int value of the percentage progress (0-100)
     */
     public int getTotalPercentageCompleted()
     {
         return events.getTotalPercentageCompleted();
     }
     
     
       
           /**
     * get the percentage progress in the current cycle
     * @return the int value of the percentage progress in the current cycle (0-100)
     */
     public int getCyclusPercentageCompleted()
     {
         return events.getCyclusPercentageCompleted();
     }
     
  
    
    //////////////////////////////////////////////////////////////////
    /////////////////////////BATTERY INFORMATION//////////////////////
     
  
    /**
     * retrieves the last docked battery
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
    
    
/**get the charging voltage of a spesifikk battery from the array list. 
 * 
 * @param x the number of the spesifikk battery in the list
 * @return the voltage of the spesifikk battery
 */
    public float getSpesificChargingVoltage(int x) {
        return this.batteryStationLogic.getSpesificChargingVoltage(x);
    }

/**get the limitSwitch of a spesifikk battery from the array list. 
 * This indicates if the battery is docked or not
 * 
 * @param x the number of the spesifikk battery in the list
 * @return the value of the limit switch of the spesifikk battery
 */
    public boolean getSpesificLimitSwitch(int x) {
        return batteryStationLogic.getBatteryLimitSwitchValue(x);
    }

  /**get the status of a spesifikk battery from the array list. 
 * 
 * @param x the number of the spesifikk battery in the list
 * @return the voltage of the spesifikk battery
 */
    public int getBatteriesStatus(int x) {
        return batteryStationLogic.getBatteriesStatus(x);
    }

    
    
    /**
     * get the timestamp as a string
     * @return timestamp in the "HH:mm:ss" format
     */
    public String getTimeStamp(){
        return events.getTimeStamp();
    }
    
    
    
    /////////////////////////////////////////////////
    //////////////kommentert bort for å se om de kan slettes
        /*
    public void addFaultToList() {
        faultHandler.addFault();
    }*/
    /*
    
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
    /*
    public void setSpesificBatteryToDocking(int x) {
        batteryStationLogic.settBatteryToChargeInStation(x);
    }

    /**
     * release a spesific battery from docking
     *
     * @param x the number of the battery
     *//*
    public void releaseSpesificBatteryFromDocking(int x) {

        batteryStationLogic.releaseBatteryFromChargeInStation(x);
    }*/
   

    
      /*
    public boolean getBatteryStationDockingStatus(int x) {
        boolean isBatteryDockedInStation = false;
        isBatteryDockedInStation = batteries.get(x).isDocked();

        return isBatteryDockedInStation;
    }*/

    
    /*  public int getTimeLeftCyclus() {
        return timeLeftCyclus;
    }*/
     
    
      /**
     * get the faultmessages
     *
     * @param x the message number in the list
     * @return the message as string
     */
 
    
}
