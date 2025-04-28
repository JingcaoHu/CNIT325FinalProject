package com.ai_assistant.api.controller;

import com.ai_assistant.api.model.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    @PostMapping("/ask")
    public String askAI(
            @RequestParam int userId,
            @RequestParam int selection,
            @RequestBody String question) {
        
        // Create prompt
        Prompt prompt = new Prompt(userId, selection, question, null);
        
        // Connect to AI (using your existing AIConnection)
        AIConnection aiConnection = new AIConnection();
        String aiAddress = "http://localhost:1234/v1/chat/completions"; // Your AI endpoint
        String response = aiConnection.runConnection(selection, question, aiAddress);
        
        // Save to database (using your existing InsertRecord)
        new InsertRecord().connectDatabase(prompt);
        
        return response;
    }
}