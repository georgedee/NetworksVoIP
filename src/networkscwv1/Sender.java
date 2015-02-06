/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package networkscwv1;

import CMPC3M06.AudioRecorder;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import static networkscwv1.NetworksCwV1.sending_socket;
import uk.ac.uea.cmp.voip.*;

/**
 *
 * @author fqv12suu
 */
public class Sender implements Runnable{
    boolean recording = true;
    final int PORT_NUMBER = 55555;
    InetAddress clientIP = null;
    byte[] voiceFrame[];
    //Vector voiceFrame;
    Scanner input;
    AudioRecorder recorder;
    DatagramPacket packet;
	public int count;
    
    public void start(){
        Thread thread = new Thread(this);
	thread.start();
    }
    
    public void run(){
        /**
         * Set up the connection first.
         * 1). Set IP address
         * 2). Set socket
         * getByName returns the IP address of the client and re-assigns
         * clientIP
         */
        try {
            clientIP = InetAddress.getByName("localhost");
	} catch (UnknownHostException e) {
                System.out.println(
                        "ERROR: Sender: Could not find client IP");
		e.printStackTrace();
                System.exit(0);
	}
        
        /**
         * Set up socket. I think we use this for receiving only
         */
        try{
            sending_socket = new DatagramSocket4();
	} catch (SocketException e){
                System.out.println(
                        "ERROR: Sender: "
                                + "Could not open UDP socket to send from.");
		e.printStackTrace();
                System.exit(0);
	}
        
        input = new Scanner(System.in);
        try{
            recorder = new AudioRecorder();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        count = 0;
        while(recording){
            try{
                byte[] block = recorder.getBlock();
                packet = new DatagramPacket(
                        block, block.length, clientIP, PORT_NUMBER);
                sending_socket.send(packet);
				count ++;
            }
            catch(Exception e){
                e.printStackTrace();
            }
            
        }
        recorder.close();
    }
}
