package networkscwv1;

import CMPC3M06.AudioPlayer;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import uk.ac.uea.cmp.voip.*;

/**
 *
 * @author fqv12suu
 */
public class Receiver implements Runnable {
    
    static DatagramSocket receiving_socket;
    final int PORT_NUMBER = 55555;
    boolean receiving = true;
    byte[] voiceFrame;
    AudioPlayer player;
	public int count;
    
    public void start(){
        Thread thread = new Thread(this);
	thread.start();
    }
        
    public void run(){
        
        try{
            player = new AudioPlayer();        
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        try{
            receiving_socket = new DatagramSocket(PORT_NUMBER);
	} 
        catch (SocketException e){
                System.out.println(
                                  "ERROR: Receiver: "
                                + "Could not open UDP socket to "
                                + "receive from.");
		e.printStackTrace();
                System.exit(0);
	}
		count = 0;
        while (receiving){
         
        try{
            //Receive a DatagramPacket from the sender
            byte[] buffer = new byte[512];
            DatagramPacket packet = new DatagramPacket(buffer, 0, 512);
            receiving_socket.setSoTimeout(100000);
                
            receiving_socket.receive(packet);
            voiceFrame = new byte[512];
            //Get a frame of audio from the byte buffer
            voiceFrame = packet.getData();
            player.playBlock(voiceFrame);
			count++;

        } 
        catch (IOException e){
            System.out.println(
                    "ERROR: Receiver: Some random IO error occured!");
            e.printStackTrace();
        }
    }
    player.close();
        
    }
}

