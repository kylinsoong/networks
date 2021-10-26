package io.cloudadc.sockets;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Override
	public void run(String... args)  throws Exception{
		
		
		DatagramSocket socket = new DatagramSocket(1117);
		 
		byte[] buffer = new byte[256];
		 
		while(true) {
			DatagramPacket request = new DatagramPacket(buffer, buffer.length);
			socket.receive(request);
			
			String cur = new String(buffer, 0, request.getLength());
		    System.out.println(cur);
		    
		    InetAddress clientAddress = request.getAddress();
		    int clientPort = request.getPort();
		    
		    String data = "The knowledge that you have emerged wiser and stronger from setbacks means that you are, ever after, secure in your ability to survive. \nYou will never truly know yourself, or the strength of your relationships, until both have been tested by adversity. \n\nWe do not need magic to change the world, we carry all the power we need inside ourselves already: we have the power to imagine better.";
		    buffer = data.getBytes();
		    
		    DatagramPacket response = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
		    socket.send(response);
		}
		
		
        
	}
	

}
