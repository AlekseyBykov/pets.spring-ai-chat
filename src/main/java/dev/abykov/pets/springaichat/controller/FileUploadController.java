package dev.abykov.pets.springaichat.controller;

import dev.abykov.pets.springaichat.service.RagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/rag")
public class FileUploadController {

    private final RagService ragService;

    public FileUploadController(RagService ragService) {
        this.ragService = ragService;
    }

    @Operation(summary = "Upload a document for RAG")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File uploaded and indexed")
    })
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            ragService.processFile(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("Uploaded and indexed");
    }
}
