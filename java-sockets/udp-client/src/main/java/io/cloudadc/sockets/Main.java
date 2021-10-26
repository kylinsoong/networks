package io.cloudadc.sockets;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.net.ssl.SSLSocketFactory;

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
		
		if (args.length < 2) {
            System.out.println("Syntax: java -jar udp-client.jar <hostname> <port>");
            return;
        }
		
		String ip = args[0];
		int port = Integer.parseInt(args[1]);
		
		InetAddress address = InetAddress.getByName(ip);
		DatagramSocket socket = new DatagramSocket();
		
		int id = 0;
		while(true) {
			String cur = String.valueOf(++id);
			DatagramPacket request = new DatagramPacket(cur.getBytes(), cur.length(), address, port);
		    socket.send(request);
		    
		    byte[] buffer = new byte[512];
		    DatagramPacket response = new DatagramPacket(buffer, buffer.length);
		    socket.receive(response);
		    
		    String quote = new String(buffer, 0, response.getLength());
		    System.out.println(quote);
		    System.out.println();
		    Thread.sleep(1000 * 30);
		}
        
	}
	
	static void tmp () {
		
		SSLSocketFactory factory =(SSLSocketFactory)SSLSocketFactory.getDefault();
	}
	

}
