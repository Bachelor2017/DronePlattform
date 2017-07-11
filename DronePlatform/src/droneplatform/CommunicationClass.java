/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package droneplatform;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pi
 */
public class CommunicationClass {

    private final DataHandler dh;
    private final Semaphore semapore;
    private Thread t;
    private byte[] data;
    
    public CommunicationClass(DataHandler dh, Semaphore semapore){
        
        this.dh = dh;
        this.semapore = semapore;
        
    }
    
    public int execute(){
        
        updateData();
        int returnNumb = 0;
        // Checking if drone is in waiting position
        // data{6[ = 1: waiting with battery. 2: waiting for battery. 3: picking up battery. 4: changing battery. 5: finished changing but still working.
        
        /*
        if(data[6] == 2){
            int bat = dh.getBatteryWithHighestPercent();
                    
        }
*/
        
        if(data[6] == 1){
            dh.droneOnPlatform(1);
            returnNumb = 1;
        }
        else {
            returnNumb = 2;
        }
        
        return returnNumb;
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
