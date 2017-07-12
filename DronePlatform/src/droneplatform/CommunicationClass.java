/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package droneplatform;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pi
 */
public class CommunicationClass extends Thread{

    private final DataHandler dh;
    private final Semaphore semapore;
    private Thread t;
    private byte[] data;
     private BatteryStationLogic batteryStationLogic;
      private ArrayList<BatteryStation> batteries;
    private final DataHandlerCom dhCom;
    private final Semaphore semaporeCom;
    
    public CommunicationClass(DataHandler dh, Semaphore semapore, BatteryStationLogic batteryStationLogic, DataHandlerCom dhCom, Semaphore semaphoreCom){
        
        this.dh = dh;
        this.semapore = semapore;
          this.batteryStationLogic = batteryStationLogic;
          this.dhCom = dhCom;
          this.semaporeCom = semaphoreCom;
        
    }
    public void run(){
        while(true){
            try {
                semaporeCom.acquire();
            } catch (InterruptedException ex) {
                Logger.getLogger(CommunicationClass.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
           semaporeCom.release();
            
        }
        
    }
    
    public int execute(){
        
        updateData();
        int returnNumb = 0;
        // Checking if drone is in waiting position
        // data[9] = 1: waiting with battery. 2: waiting for battery. 3: picking up battery. 4: changing battery. 5: finished changing but still working.
        
        /*
        if(data[6] == 2){
            int bat = dh.getBatteryWithHighestPercent();
                    
        }
*/
        
        if(data[9] == 1){
            dh.startCommandToMega(1);
            returnNumb = 1;
        }
        else {
            returnNumb = data[9];
        }
        
        return returnNumb;
    }
    public int dispose(){
        updateData();
        int returnNumb = 0;
        if(data[9] ==1){
            dh.startCommandToMega(3);
        }
        
        return returnNumb;
    }
    public byte[] getData(){
        byte[] returnByte = new byte[40];
        returnByte[0] = 1;
        
        // get battery percent
         batteries = batteryStationLogic.getArrayListBatteries();
         for(int i = 1; i <=16; i++){
             returnByte[i] = (byte)batteries.get(i).getChargedPercentage();
         }
         
         returnByte[17] = data[6];          //Platform case number
         returnByte[18] = data[9];  //Status number     1: waiting with battery. 2: waiting for battery. 3: picking up battery. 4: changing battery. 5: finished changing but still working.
         returnByte[19] = data[10]; //number of battery changed
         returnByte[20] = data[13]; // fault code from platform.    0 - ok. 10 - Battery stuck on either drone or charging station. 11 - drone stuck on arm
         
         
        
        
        
        
        return returnByte;
    }
    
    

    private void updateData() {
        try {
            semapore.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(CommunicationClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        data = dh.getDataFromMega();
        semapore.release();
    }
    
    
    
  
    
    
}
