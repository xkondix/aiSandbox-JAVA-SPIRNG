package com.kowalczyk.konrad.ai.service;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiTokenCountEstimator;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static dev.langchain4j.data.message.UserMessage.userMessage;
import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O;

@Service
public class GemmaMemoryRedisService {
    @Qualifier("gemmaModel")
    private final OllamaChatModel model;
    private final ChatMemoryStore redis;

    public GemmaMemoryRedisService(@Qualifier("gemmaModel") OllamaChatModel model,
                                   ChatMemoryStore redis) {
        this.model = model;
        this.redis = redis;
    }

    public String chatWithRedis(String userId, String message) {
        ChatMemory memory = TokenWindowChatMemory.builder()
                .chatMemoryStore(redis)
                .id(userId)
                .maxTokens(1000, new OpenAiTokenCountEstimator(GPT_4_O))
                .build();
        memory.add(userMessage(message));
        AiMessage aiMessage = model.chat(memory.messages()).aiMessage();
        memory.add(aiMessage);
        return aiMessage.text();
    }

    public void removeUser(String userId) {
        redis.deleteMessages(userId);
    }
}
