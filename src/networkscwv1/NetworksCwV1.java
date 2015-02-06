package networkscwv1;

import CMPC3M06.AudioPlayer;
import CMPC3M06.AudioRecorder;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;
/**
 *
 * @author fqv12suu
 */
public class NetworksCwV1 {
    
    static DatagramSocket sending_socket;
    
    public static void main(String[] args) {
        boolean running = true;
        Sender sender = new Sender();
        Receiver receiver = new Receiver();
        
        sender.start();
        receiver.start();
        
		while(running){
			System.out.println(sender.count - receiver.count);
		}
    }

    
}
