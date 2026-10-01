package com.azeem.portfolio_ai_backend;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<String> getFullPortfolio() {
        return ResponseEntity.ok(portfolioService.getFullPortfolio());
    }

    @GetMapping("/test")
    public String test() {
        return "Controller is working!";
    }
}