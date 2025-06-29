package com.kowalczyk.konrad.ai.configuration;

import dev.langchain4j.model.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
public class OllamaChatModelProvider {

    @Bean
    public OllamaChatModel gemmaChatModelProvider() {
        return OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("gemma3:4b")
                .build();
    }

}
