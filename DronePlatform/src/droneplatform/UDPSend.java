/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package droneplatform;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jrockit.jfr.events.Bits;

/**
 *
 * @author pi
 */
public class UDPSend extends Thread  {
    


    private InetAddress HOST;
    private DatagramSocket s;
    private final int PORT;
    private byte[] outByte;
    String ipAddr;
    private final DataHandlerCom dh;
    private final Semaphore semaphore;
    private byte[] dataToSend;
    private boolean send;

    public UDPSend(DataHandlerCom dh, Semaphore semaphore) {
        
    this.PORT = 1234;
    ipAddr ="192.168.1.115";
    this.dh = dh;
    this. semaphore = semaphore;
    
    
        try {
            HOST = InetAddress.getByName(ipAddr);
        } catch (UnknownHostException ex) {
            System.out.println("Exeption in generating InetAddress in UDPSend...  n/" + ex.getMessage());
        }
    
        try {
            s = new DatagramSocket();
        } catch (SocketException ex) {
            System.out.println("Exeption in generating DatagramSocket in UDPSend...  n/" + ex.getMessage());
        }
    }
    
    public void run(){
        while(true){
            
            try {
                semaphore.acquire();
                if(dh.isNewBytesToSendFlag()){
                    dataToSend = dh.getBytesToSend();
                    send = true;
                }
                semaphore.release();
            } catch (InterruptedException ex) {
                Logger.getLogger(UDPSend.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(send){
                sendBytes();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(UDPSend.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean sendParam(byte[] bytesToSend) {
        boolean result = false;
        byte[] paramBytes = bytesToSend;
        result = send(paramBytes);
        System.out.println("send");
        return result;
    }

    private boolean send(byte[] b) {
        boolean result = false;
      
        DatagramPacket out = new DatagramPacket(b, b.length, HOST, PORT);
  
        try {
            
            s.send(out);
            result = true;
        } catch (IOException ex) {
            System.out.println("Exeption in sending (s.send(out)) in UDPSend...  n/" + ex.getMessage());
        }
        return result;
    }

    private void sendBytes() {
        DatagramPacket out = new DatagramPacket(dataToSend, dataToSend.length, HOST, PORT);
  
        try {
            
            s.send(out);
            send = false;
        
        } catch (IOException ex) {
            System.out.println("Exeption in sending (s.send(out)) in UDPSend...  n/" + ex.getMessage());
        }
        
    }
}