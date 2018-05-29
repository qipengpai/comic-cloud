package com.qpp.wechatserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class WechatServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WechatServerApplication.class, args);
	}
}
