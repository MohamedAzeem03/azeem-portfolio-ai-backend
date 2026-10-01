package com.azeem.portfolio_ai_backend.service;

import com.azeem.portfolio_ai_backend.PortfolioService;
import com.azeem.portfolio_ai_backend.dto.GroqMessage;
import com.azeem.portfolio_ai_backend.dto.GroqRequest;
import com.azeem.portfolio_ai_backend.dto.GroqResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroqService {

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.api.url}")
    private String apiUrl;

    @Value("${groq.model}")
    private String model;

    private final RestTemplate restTemplate;
    private final PortfolioService portfolioService;

    public GroqService(RestTemplate restTemplate, PortfolioService portfolioService) {
        this.restTemplate = restTemplate;
        this.portfolioService = portfolioService;
    }

    public String askQuestion(String userMessage) {
        // Build the system prompt using portfolio context
        String portfolioContext = portfolioService.getFullPortfolio();
        String systemPrompt = "You are Mohamed Azeem's portfolio AI assistant. Answer questions using only the available portfolio information. Keep answers concise, natural, and easy to understand. Do not invent personal information or facts that are not present in the portfolio data.\n\n" +
                "CRITICAL INSTRUCTIONS:\n" +
                "1. Keep responses short: normally 1-4 sentences. Use short lists only when necessary.\n" +
                "2. Do not generate unnecessarily long explanations. Be concise and human-like.\n" +
                "3. Do not start every response with an introduction. ONLY state your name if the user explicitly asks for an introduction.\n" +
                "4. If a question is not supported by the portfolio data (e.g., personal judgments like 'Is Azeem a good boy?'), respond politely and briefly like: 'I can tell you about Azeem's projects, skills, education, experience, and achievements, but I don't have enough portfolio information to answer that.'\n" +
                "5. Clarify that Azeem is currently an MCA student. Treat listed internships as past/completed experiences; do NOT say he is 'currently working' there.\n\n" +
                "PORTFOLIO DATA:\n" + portfolioContext;

        // Construct messages
        List<GroqMessage> messages = new ArrayList<>();
        messages.add(new GroqMessage("system", systemPrompt));
        messages.add(new GroqMessage("user", userMessage));

        // Create Groq request payload with max_tokens set to 300 to prevent 429 errors
        GroqRequest requestPayload = new GroqRequest(model, messages, 0.4, 300);

        // Setup headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<GroqRequest> entity = new HttpEntity<>(requestPayload, headers);

        try {
            // Because the key might be the placeholder "groq_api_key", let's handle the potential Unauthorized exception
            // gracefully if testing locally without a real key.
            if ("groq_api_key".equals(apiKey)) {
                return "[Mock Mode] Since the GROQ_API_KEY environment variable is not set (using placeholder), the actual API call was skipped. " +
                        "However, your question was: '" + userMessage + "'. The context loaded from portfolio.json has " + portfolioContext.length() + " characters.";
            }

            GroqResponse response = restTemplate.postForObject(apiUrl, entity, GroqResponse.class);
            if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
                return response.getChoices().get(0).getMessage().getContent();
            } else {
                return "Sorry, I'm unable to answer right now. Please try again.";
            }
        } catch (Exception e) {
            // Log the actual error to console for debugging, but return friendly message to user
            System.err.println("Groq API Error: " + e.getMessage());
            return "Sorry, I'm unable to answer right now. Please try again.";
        }
    }
}
