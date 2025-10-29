package com.example.instructions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
	info = @Info(
		title = "Trade Instructions API",
		version = "1.0",
		description = "API documentation for Trade Instructions"
	)
)
@SpringBootApplication
public class InstructionsCaptureApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstructionsCaptureApplication.class, args);
	}

}
