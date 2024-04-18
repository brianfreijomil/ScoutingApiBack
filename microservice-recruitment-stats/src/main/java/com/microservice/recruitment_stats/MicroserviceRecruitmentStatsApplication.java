package com.microservice.recruitment_stats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MicroserviceRecruitmentStatsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceRecruitmentStatsApplication.class, args);
	}

}
