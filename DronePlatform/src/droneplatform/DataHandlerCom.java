/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package droneplatform;

/**
 *
 * @author pi
 */
public class DataHandlerCom {
    
   
    private byte[] commands;
    private boolean newCommands;
    private boolean newBytesToSendFlag;
    private byte[] bytesToSend;

    public byte[] getCommands() {
        newCommands = false;
        return commands;
    }

    public void setCommands(byte[] commands) {
        newCommands = true;
        this.commands = commands;
    }

    public boolean isNewCommands() {
        return newCommands;
    }

    public boolean isNewBytesToSendFlag() {
        return newBytesToSendFlag;
    }

    public byte[] getBytesToSend() {
        newBytesToSendFlag = false;
        return bytesToSend;
    }

    public void setBytesToSend(byte[] bytesToSend) {
        newBytesToSendFlag = true;
        this.bytesToSend = bytesToSend;
    }

    
    
    

     public DataHandlerCom(){
    commands = new byte[3];
    bytesToSend = new byte[20];
    newCommands = false;
    newBytesToSendFlag = false;
    
}
    
}
