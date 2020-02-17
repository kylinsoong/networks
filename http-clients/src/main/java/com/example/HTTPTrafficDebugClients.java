package com.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
		
		String uri = null;
		int times = 1;
		int threads = 1;
		boolean cookie = false;
		boolean print = false;
		
		for(int i = 0 ; i < args.length ; i++) {
			
			if(args[i].equals("--uri")) {
				uri = args[++i];
			} else if (args[i].equals("--times")) {
				times = Integer.parseInt(args[++i]);
			} else if (args[i].equals("--threads")) {
				threads = Integer.parseInt(args[++i]);
			} else if (args[i].equals("--cookie")) {
				cookie = true;
			} else if (args[i].equals("--print")) {
				print = true;
			}
		}
		
		if(uri == null) {
			System.out.println("request uri is necessary");
			System.out.println("  java -jar http-clients.jar --uri <URI> --times <Times> --threads <Threads> --cookie --print");			
			System.exit(0);
		}
		
		if(threads > 1) {
			//TODO-- handle multiple threads
		} else {
						
			HttpHeaders headers = null;
			
			for(int i = 1 ; i <= times ; i ++) {
				
				HttpClient httpClient = HttpClient.newBuilder()
			            .version(HttpClient.Version.HTTP_1_1)
			            .build();
								
				HttpRequest request = HttpRequest.newBuilder()
		                .GET()
		                .uri(URI.create(uri))
		                .setHeader("User-Agent", "Bot")
		                .build();
				
				if (cookie && headers != null) {
					HttpRequest.Builder builder = HttpRequest.newBuilder().GET().uri(URI.create(uri));
					headers.map().forEach((k, v) -> {
						System.out.println(k + " -> " + v);
					});
				}
				
				if(print) {
					System.out.println("request " + i + ", " + uri);
				}
				
				// short wait
				Thread.sleep(100);
	
				HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
				
				headers = response.request().headers();
				
				if(print) {
					System.out.println(response);
					System.out.println("Request  Headers: " + headers.map());
					System.out.println("Response Headers: " + response.headers().map());
				}
				
				System.out.println(response.body());
				
				if(print) {
					System.out.println();
				}
			}
			
			
			System.out.println("\nPress \"ENTER\" to continue...");
			Scanner scanner = new Scanner(System.in);
			scanner.nextLine();
			scanner.close();
			
		}
		
	}

	
}
