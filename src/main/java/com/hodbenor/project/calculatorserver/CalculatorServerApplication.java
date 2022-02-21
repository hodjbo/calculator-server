package com.hodbenor.project.calculatorserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CalculatorServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalculatorServerApplication.class, args);
	}
}
