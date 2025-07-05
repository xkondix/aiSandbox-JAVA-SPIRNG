package com.kowalczyk.konrad.ai.service;

import com.kowalczyk.konrad.ai.assistant.Assistant;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiTokenCountEstimator;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static dev.langchain4j.data.message.UserMessage.userMessage;
import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O;

@Service
public class GemmaMemoryRedisService {

    private static final Logger log = LoggerFactory.getLogger(GemmaMemoryRedisService.class);
    private final Map<String, Assistant> assistantCache = new ConcurrentHashMap<>();

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
        ChatResponse response = model.chat(memory.messages());
        log.info("token usage = {}", response.tokenUsage());
        AiMessage aiMessage = response.aiMessage();
        memory.add(aiMessage);
        return aiMessage.text();
    }

    public String chatWithRedisAiAssistant(String userId, String message) {
        return assistantCache.computeIfAbsent(userId, id -> {
            ChatMemory memory = TokenWindowChatMemory.builder()
                    .chatMemoryStore(redis)
                    .id(userId)
                    .maxTokens(1000, new OpenAiTokenCountEstimator(GPT_4_O))
                    .build();

            return AiServices.builder(Assistant.class)
                    .chatModel(model)
                    .chatMemory(memory)
                    .build();
        }).chat(message);
    }

    public void removeUser(String userId) {
        redis.deleteMessages(userId);
    }
}
