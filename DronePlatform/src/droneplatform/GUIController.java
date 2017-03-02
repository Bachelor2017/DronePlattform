/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package droneplatform;

//import java.nio.ByteBuffer;
import java.util.TimerTask;

/**
 * runs on timertask. checs the functions each run
 *
 * @author Jørgen
 */
public class GUIController extends TimerTask {

    private DataHandler datahandler;

    public GUIController() {

    }

    /**
     * prints the statement :Reques data:"
     */
    @Override
    public void run() {
        //System.out.println("Request data");
        //this.datahandler.incrementRequestCode();
    }

    /**
     * setting the datahandler to the timertask
     *
     * @param datahandler
     */
    public void setDatahandler(DataHandler datahandler) {
        this.datahandler = datahandler;
    }

    /**
     * checs value if true, and ends the boolean value to datahandler
     *
     * @param value
     */
    
    
     public void setLiftStatus(boolean value,int direction) {

        System.out.println("status lift " + value);
        this.datahandler.motorStatus(1,value,direction);

    }
      
    
     /**
     * checs value if true, and ends the boolean value to datahandler
     *
     * @param value
     */
    public void setConveyorStatus(boolean value,int direction) {
        System.out.println("status conveyor " + value);
        this.datahandler.motorStatus(2,value,direction);

    }
    
    
    /**
     * Setting the running mode of platform. true = auto , false = manual
     *
     * @param value boolean value , true =auto , false = manual
     */
    public void setPlatformMode(boolean value) {
       
            this.datahandler.setPlatformMode(value);      //auto mode
           
    }
    

}
