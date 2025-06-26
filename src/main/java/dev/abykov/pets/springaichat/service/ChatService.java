package dev.abykov.pets.springaichat.service;

import dev.abykov.pets.springaichat.component.LLMClient;
import dev.abykov.pets.springaichat.model.ChatRequest;
import dev.abykov.pets.springaichat.model.ChatResponse;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final LLMClient llmClient;

    public ChatService(LLMClient llmClient) {
        this.llmClient = llmClient;
    }

    public ChatResponse chat(ChatRequest request) {
        return llmClient.chat(request);
    }
}
