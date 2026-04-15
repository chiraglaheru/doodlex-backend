package com.example.pawfeed;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.example.pawfeed")
public class PawfeedApplication {

	public static void main(String[] args) {
		SpringApplication.run(PawfeedApplication.class, args);
	}

}
