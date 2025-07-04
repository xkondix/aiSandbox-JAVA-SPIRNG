package com.kowalczyk.konrad.ai.service;

import com.kowalczyk.konrad.ai.assistant.Assistant;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class GemmaMemoryService {

    private final Assistant assistant;

    public GemmaMemoryService(@Qualifier("gemmaModel") OllamaChatModel gemmaChatModel) {
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

        this.assistant = AiServices.builder(Assistant.class)
                .chatModel(gemmaChatModel)
                .chatMemory(chatMemory)
                .build();
    }
    public String chatWithHistory(String message) {
        return assistant.chat(message);
    }
}
