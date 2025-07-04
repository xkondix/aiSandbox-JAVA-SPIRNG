package com.kowalczyk.konrad.ai.service;

import dev.langchain4j.model.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class GemmaService {
    @Qualifier("gemmaModel")
    private final OllamaChatModel gemmaChatModel;

    public GemmaService(@Qualifier("gemmaModel") OllamaChatModel gemmaChatModel) {
        this.gemmaChatModel = gemmaChatModel;
    }
    public String chat(String message) {
        return gemmaChatModel.chat(message);
    }
}
