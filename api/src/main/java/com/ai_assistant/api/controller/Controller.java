package com.ai_assistant.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai_assistant.api.model.AIConnection;
import com.ai_assistant.api.model.Prompt;

@RestController
@RequestMapping("api")
public class Controller {
    AIConnection connection = new AIConnection();

    @PostMapping("/request")
    public String request(@RequestBody Prompt prompt) {
        return connection.runConnection(prompt.getSelection(), prompt.getContent(), "http://localhost:1234/v1/chat/completions");
    }
}
