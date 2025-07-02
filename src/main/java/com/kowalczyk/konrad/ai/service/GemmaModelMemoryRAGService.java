package com.kowalczyk.konrad.ai.service;

import com.kowalczyk.konrad.ai.assistant.Assistant;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class GemmaModelMemoryRAGService {
    private final Assistant assistantMusic;
    private final Assistant assistantStory;
    private final Assistant assistantDefault;
    private final Assistant assistantRetriver;


    public GemmaModelMemoryRAGService(OllamaChatModel gemmaChatModelProvider, ContentRetriever retriever,
                                      @Qualifier("music") RetrievalAugmentor augmentorMusic,
                                      @Qualifier("story") RetrievalAugmentor augmentorStory,
                                      @Qualifier("default") RetrievalAugmentor augmentorDefault) {

        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

        this.assistantRetriver = AiServices.builder(Assistant.class)
                .chatModel(gemmaChatModelProvider)
                .contentRetriever(retriever)
                .chatMemory(chatMemory)
                .build();

        this.assistantMusic = AiServices.builder(Assistant.class)
                .chatModel(gemmaChatModelProvider)
                .retrievalAugmentor(augmentorMusic)
                .chatMemory(chatMemory)
                .build();

        this.assistantStory = AiServices.builder(Assistant.class)
                .chatModel(gemmaChatModelProvider)
                .retrievalAugmentor(augmentorStory)
                .chatMemory(chatMemory)
                .build();

        this.assistantDefault = AiServices.builder(Assistant.class)
                .chatModel(gemmaChatModelProvider)
                .retrievalAugmentor(augmentorDefault)
                .chatMemory(chatMemory)
                .build();
    }

    public String chatWithHistory(String message) {
        return assistantRetriver.chat(message);
    }

    public String chatWithStory(String message) {
        return assistantStory.chat(message);
    }


}
