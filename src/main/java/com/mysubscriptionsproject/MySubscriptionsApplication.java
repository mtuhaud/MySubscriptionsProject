package com.mysubscriptionsproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MySubscriptionsApplication {

	public static void main(String[] args) {
		System.setProperty("spring.profiles.default", "dev");
		SpringApplication.run(MySubscriptionsApplication.class, args);
		System.out.println("Console h2 : " + "http://localhost:8081/h2-console/");
	}

}
