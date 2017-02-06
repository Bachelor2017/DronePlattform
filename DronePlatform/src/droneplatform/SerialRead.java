package droneplatform;

import java.util.Arrays;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import java.util.concurrent.Semaphore;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * 
 */
public class SerialRead implements Runnable {

    SerialCom serialCom;
    Semaphore semaphore;
    SerialPort serialPort;
    public byte[] dataArduino = new byte[25];
    int increment;
    

    public SerialRead(SerialCom serialCom, Semaphore s, SerialPort sp) {
        this.semaphore = s;
        this.serialPort = sp;
        this.serialCom = serialCom;
    }


    public void run() {
        try {
            while (true) {
                semaphore.acquire();
                byte[] data = serialPort.readBytes(25);
                increment++;
                //System.out.println("Read Serial " + Arrays.toString(data));
                if(data.length > 0){
                byte[] arrangedData = checkDataArrangementTest(data);
                this.dataArduino = arrangedData; 
                serialCom.dataFromArduino = arrangedData;
                    System.out.println("received: " + increment);
                System.out.println("Read Arranged " + Arrays.toString(arrangedData));
                }
                
                semaphore.release();
            }
        } catch (SerialPortException ex) {
            System.out.println("SerialPortException i SerialRead");
        } catch (InterruptedException e) {
            System.out.println("InterruptedException i SerialRead");
        }
    }
    
 
    public byte[] checkDataArrangement(byte[] data) {
        byte[] realData = new byte[6];
        for (int x = 0; x <= 5; x++) {
            if (data[x] == (-128)) {

                if (x == 5) {
                    realData[0] = data[x];
                    realData[1] = data[x - 5];
                    realData[2] = data[x - 4];
                    realData[3] = data[x - 3];
                    realData[4] = data[x - 2];
                    realData[5] = data[x - 1];
                } else if (x == 4) {
                    realData[0] = data[x];
                    realData[1] = data[x + 1];
                    realData[2] = data[x - 4];
                    realData[3] = data[x - 3];
                    realData[4] = data[x - 2];
                    realData[5] = data[x - 1];
                } else if (x == 3) {
                    realData[0] = data[x];
                    realData[1] = data[x + 1];
                    realData[2] = data[x + 2];
                    realData[3] = data[x - 3];
                    realData[4] = data[x - 2];
                    realData[5] = data[x - 1];
                } else if (x == 2) {
                    realData[0] = data[x];
                    realData[1] = data[x + 1];
                    realData[2] = data[x + 2];
                    realData[3] = data[x + 3];
                    realData[4] = data[x - 2];
                    realData[5] = data[x - 1];
                } else if (x == 1) {
                    realData[0] = data[x];
                    realData[1] = data[x + 1];
                    realData[2] = data[x + 2];
                    realData[3] = data[x + 3];
                    realData[4] = data[x + 4];
                    realData[5] = data[x - 1];
                } else {
                    realData = data;
                }
                // System.out.println("READ real " + Arrays.toString(realData));
            }

        }
        return realData;
    }

