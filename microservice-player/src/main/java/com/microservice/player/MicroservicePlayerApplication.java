package com.microservice.player;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MicroservicePlayerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicePlayerApplication.class, args);
	}

}
