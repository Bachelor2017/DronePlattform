package droneplatform;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * 
 */
public class DataHandler {

    private SerialCom serialCom;

    public DataHandler() {
        //serialCom = new SerialCom("/dev/ttyUSB0", this);
        serialCom = new SerialCom("COM7", this);
        serialCom.connect();
    }

    public byte[] dataToArduino() {
        byte[] data = new byte[7];
        data[0] = 100;
        data[1] = 2;
        data[2] = 3;
        data[3] = 4;
        data[4] = 5;
        data[5] = 6;
        data[6] = 123;
        return data;
    }
    
    
    public byte[] getDataFromArduino(){
        byte[] data = new byte[64];
        data[0] = 100;
        data[1] = 2;
        data[2] = 3;
        data[3] = 4;
        data[4] = 5;
        data[5] = 6;
        data[6] = 123;
        return data;
        
        
    }
}
