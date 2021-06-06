package io.cloudadc.sockets;

import java.io.InputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

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
	public void run(String... args) throws Exception {
		
		int port = 0;
		
		if (args.length == 1) {
			port = Integer.parseInt(args[0]);
        }
		
		if(port == 0) {
			port = 9905;
		}
		
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			
			System.out.println("Server started: " + serverSocket);
			
			while(true) {
				Socket socket = serverSocket.accept();
				System.out.println("Receive a docket: " + socket);
				while(true) {
					
					InputStream in = socket.getInputStream();
					byte[] buf = new byte[2048];
					in.read(buf);
					
					int length = 0;
					for(int i = 0 ; i < 2048 ; i ++) {
						if(buf[i] == 0) {
							break;
						}
						length ++;
						
					}
					
					byte[] rBuf = Arrays.copyOf(buf, length);
					if(length == 0) {
						break;
					} 
					
					
					System.out.println("\n[" + socket + "] Received bytes, length: [" + rBuf.length + "], data: \n" + new BigInteger(rBuf).toString());
					
				}
			}
		} finally {
			if(serverSocket != null) {
				serverSocket.close();
			}
		}
		
		
        
	}
	

}
