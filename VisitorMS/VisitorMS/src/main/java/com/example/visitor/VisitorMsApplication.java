package com.example.visitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VisitorMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisitorMsApplication.class, args);
	}

}
