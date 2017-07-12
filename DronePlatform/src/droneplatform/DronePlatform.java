/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package droneplatform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.JsonRpcClient;
import com.googlecode.jsonrpc4j.JsonRpcServer;
import com.googlecode.jsonrpc4j.StreamServer;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Olav Rune
 */
public class DronePlatform {

   
    //private SerialComArduino serialComArduino;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, SocketException, UnknownHostException, IOException {

        Semaphore semaphore = new Semaphore(1, true);
        DataHandler dataHandler = new DataHandler();
        GUI gui = new GUI();
        gui.setVisible(true);
        gui.setHandler(dataHandler);

     
        //creating logic classes/threads
        BatteryStationLogic bsg = new BatteryStationLogic(dataHandler, semaphore);
        bsg.start();
        SystemLogic sysLog = new SystemLogic(dataHandler, semaphore);
        sysLog.start();
        FaultLogic faultLog = new FaultLogic(dataHandler, semaphore);
        faultLog.start();

        //Adding the observer
        GUIObservable observable = new GUIObservable(faultLog, bsg, sysLog,dataHandler,semaphore);
        observable.addObserver(gui);

        
     //Serial Communication batteries
        //SerialComArduino serialComArduino = new SerialComArduino("COM6", dataHandler, semaphore);
      
        SerialComArduino serialComArduino = new SerialComArduino("/dev/ttyUSB0", dataHandler, semaphore); 
         serialComArduino.start();
        //Serial Communication stepper controller
       // SerialComMega serialComTeensy = new SerialComMega("COM4", dataHandler, semaphore);
         SerialComMega serialComTeensy = new SerialComMega("/dev/ttyACM0", dataHandler, semaphore);
        serialComTeensy.start();
        
        
        Semaphore semaphoreCom = new Semaphore(1, true);
        DataHandlerCom dataHandlerCom= new DataHandlerCom();
        CommunicationClass com = new CommunicationClass(dataHandler, semaphore, bsg, dataHandlerCom,semaphoreCom);
        com.start();
        
        UDPSend udpSend = new UDPSend(dataHandlerCom,semaphoreCom);
        udpSend.start();

         
        

        UDPReceive udpRecive = new UDPReceive(1111,dataHandler,semaphore, dataHandlerCom,semaphoreCom);
        udpRecive.start();
        /*
        
        ObjectMapper userService = new ObjectMapper();
      //  UserService userService = new UserServiceImpl();
        JsonRpcServer server = new JsonRpcServer(userService, UserService.class);
        int maxThreads = 2;
        int port = 1234;
        InetAddress bindAdress = InetAddress.getByName("localhost");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            StreamServer streamServer = new StreamServer(server, maxThreads, serverSocket);
            streamServer.start();
        } catch (IOException ex) {
            Logger.getLogger(DronePlatform.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        
        
        JsonRpcClient client = new JsonRpcClient(userService);
        client.invoke("getState", new Object[]{}, new OutputStream() {
            @Override
            public void write(int b) {
            }
        });
        

      */

        while (true) {
            observable.setData();
        }

    }

}
