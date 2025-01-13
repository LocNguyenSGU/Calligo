package com.example.friendservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.example.commonservice","com.example.friendservice"})
public class FriendserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FriendserviceApplication.class, args);
	}

}
