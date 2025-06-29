package com.kowalczyk.konrad.ai.service;

import dev.langchain4j.model.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

@Service
public class GemmaModelService {

    private final OllamaChatModel gemmaChatModelProvider;

    public GemmaModelService(OllamaChatModel gemmaChatModelProvider) {
        this.gemmaChatModelProvider = gemmaChatModelProvider;
    }

    public String chat(String message) {
        return gemmaChatModelProvider.chat(message);
    }
}
