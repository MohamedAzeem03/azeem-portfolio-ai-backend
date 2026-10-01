package com.azeem.portfolio_ai_backend;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PortfolioAiBackendApplication {

	public static void main(String[] args) {
		// Load .env variables and inject them into System properties so Spring can read them
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		SpringApplication.run(PortfolioAiBackendApplication.class, args);
	}

}
