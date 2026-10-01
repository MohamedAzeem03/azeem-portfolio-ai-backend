package com.azeem.portfolio_ai_backend.controller;

import com.azeem.portfolio_ai_backend.dto.ChatRequest;
import com.azeem.portfolio_ai_backend.dto.ChatResponse;
import com.azeem.portfolio_ai_backend.service.GroqService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    private final GroqService groqService;

    public ChatController(GroqService groqService) {
        this.groqService = groqService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        String answer = groqService.askQuestion(request.getMessage());
        return ResponseEntity.ok(new ChatResponse(answer));
    }
}
