package com.kowalczyk.konrad.ai.configuration;

import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GPTChatModelProvide {

    @Value("${OPENAI_API_KEY}")
    String key;

    @Bean
    public OpenAiChatModel gptChatModel() {
        return OpenAiChatModel.builder()
                .apiKey(key)
                .modelName("gpt-4o")
                .logRequests(true)
                .logResponses(true)
                .build();
    }

}
