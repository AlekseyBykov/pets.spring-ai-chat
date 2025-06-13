package dev.abykov.pets.springaichat.controller;

import dev.abykov.pets.springaichat.model.ChatRequest;
import dev.abykov.pets.springaichat.model.ChatResponse;
import dev.abykov.pets.springaichat.service.ChatService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class AiController {

    private final ChatService chatService;

    public AiController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {
        return chatService.chat(request);
    }
}