    public byte[] checkDataArrangementTest(byte[] data) {
        byte[] realData = new byte[25];
        for (int x = 0; x < realData.length; x++) {
            if (data[x] == (-128)) {
                 if (x == 22) {
                       realData[0] = data[x];
                       realData[1] = data[x + 1];
                    realData[2] = data[x + 2];
                    realData[3] = data[x - 22];
                    realData[4] = data[x - 21];
                    realData[5] = data[x - 20];
                    realData[6] = data[x - 19];
                    realData[7] = data[x - 18];
                    realData[8] = data[x - 17];
                    realData[9] = data[x - 16];
                    realData[10] = data[x - 15];
                    realData[11] = data[x - 14];
                    realData[12] = data[x - 13];
                    realData[13] = data[x - 12];
                    realData[14] = data[x - 11];
                    realData[15] = data[x - 10];
                    realData[16] = data[x - 9];
                    realData[17] = data[x - 8];
                    realData[18] = data[x - 7];
                    realData[19] = data[x - 6];
                    realData[20] = data[x - 5];
                    realData[21] = data[x - 4];
                    realData[22] = data[x - 3];
                    realData[23] = data[x - 2];
                    realData[24] = data[x - 1];
                }
                else if (x == 21) {
                       realData[0] = data[x];
                    realData[1] = data[x +1];
                    realData[2] = data[x + 2];
                    realData[3] = data[x + 3];
                    realData[4] = data[x - 21];
                    realData[5] = data[x - 20];
                    realData[6] = data[x - 19];
                    realData[7] = data[x - 18];
                    realData[8] = data[x - 17];
                    realData[9] = data[x - 16];
                    realData[10] = data[x - 15];
                    realData[11] = data[x - 14];
                    realData[12] = data[x - 13];
                    realData[13] = data[x - 12];
                    realData[14] = data[x - 11];
                    realData[15] = data[x - 10];
                    realData[16] = data[x - 9];
                    realData[17] = data[x - 8];
                    realData[18] = data[x - 7];
                    realData[19] = data[x - 6];
                    realData[20] = data[x - 5];
                    realData[21] = data[x - 4];
                    realData[22] = data[x - 3];
                    realData[23] = data[x - 2];
                    realData[24] = data[x - 1];
                }
                else if (x == 20) {
                       realData[0] = data[x];
                    realData[1] = data[x +1];
                    realData[2] = data[x +2];
                    realData[3] = data[x + 3];
                    realData[4] = data[x + 4];
                    realData[5] = data[x - 20];
                    realData[6] = data[x - 19];
                    realData[7] = data[x - 18];
                    realData[8] = data[x - 17];
                    realData[9] = data[x - 16];
                    realData[10] = data[x - 15];
                    realData[11] = data[x - 14];
                    realData[12] = data[x - 13];
                    realData[13] = data[x - 12];
                    realData[14] = data[x - 11];
                    realData[15] = data[x - 10];
                    realData[16] = data[x - 9];
                    realData[17] = data[x - 8];
                    realData[18] = data[x - 7];
                    realData[19] = data[x - 6];
                    realData[20] = data[x - 5];
                    realData[21] = data[x - 4];
                    realData[22] = data[x - 3];
                    realData[23] = data[x - 2];
                    realData[24] = data[x - 1];
                }
                else if (x == 19) {
                       realData[0] = data[x];
                    realData[1] = data[x +1];
                    realData[2] = data[x +2];
                    realData[3] = data[x +3];
                    realData[4] = data[x + 4];
                    realData[5] = data[x + 5];
                    realData[6] = data[x - 19];
                    realData[7] = data[x - 18];
                    realData[8] = data[x - 17];
                    realData[9] = data[x - 16];
                    realData[10] = data[x - 15];
                    realData[11] = data[x - 14];
                    realData[12] = data[x - 13];
                    realData[13] = data[x - 12];
                    realData[14] = data[x - 11];
                    realData[15] = data[x - 10];
                    realData[16] = data[x - 9];
                    realData[17] = data[x - 8];
                    realData[18] = data[x - 7];
                    realData[19] = data[x - 6];
                    realData[20] = data[x - 5];
                    realData[21] = data[x - 4];
                    realData[22] = data[x - 3];
                    realData[23] = data[x - 2];
                    realData[24] = data[x - 1];
                }
                else if (x == 18) {
                       realData[0] = data[x];
                    realData[1] = data[x +1];
                    realData[2] = data[x +2];
                    realData[3] = data[x +3];
                    realData[4] = data[x +4];
                    realData[5] = data[x + 5];
                    realData[6] = data[x + 6];
                    realData[7] = data[x - 18];
                    realData[8] = data[x - 17];
                    realData[9] = data[x - 16];
                    realData[10] = data[x - 15];
                    realData[11] = data[x - 14];
                    realData[12] = data[x - 13];
                    realData[13] = data[x - 12];
                    realData[14] = data[x - 11];
                    realData[15] = data[x - 10];
                    realData[16] = data[x - 9];
                    realData[17] = data[x - 8];
                    realData[18] = data[x - 7];
                    realData[19] = data[x - 6];
                    realData[20] = data[x - 5];
                    realData[21] = data[x - 4];
                    realData[22] = data[x - 3];
                    realData[23] = data[x - 2];
                    realData[24] = data[x - 1];
                }
                else if (x == 17) {
                       realData[0] = data[x];
                    realData[1] = data[x +1];
                    realData[2] = data[x +2];
                    realData[3] = data[x +3];
                    realData[4] = data[x +4];
                    realData[5] = data[x +5];
                    realData[6] = data[x + 6];
                    realData[7] = data[x + 7];
                    realData[8] = data[x - 17];
                    realData[9] = data[x - 16];
                    realData[10] = data[x - 15];
                    realData[11] = data[x - 14];
                    realData[12] = data[x - 13];
                    realData[13] = data[x - 12];
                    realData[14] = data[x - 11];
                    realData[15] = data[x - 10];
                    realData[16] = data[x - 9];
                    realData[17] = data[x - 8];
                    realData[18] = data[x - 7];
                    realData[19] = data[x - 6];
                    realData[20] = data[x - 5];
                    realData[21] = data[x - 4];
                    realData[22] = data[x - 3];
                    realData[23] = data[x - 2];
                    realData[24] = data[x - 1];
                }
                else if (x == 16) {
                       realData[0] = data[x];
                    realData[1] = data[x +1];
                    realData[2] = data[x +2];
                    realData[3] = data[x +3];
                    realData[4] = data[x +4];
                    realData[5] = data[x +5];
                    realData[6] = data[x + 6];
                    realData[7] = data[x + 7];
                    realData[8] = data[x + 8];
                    realData[9] = data[x - 16];
                    realData[10] = data[x - 15];
                    realData[11] = data[x - 14];
                    realData[12] = data[x - 13];
                    realData[13] = data[x - 12];
                    realData[14] = data[x - 11];
                    realData[15] = data[x - 10];
                    realData[16] = data[x - 9];
                    realData[17] = data[x - 8];
                    realData[18] = data[x - 7];
                    realData[19] = data[x - 6];
                    realData[20] = data[x - 5];
                    realData[21] = data[x - 4];
                    realData[22] = data[x - 3];
                    realData[23] = data[x - 2];
                    realData[24] = data[x - 1];
                }
                else if (x == 15) {
                       realData[0] = data[x];
                    realData[1] = data[x +1];
                    realData[2] = data[x +2];
                    realData[3] = data[x +3];
                    realData[4] = data[x +4];
                    realData[5] = data[x +5];
                    realData[6] = data[x + 6];
                    realData[7] = data[x +7];
                    realData[8] = data[x + 8];
                    realData[9] = data[x + 9];
                    realData[10] = data[x - 15];
                    realData[11] = data[x - 14];
                    realData[12] = data[x - 13];
                    realData[13] = data[x - 12];
                    realData[14] = data[x - 11];
                    realData[15] = data[x - 10];
                    realData[16] = data[x - 9];
                    realData[17] = data[x - 8];
                    realData[18] = data[x - 7];
                    realData[19] = data[x - 6];
                    realData[20] = data[x - 5];
                    realData[21] = data[x - 4];
                    realData[22] = data[x - 3];
                    realData[23] = data[x - 2];
                    realData[24] = data[x - 1];
                }
                else if (x == 14) {
                       realData[0] = data[x];
                    realData[1] = data[x +1];
                    realData[2] = data[x +2];
                    realData[3] = data[x +3];
                    realData[4] = data[x +4];
                    realData[5] = data[x +5];
                    realData[6] = data[x + 6];
                    realData[7] = data[x +7];
                    realData[8] = data[x +8];
                    realData[9] = data[x + 9];
                    realData[10] = data[x + 10];
                    realData[11] = data[x - 14];
                    realData[12] = data[x - 13];
                    realData[13] = data[x - 12];
                    realData[14] = data[x - 11];
                    realData[15] = data[x - 10];
                    realData[16] = data[x - 9];
                    realData[17] = data[x - 8];
                    realData[18] = data[x - 7];
                    realData[19] = data[x - 6];
                    realData[20] = data[x - 5];
                    realData[21] = data[x - 4];
                    realData[22] = data[x - 3];
                    realData[23] = data[x - 2];
                    realData[24] = data[x - 1];
                }
                else if (x == 13) {
                       realData[0] = data[x];
                    realData[1] = data[x +1];
                    realData[2] = data[x +2];
                    realData[3] = data[x +3];
                    realData[4] = data[x +4];
                    realData[5] = data[x +5];
                    realData[6] = data[x + 6];
                    realData[7] = data[x +7];
                    realData[8] = data[x +8];
                    realData[9] = data[x +9];
                    realData[10] = data[x + 10];
                    realData[11] = data[x + 11];
                    realData[12] = data[x - 13];
                    realData[13] = data[x - 12];
                    realData[14] = data[x - 11];
                    realData[15] = data[x - 10];
                    realData[16] = data[x - 9];
                    realData[17] = data[x - 8];
                    realData[18] = data[x - 7];
                    realData[19] = data[x - 6];
                    realData[20] = data[x - 5];
                    realData[21] = data[x - 4];
                    realData[22] = data[x - 3];
                    realData[23] = data[x - 2];
                    realData[24] = data[x - 1];
                }
                else if (x == 12) {
                       realData[0] = data[x];
                    realData[1] = data[x +1];
                    realData[2] = data[x +2];
                    realData[3] = data[x +3];
                    realData[4] = data[x +4];
                    realData[5] = data[x +5];
                    realData[6] = data[x + 6];
                    realData[7] = data[x +7];
                    realData[8] = data[x +8];
                    realData[9] = data[x +9];
                    realData[10] = data[x +10];
                    realData[11] = data[x + 11];
                    realData[12] = data[x + 12];
                    realData[13] = data[x - 12];
                    realData[14] = data[x - 11];
                    realData[15] = data[x - 10];
                    realData[16] = data[x - 9];
                    realData[17] = data[x - 8];
                    realData[18] = data[x - 7];
                    realData[19] = data[x - 6];
                    realData[20] = data[x - 5];
                    realData[21] = data[x - 4];
                    realData[22] = data[x - 3];
                    realData[23] = data[x - 2];
                    realData[24] = data[x - 1];
                }
                else if (x == 11) {
                       realData[0] = data[x];
                    realData[1] = data[x +1];
                    realData[2] = data[x +2];
                    realData[3] = data[x +3];
                    realData[4] = data[x +4];
                    realData[5] = data[x +5];
                    realData[6] = data[x + 6];
                    realData[7] = data[x +7];
                    realData[8] = data[x +8];
                    realData[9] = data[x +9];
                    realData[10] = data[x +10];
                    realData[11] = data[x + 11];
                    realData[12] = data[x + 12];
                    realData[13] = data[x + 13];
                    realData[14] = data[x - 11];
                    realData[15] = data[x - 10];
                    realData[16] = data[x - 9];
                    realData[17] = data[x - 8];
                    realData[18] = data[x - 7];
                    realData[19] = data[x - 6];
                    realData[20] = data[x - 5];
                    realData[21] = data[x - 4];
                    realData[22] = data[x - 3];
                    realData[23] = data[x - 2];
                    realData[24] = data[x - 1];
                }
                else if (x == 10) {
                       realData[0] = data[x];
                    realData[1] = data[x +1];
                    realData[2] = data[x +2];
                    realData[3] = data[x +3];
                    realData[4] = data[x +4];
                    realData[5] = data[x +5];
                    realData[6] = data[x + 6];
                    realData[7] = data[x +7];
                    realData[8] = data[x +8];
                    realData[9] = data[x +9];
                    realData[10] = data[x +10];
                    realData[11] = data[x + 11];
                    realData[12] = data[x +12];
                    realData[13] = data[x + 13];
                    realData[14] = data[x + 14];
                    realData[15] = data[x - 10];
                    realData[16] = data[x - 9];
                    realData[17] = data[x - 8];
                    realData[18] = data[x - 7];
                    realData[19] = data[x - 6];
                    realData[20] = data[x - 5];
                    realData[21] = data[x - 4];
                    realData[22] = data[x - 3];
                    realData[23] = data[x - 2];
                    realData[24] = data[x - 1];
                } else if (x == 9) {
                       realData[0] = data[x];
                    realData[1] = data[x +1];
                    realData[2] = data[x +2];
                    realData[3] = data[x +3];
                    realData[4] = data[x +4];
                    realData[5] = data[x +5];
                    realData[6] = data[x + 6];
                    realData[7] = data[x +7];
                    realData[8] = data[x +8];
                    realData[9] = data[x +9];
                    realData[10] = data[x +10];
                    realData[11] = data[x + 11];
                    realData[12] = data[x +12];
                    realData[13] = data[x +13];
                    realData[14] = data[x + 14];
                    realData[15] = data[x + 15];
                    realData[16] = data[x - 9];
                    realData[17] = data[x - 8];
                    realData[18] = data[x - 7];
                    realData[19] = data[x - 6];
                    realData[20] = data[x - 5];
                    realData[21] = data[x - 4];
                    realData[22] = data[x - 3];
                    realData[23] = data[x - 2];
                    realData[24] = data[x - 1];
                } else if (x == 8) {
                       realData[0] = data[x];
                    realData[1] = data[x +1];
                    realData[2] = data[x +2];
                    realData[3] = data[x +3];
                    realData[4] = data[x +4];
                    realData[5] = data[x +5];
                    realData[6] = data[x + 6];
                    realData[7] = data[x +7];
                    realData[8] = data[x +8];
                    realData[9] = data[x +9];
                    realData[10] = data[x +10];
                    realData[11] = data[x + 11];
                    realData[12] = data[x +12];
                    realData[13] = data[x +13];
                    realData[14] = data[x +14];
                    realData[15] = data[x + 15];
                    realData[16] = data[x + 16];
                    realData[17] = data[x - 8];
                    realData[18] = data[x - 7];
                    realData[19] = data[x - 6];
                    realData[20] = data[x - 5];
                    realData[21] = data[x - 4];
                    realData[22] = data[x - 3];
                    realData[23] = data[x - 2];
                    realData[24] = data[x - 1];
                }else if (x == 7) {
                       realData[0] = data[x];
                    realData[1] = data[x +1];
                    realData[2] = data[x +2];
                    realData[3] = data[x +3];
                    realData[4] = data[x +4];
                    realData[5] = data[x +5];
                    realData[6] = data[x + 6];
                    realData[7] = data[x +7];
                    realData[8] = data[x +8];
                    realData[9] = data[x +9];
                    realData[10] = data[x +10];
                    realData[11] = data[x + 11];
                    realData[12] = data[x +12];
                    realData[13] = data[x +13];
                    realData[14] = data[x +14];
                    realData[15] = data[x +15];
                    realData[16] = data[x + 16];
                    realData[17] = data[x + 17];
                    realData[18] = data[x - 7];
                    realData[19] = data[x - 6];
                    realData[20] = data[x - 5];
                    realData[21] = data[x - 4];
                    realData[22] = data[x - 3];
                    realData[23] = data[x - 2];
                    realData[24] = data[x - 1];
                } else if (x == 6) {
                       realData[0] = data[x];
                    realData[1] = data[x +1];
                    realData[2] = data[x +2];
                    realData[3] = data[x +3];
                    realData[4] = data[x +4];
                    realData[5] = data[x +5];
                    realData[6] = data[x + 6];
                    realData[7] = data[x +7];
                    realData[8] = data[x +8];
                    realData[9] = data[x +9];
                    realData[10] = data[x +10];
                    realData[11] = data[x + 11];
                    realData[12] = data[x +12];
                    realData[13] = data[x +13];
                    realData[14] = data[x +14];
                    realData[15] = data[x +15];
                    realData[16] = data[x +16];
                    realData[17] = data[x + 17];
                    realData[18] = data[x + 18];
                    realData[19] = data[x - 6];
                    realData[20] = data[x - 5];
                    realData[21] = data[x - 4];
                    realData[22] = data[x - 3];
                    realData[23] = data[x - 2];
                    realData[24] = data[x - 1];
                }
                    else if (x == 5) {
                       realData[0] = data[x];
                    realData[1] = data[x +1];
                    realData[2] = data[x +2];
                    realData[3] = data[x +3];
                    realData[4] = data[x +4];
                    realData[5] = data[x +5];
                    realData[6] = data[x + 6];
                    realData[7] = data[x +7];
                    realData[8] = data[x +8];
                    realData[9] = data[x +9];
                    realData[10] = data[x +10];
                    realData[11] = data[x + 11];
                    realData[12] = data[x +12];
                    realData[13] = data[x +13];
                    realData[14] = data[x +14];
                    realData[15] = data[x +15];
                    realData[16] = data[x +16];
                    realData[17] = data[x +17];
                    realData[18] = data[x + 18];
                    realData[19] = data[x + 19];
                    realData[20] = data[x - 5];
                    realData[21] = data[x - 4];
                    realData[22] = data[x - 3];
                    realData[23] = data[x - 2];
                    realData[24] = data[x - 1];
                }
                      else if (x == 4) {
                       realData[0] = data[x];
                    realData[1] = data[x +1];
                    realData[2] = data[x +2];
                    realData[3] = data[x +3];
                    realData[4] = data[x +4];
                    realData[5] = data[x +5];
                    realData[6] = data[x + 6];
                    realData[7] = data[x +7];
                    realData[8] = data[x +8];
                    realData[9] = data[x +9];
                    realData[10] = data[x +10];
                    realData[11] = data[x + 11];
                    realData[12] = data[x +12];
                    realData[13] = data[x +13];
                    realData[14] = data[x +14];
                    realData[15] = data[x +15];
                    realData[16] = data[x +16];
                    realData[17] = data[x +17];
                    realData[18] = data[x +18];
                    realData[19] = data[x + 19];
                    realData[20] = data[x + 20];
                    realData[21] = data[x - 4];
                    realData[22] = data[x - 3];
                    realData[23] = data[x - 2];
                    realData[24] = data[x - 1];
                    
                }
                        else if (x == 3) {
                       realData[0] = data[x];
                    realData[1] = data[x +1];
                    realData[2] = data[x +2];
                    realData[3] = data[x +3];
                    realData[4] = data[x +4];
                    realData[5] = data[x +5];
                    realData[6] = data[x + 6];
                    realData[7] = data[x +7];
                    realData[8] = data[x +8];
                    realData[9] = data[x +9];
                    realData[10] = data[x +10];
                    realData[11] = data[x + 11];
                    realData[12] = data[x +12];
                    realData[13] = data[x +13];
                    realData[14] = data[x +14];
                    realData[15] = data[x +15];
                    realData[16] = data[x +16];
                    realData[17] = data[x +17];
                    realData[18] = data[x +18];
                    realData[19] = data[x +19];
                    realData[20] = data[x + 20];
                    realData[21] = data[x + 21];
                    realData[22] = data[x - 3];
                    realData[23] = data[x - 2];
                    realData[24] = data[x - 1];
                    
                }
                          else if (x == 2) {
                       realData[0] = data[x];
                    realData[1] = data[x +1];
                    realData[2] = data[x +2];
                    realData[3] = data[x +3];
                    realData[4] = data[x +4];
                    realData[5] = data[x +5];
                    realData[6] = data[x + 6];
                    realData[7] = data[x +7];
                    realData[8] = data[x +8];
                    realData[9] = data[x +9];
                    realData[10] = data[x +10];
                    realData[11] = data[x + 11];
                    realData[12] = data[x +12];
                    realData[13] = data[x +13];
                    realData[14] = data[x +14];
                    realData[15] = data[x +15];
                    realData[16] = data[x +16];
                    realData[17] = data[x +17];
                    realData[18] = data[x +18];
                    realData[19] = data[x +19];
                    realData[20] = data[x +20];
                    realData[21] = data[x + 21];
                    realData[22] = data[x + 22];
                    realData[23] = data[x - 2];
                    realData[24] = data[x - 1];
                }
                           else if (x == 1) {  
                       realData[0] = data[x];
                    realData[1] = data[x +1];
                    realData[2] = data[x +2];
                    realData[3] = data[x +3];
                    realData[4] = data[x +4];
                    realData[5] = data[x +5];
                    realData[6] = data[x + 6];
                    realData[7] = data[x +7];
                    realData[8] = data[x +8];
                    realData[9] = data[x +9];
                    realData[10] = data[x +10];
                    realData[11] = data[x + 11];
                    realData[12] = data[x +12];
                    realData[13] = data[x +13];
                    realData[14] = data[x +14];
                    realData[15] = data[x +15];
                    realData[16] = data[x +16];
                    realData[17] = data[x +17];
                    realData[18] = data[x +18];
                    realData[19] = data[x +19];
                    realData[20] = data[x +20];
                    realData[21] = data[x +21];
                    realData[22] = data[x + 22];
                    realData[23] = data[x + 23];
                    realData[24] = data[x - 1];
                }
                    else {
                    realData = data;
                }
                // System.out.println("READ real " + Arrays.toString(realData));
            }

        }
        return realData;
    }

}
