package com.example.visitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
// import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableFeignClients
// @EnableKafka
public class VisitorMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisitorMsApplication.class, args);
	}

}
