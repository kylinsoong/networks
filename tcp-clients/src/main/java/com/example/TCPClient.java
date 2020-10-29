package com.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TCPClient implements CommandLineRunner {

	public static final String LOCAL_HOST = "127.0.0.1";
	public static final int ECHO_PORT = 8877;

	public static void main(String[] args) {
		SpringApplication.run(TCPClient.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		String host = LOCAL_HOST;
		int port = ECHO_PORT;	
		boolean isClose = false;
		
		for(int i = 0 ; i < args.length ; i++) {
			
			if(args[i].equals("--host")) {
				host = args[++i];
			} else if (args[i].equals("--port")) {
				port = Integer.parseInt(args[++i]);
			} else if (args[i].equals("--close"))  {
				isClose = true;
			} else if (args[i].equals("--help") || args[i].equals("-h")) {
				System.out.println("Run ");
				System.out.println("  java -jar tcp-clients.jar --host <Host> --port <Port> ");			
				System.exit(0);
			}
		}
		
		List<Socket> list = new ArrayList<>();
		
		while(true) {
			
			Scanner in = new Scanner(System.in);
			
			System.out.println("Entered 'interger' to continue or 'q' to quit");
			
			String s = in.nextLine();
			
			if(s.equals("q") || s.equals("quit")) {
				
				in.close();
				
				if(!isClose) {
					list.forEach(c -> {
						try {
							c.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
				}		
				break;
			}
			
			Integer num;
			try {
				num = Integer.parseInt(s);
			} catch (NumberFormatException e) {
				num = 1;
			}
			
			System.out.println("create " + num + " connections");
			for(int i = 0 ; i < num ; i ++ ) {
				SocketAddress address = new InetSocketAddress(host, port);
				Socket socket = new Socket();
				socket.connect(address, 2000);
				if(isClose) {
					socket.close();
				} else {
					list.add(socket);
				}
				
			}
			
			
		}
		

	}

	
}
