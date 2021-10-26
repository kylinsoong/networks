package io.cloudadc.sockets;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	public void run(String... args)  {
		
		
		
		if (args.length < 3) {
            System.err.println("USAGE: <IP> <PORT> <COUNT>");
            System.exit(2);
        }
        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        int count = Integer.parseInt(args[2]);
        
        List<Socket> list = new ArrayList<>();
        for (int i = 0 ; i < count ; i ++) {
        	SocketAddress address = new InetSocketAddress(ip, port);
        	Socket socket = new Socket();
            try {
				socket.connect(address, 2000);
				System.out.println("open a sockets: " + new Entity(i, socket) );
	            list.add(socket);
			} catch (IOException e) {
				Set<Integer> sets = new HashSet<>();
				list.forEach(s -> {
					sets.add(Integer.valueOf(s.getLocalPort()));
				});
				System.out.println(sets);
				e.printStackTrace();
				break;
			}
            
        }
        
        pressToContinue();
        
        System.out.println(list.size() + " sockets will be closed");
        list.forEach(s -> {
        	try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
        
        
        list = null;
        
        
	}

	void pressToContinue()  {

		System.out.println("\nPress any key to continue...");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	

}
