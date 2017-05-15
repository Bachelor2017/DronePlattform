package droneplatform;

import droneplatform.DataHandler;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 * @author
 */
public class UDPrecive extends Thread {

    private Semaphore semaphore;
    private DataHandler dh;
    private int consumerID;
    private int numberOfProducerThreads;
    private boolean stop;
    private boolean available;
    private long sleepTime;

    private final int PORT;
    private static final int PARAMS = 25;
    private DatagramSocket socket;
    private DatagramPacket datagram;

    public UDPrecive(int PORT, DataHandler dh, Semaphore semapore) throws SocketException {

        this.sleepTime = 1;
        stop = false;

        this.PORT = PORT;
        byte[] buf = new byte[PARAMS];
        datagram = new DatagramPacket(buf, buf.length);
        socket = new DatagramSocket(this.PORT);

    }

    public void run() {

        while (!stop) {
            byte[] b = new byte[25];    // size of byte to be received
            try {
                b = receiveParam();
            } catch (IOException ex) {
                Logger.getLogger(UDPrecive.class.getName()).log(Level.SEVERE, null, ex);
            }
            processData(b);

        }

    }

    public byte[] receiveParam() throws IOException {
        socket.receive(datagram);
        byte[] datagramData = datagram.getData();
        return datagramData;
    }

    private void processData(byte[] b) {

        if (b[0] == 1) {
            try {
                semaphore.acquire();
                dh.droneOnPlatform(1);
                semaphore.release();
            } catch (InterruptedException ex) {
                Logger.getLogger(UDPrecive.class.getName()).log(Level.SEVERE, null, ex);
            }

          

        }
        
    }

}
