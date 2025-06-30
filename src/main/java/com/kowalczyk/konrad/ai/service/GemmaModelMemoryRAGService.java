package com.kowalczyk.konrad.ai.service;

import com.kowalczyk.konrad.ai.assistant.Assistant;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;

public class GemmaModelMemoryRAGService {
    private final Assistant assistant;

    public GemmaModelMemoryRAGService(OllamaChatModel gemmaChatModelProvider, ContentRetriever retriever) {
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

        this.assistant = AiServices.builder(Assistant.class)
                .chatModel(gemmaChatModelProvider)
                .contentRetriever(retriever)
                .chatMemory(chatMemory)
                .build();
    }

    public String chatWithHistory(String message) {
        return assistant.chat(message);
    }

}
