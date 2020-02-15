package com.example;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HTTPTrafficDebugClient implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(HTTPTrafficDebugClient.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		String uri = "http://10.1.10.20/hello";
		if(args.length == 1) {
			uri = args[0];	
		}

		System.out.println("http client start");
		HttpClient client = HttpClient.newHttpClient();
	    HttpRequest request = HttpRequest.newBuilder()
	          .uri(URI.create(uri))
	          .build();

	    HttpResponse<String> response =
	          client.send(request, BodyHandlers.ofString());

	    System.out.println(response.headers());
	    System.out.println(response.body());
		
	}

	
}
