package com.qpp.registryha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RegistryHaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistryHaApplication.class, args);
	}
}
