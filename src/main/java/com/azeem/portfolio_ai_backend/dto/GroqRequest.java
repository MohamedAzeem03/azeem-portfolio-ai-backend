package com.azeem.portfolio_ai_backend.dto;

import java.util.List;

public class GroqRequest {
    private String model;
    private List<GroqMessage> messages;
    private double temperature;
    private int max_tokens;

    public GroqRequest() {}

    public GroqRequest(String model, List<GroqMessage> messages, double temperature, int max_tokens) {
        this.model = model;
        this.messages = messages;
        this.temperature = temperature;
        this.max_tokens = max_tokens;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<GroqMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<GroqMessage> messages) {
        this.messages = messages;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getMax_tokens() {
        return max_tokens;
    }

    public void setMax_tokens(int max_tokens) {
        this.max_tokens = max_tokens;
    }
}
