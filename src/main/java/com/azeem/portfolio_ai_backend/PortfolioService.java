package com.azeem.portfolio_ai_backend;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class PortfolioService {

    private String rawJson;

    @PostConstruct
    public void init() throws IOException {
        loadPortfolioData();
    }

    public void loadPortfolioData() throws IOException {

        ClassPathResource resource =
                new ClassPathResource("data/portfolio.json");

        try (InputStream inputStream = resource.getInputStream()) {

            this.rawJson = new String(
                    inputStream.readAllBytes(),
                    StandardCharsets.UTF_8
            );

            System.out.println(
                    "Read " + this.rawJson.length() + " bytes."
            );
        }
    }

    public String getFullPortfolio() {
        return rawJson;
    }
}