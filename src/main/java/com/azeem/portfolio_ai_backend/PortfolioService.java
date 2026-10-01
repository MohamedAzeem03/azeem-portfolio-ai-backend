package com.azeem.portfolio_ai_backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class PortfolioService {

    @Value("${portfolio.data.path:../portfolio_content/portfolio.json}")
    private String portfolioDataPath;

    private String rawJson;

    public PortfolioService() {
    }

    @PostConstruct
    public void init() throws IOException {
        loadPortfolioData();
    }

    public void loadPortfolioData() throws IOException {
        File file = new File(portfolioDataPath);
        if (file.exists()) {
            System.out.println("Reading file from: " + file.getAbsolutePath());
            this.rawJson = new String(Files.readAllBytes(Paths.get(portfolioDataPath)));
            System.out.println("Read " + this.rawJson.length() + " bytes. Starts with: " + this.rawJson.substring(0, Math.min(this.rawJson.length(), 50)));
        } else {
            throw new RuntimeException("Portfolio JSON file not found at: " + file.getAbsolutePath());
        }
    }

    public String getFullPortfolio() {
        return rawJson;
    }
}
