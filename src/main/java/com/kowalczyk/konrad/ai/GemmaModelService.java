package com.kowalczyk.konrad.ai;

import dev.langchain4j.model.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

@Service
public class GemmaModelService {

    private final OllamaChatModel model;

    public GemmaModelService() {
        this.model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("gemma3:12b")
                .build();
    }

    public String chat(String message) {
        return model.chat(message);
    }
}
