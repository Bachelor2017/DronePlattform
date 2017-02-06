/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package droneplatform;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

/**
 *
 * @author Olav Rune
 */
public class DronePlatform {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //test.testPrint();
        GUI gui = new GUI();
        gui.setVisible(true);
        //GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //GraphicsDevice screen = ge.getDefaultScreenDevice();
        //screen.setFullScreenWindow(gui);
        
        
        DataHandler dataHandler = new DataHandler();
        FaultHandler faultHandler = new FaultHandler();
        GUIObservable observable = new GUIObservable(faultHandler);
        faultHandler.testing();
        observable.addObserver(gui);
        
        while(true){
            observable.setData();
//            faultHandler.testing();
        }
       
       
    }
    
 
    
}
