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
 *
 * 
 */
public class GUIObservable extends Observable {
    private String textArea1;
    private String textArea2;
    private String textArea3;
    private String textArea4;
    private String textArea5;
    private String textArea6;
    private String textArea7;
    private String textArea8;
    private String textArea9;
    private String textArea10;
    
   private FaultHandler faultHandler;
   private BatteryStationLogic batteryStationLogic;
   private ArrayList<BatteryStation> batteries;
    
    public GUIObservable(FaultHandler faultHandler){
        this.faultHandler = faultHandler;
        this.batteryStationLogic = new BatteryStationLogic();
    }
    
     @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o); //To change body of generated methods, choose Tools | Templates.
        
        
    }
    
    public void setData()
    {
        String[] faultArray = faultHandler.guiFaultList();
        this.textArea1 = faultArray[0];
        this.textArea2 = faultArray[1];
        this.textArea3 = faultArray[2];
        this.textArea4 = faultArray[3];
        this.textArea5 = faultArray[4];
        this.textArea6 = faultArray[5];
        this.textArea7 = faultArray[6];
        this.textArea8 = faultArray[7];
        this.textArea9 = faultArray[8];
        this.textArea10 = faultArray[9];
        batteries = batteryStationLogic.getArrayListBatteries();
        
        setChanged();
        notifyObservers();
    }
    
    
    public String getFaultTextArea1(){
       
        return this.textArea1;
    }
    
    public String getFaultTextArea2(){
       
        return this.textArea2;
    }
    public String getFaultTextArea3(){
       
        return this.textArea3;
    }
    public String getFaultTextArea4(){
       
        return this.textArea4;
    }
    public String getFaultTextArea5(){
       
        return this.textArea5;
    }
    public String getFaultTextArea6(){
       
        return this.textArea6;
    }
    public String getFaultTextArea7(){
       
        return this.textArea7;
    }
    public String getFaultTextArea8(){
       
        return this.textArea8;
    }
    public String getFaultTextArea9(){
       
        return this.textArea9;
    }
    public String getFaultTextArea10(){
       
        return this.textArea10;
    }
    
    
    ///////////////////////////////////////////////////
    //to battery GUI
    
      
    public int getBatteryLevel(int x)
    {
        int batteryChargingLevel = 0;
       batteryChargingLevel = batteries.get(x).getNumberOfSecondsCharged();
      //  int batteryLevel = 0;
        String batteryLevelString = null;
        //batteries = batteryStationLogic.getArrayListBatteries();
       // batteryLevel = batteryStationLogic.getActiveBatteryChargingLevel(x);
       // batteryLevelString = Integer.toString(batteryChargingLevel);
         
        return batteryChargingLevel;
    }
    
     public boolean getBatteryStationDockingStatus(int x)
    {
        boolean isBatteryDockedInStation = false;
       isBatteryDockedInStation = batteries.get(x).isDocked();
     
         
        return isBatteryDockedInStation;
    }
   
    
}
