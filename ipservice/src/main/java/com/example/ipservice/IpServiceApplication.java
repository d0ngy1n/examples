package com.example.ipservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class IpServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IpServiceApplication.class, args);
	}

}
