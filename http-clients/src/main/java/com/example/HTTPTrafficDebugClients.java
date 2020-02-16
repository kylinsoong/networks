package com.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.ResponseInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HTTPTrafficDebugClients implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(HTTPTrafficDebugClients.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		if(args.length < 2 ) {
			System.out.println("Run with paramters: java -jar http-clients.jar <URI> <Times> <Threads>");
			System.out.println("                    <URI> - HTTP request URL, eg, http://example.com/hello");
			System.out.println("                    <Times> - The total clients in one threads");
			System.out.println("                    <Threads> - The total threads will run the http call, this is opertional");
			System.exit(0);
		}
		
		String uri = args[0];
		int times = Integer.parseInt(args[1]);
		int threads = 1;
		if(args.length == 3) {
			threads = Integer.parseInt(args[2]);
		}
		
		if(threads > 1) {
			//TODO-- handle multiple threads
		} else {
						
			for(int i = 1 ; i <= times ; i ++) {
				
				HttpClient httpClient = HttpClient.newBuilder()
			            .version(HttpClient.Version.HTTP_1_1)
			            .build();
				
//				lists.add(httpClient);
				
				HttpRequest request = HttpRequest.newBuilder()
		                .GET()
		                .uri(URI.create(uri))
		                .setHeader("User-Agent", "Java 13 HttpClient Bot")
		                .build();
				
				HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
				
				System.out.println("request " + i + ", " + uri);
				// short wait
				Thread.sleep(100);
				System.out.println(response);
				System.out.println("Request Headers: " + response.request().headers());
				System.out.println("Response Headers: " + response.headers());
				System.out.println(response.body());
				System.out.println();
			}
			
			
			System.out.println("\nPress \"ENTER\" to continue...");
			Scanner scanner = new Scanner(System.in);
			scanner.nextLine();
			scanner.close();
			
		}
		
	}

	
}
