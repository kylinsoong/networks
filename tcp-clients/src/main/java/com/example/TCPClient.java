package com.example;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
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
		boolean reset = false;
		boolean activeClose = false;
		
		for(int i = 0 ; i < args.length ; i++) {
			
			if(args[i].equals("--host")) {
				host = args[++i];
			} else if (args[i].equals("--port")) {
				port = Integer.parseInt(args[++i]);
			} else if (args[i].equals("--rest")) {
				reset = true;
			} else if (args[i].equals("--active")) {
				activeClose = true;
			} else if (args[i].equals("--help") || args[i].equals("-h")) {
				System.out.println("Run ");
				System.out.println("  java -jar tcp-clients.jar --host <Host> --port <Port> ");			
				System.exit(0);
			}
		}
		
		if (reset) {
			//TODO--
		}
		
		if(activeClose) {
			
			SocketAddress address = new InetSocketAddress(host, port);
			Socket socket = new Socket();
			socket.connect(address, 2000);
			
			Thread.sleep(5 * 1000);
			
			socket.close();
			
		} else {

                        SocketAddress address = new InetSocketAddress(host, port);
                        Socket socket = new Socket();
                        socket.connect(address, 2000);
                }

		System.out.println("\nPress \"ENTER\" to continue...");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();			
		scanner.close();

	}

	
}
