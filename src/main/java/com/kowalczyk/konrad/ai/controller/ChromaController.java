package com.kowalczyk.konrad.ai.controller;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChromaController {
    private final EmbeddingStore<TextSegment> store;

    public ChromaController(EmbeddingStore<TextSegment> store) {
        this.store = store;
    }

    @DeleteMapping("/chroma/clear")
    public ResponseEntity<String> clearChromaStore() {
        store.removeAll();
        return ResponseEntity.ok("Chroma collection cleared.");
    }
}
