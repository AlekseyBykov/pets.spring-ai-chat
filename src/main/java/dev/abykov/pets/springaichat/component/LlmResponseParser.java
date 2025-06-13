package dev.abykov.pets.springaichat.component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class LlmResponseParser {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String extractResponse(String rawJson) {
        try {
            JsonNode root = objectMapper.readTree(rawJson);
            return root.path("response").asText();
        } catch (Exception e) {
            return "Error parsing response from LLM: %s".formatted(e.getMessage());
        }
    }
}
