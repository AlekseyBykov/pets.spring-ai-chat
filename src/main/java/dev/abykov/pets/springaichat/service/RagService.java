package dev.abykov.pets.springaichat.service;

import dev.abykov.pets.springaichat.utils.InMemoryRetriever;
import dev.abykov.pets.springaichat.utils.TextChunker;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class RagService {

    private final TextChunker chunker = new TextChunker();
    private final InMemoryRetriever retriever = new InMemoryRetriever();

    public void processFile(MultipartFile file) throws IOException {
        String text = new String(file.getBytes(), StandardCharsets.UTF_8);
        List<String> chunks = chunker.chunk(text);
        retriever.add(chunks);
    }
}
