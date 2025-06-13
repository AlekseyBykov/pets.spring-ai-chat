package dev.abykov.pets.springaichat.service;

import dev.abykov.pets.springaichat.component.LlmClient;
import dev.abykov.pets.springaichat.model.ChatRequest;
import dev.abykov.pets.springaichat.model.ChatResponse;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final LlmClient llmClient;

    public ChatService(LlmClient llmClient) {
        this.llmClient = llmClient;
    }

    public ChatResponse chat(ChatRequest request) {
        return llmClient.chat(request);
    }
}
