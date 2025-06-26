package dev.abykov.pets.springaichat.utils;

import java.util.ArrayList;
import java.util.List;

public class TextChunker {

    public List<String> chunk(String text) {
        int maxLength = 500;
        List<String> result = new ArrayList<>();
        String[] sentences = text.split("(?<=[.!?])\\s+");

        StringBuilder current = new StringBuilder();
        for (String sentence : sentences) {
            if (current.length() + sentence.length() > maxLength) {
                result.add(current.toString());
                current = new StringBuilder();
            }
            current.append(sentence).append(" ");
        }
        if (!current.isEmpty()) {
            result.add(current.toString());
        }

        return result;
    }
}
