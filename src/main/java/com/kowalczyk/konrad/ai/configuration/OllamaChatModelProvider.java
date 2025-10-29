package com.kowalczyk.konrad.ai.configuration;

import dev.langchain4j.model.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class OllamaChatModelProvider {

    @Bean("gemmaModel")
    public OllamaChatModel gemmaChatModel() {
        return OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("gemma3:4b")
                .maxRetries(3)
                .timeout(Duration.ofSeconds(120))
                .logRequests(true)
                .logResponses(true)
                .build();
    }
    @Bean("llamaModel")
    public OllamaChatModel llamaChatModel() {
        return OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("llama3.1:8b")
                .logRequests(true)
                .logResponses(true)
                .build();
    }

}
