/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package droneplatform;

import java.util.concurrent.Semaphore;

/**
 *
 * @author pi
 */
public class UDPSend extends Thread  {
    
    Semaphore semaphore = new Semaphore(1, true);
    private DataHandler dh;
    
    public UDPSend(DataHandler dh, Semaphore semapore){
        
    }
    
    
    
    public void run(){
        
    }
    
}
