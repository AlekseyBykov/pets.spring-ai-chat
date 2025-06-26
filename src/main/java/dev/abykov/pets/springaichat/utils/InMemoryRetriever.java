package dev.abykov.pets.springaichat.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InMemoryRetriever {

    private final List<String> chunks = new ArrayList<>();

    public void add(List<String> newChunks) {
        chunks.addAll(newChunks);
    }

    public List<String> findRelevantChunks(String query) {
        String[] words = query.toLowerCase().split("\\W+");
        return chunks.stream()
                .filter(chunk -> {
                    String lc = chunk.toLowerCase();
                    return Arrays.stream(words).anyMatch(lc::contains);
                })
                .limit(3)
                .toList();
    }
}
