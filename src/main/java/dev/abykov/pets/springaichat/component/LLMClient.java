package dev.abykov.pets.springaichat.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.abykov.pets.springaichat.model.ChatRequest;
import dev.abykov.pets.springaichat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class LLMClient {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    private final LLMResponseParser responseParser;
    private final String baseUrl;
    private final String model;

    public LLMClient(
            LLMResponseParser responseParser,
            @Value("${ai.base-url}") String baseUrl,
            @Value("${ai.model}") String model
    ) {
        this.responseParser = responseParser;
        this.baseUrl = baseUrl;
        this.model = model;
    }

    public ChatResponse chat(ChatRequest request) {
        String url = baseUrl + "/api/generate";

        Map<String, Object> payload = makePayload(request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class
        );

        String answer = responseParser.extractResponse(response.getBody());
        return new ChatResponse(answer);
    }

    private Map<String, Object> makePayload(ChatRequest request) {
        return Map.of(
                "model", model,
                "prompt", request.prompt(),
                "stream", false
        );
    }
}
