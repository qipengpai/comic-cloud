package com.qpp.qiniuserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class QiniuServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(QiniuServerApplication.class, args);
	}
}
