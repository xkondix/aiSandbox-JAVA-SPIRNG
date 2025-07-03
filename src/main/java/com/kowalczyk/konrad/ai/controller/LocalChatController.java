package com.kowalczyk.konrad.ai.controller;

import com.kowalczyk.konrad.ai.model.ChatRAGPojo;
import com.kowalczyk.konrad.ai.model.ChatRequestPojo;
import com.kowalczyk.konrad.ai.service.GemmaModelMemoryRAGService;
import com.kowalczyk.konrad.ai.service.GemmaModelMemoryRedisService;
import com.kowalczyk.konrad.ai.service.GemmaModelMemoryService;
import com.kowalczyk.konrad.ai.service.GemmaModelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/local")
public class LocalChatController {

    private final GemmaModelService gemmaService;
    private final GemmaModelMemoryService gemmaMemoryService;
    private final GemmaModelMemoryRedisService gemmaRedisService;
    private final GemmaModelMemoryRAGService gemmaRAGService;


    public LocalChatController(GemmaModelService gemmaService, GemmaModelMemoryService gemmaMemoryService,
                               GemmaModelMemoryRedisService gemmaRedisService, GemmaModelMemoryRAGService gemmaRAGService) {
        this.gemmaService = gemmaService;
        this.gemmaMemoryService = gemmaMemoryService;
        this.gemmaRedisService = gemmaRedisService;
        this.gemmaRAGService = gemmaRAGService;
    }

    @PostMapping
    @RequestMapping("/chat")
    public String chat(@RequestBody String userInput) {
        return gemmaService.chat(userInput);
    }

    @PostMapping
    @RequestMapping("/chat/memory")
    public String chatMemory(@RequestBody String userInput) {
        return gemmaMemoryService.chatWithHistory(userInput);
    }

    @PostMapping
    @RequestMapping("/chat/memory/redis")
    public String chatMemoryRedis(@RequestBody ChatRequestPojo body) {
        return gemmaRedisService.chatWithRedis(body.username(), body.message());
    }

    @DeleteMapping
    @RequestMapping("/chat/memory/redis/{userId}")
    public ResponseEntity<String> removeRedisUser(@PathVariable String userId) {
        gemmaRedisService.removeUser(userId);
        return ResponseEntity.ok(String.format("User %s has been removed", userId));
    }

    @PostMapping
    @RequestMapping("/chat/rag/metadata")
    public ResponseEntity<String> chatWithRagMetadata(@RequestBody ChatRAGPojo chatRAGPojo) {
        return ResponseEntity.ok(gemmaRAGService.chatWithMetadata(chatRAGPojo));
    }

    @PostMapping
    @RequestMapping("/chat/rag")
    public ResponseEntity<String> chatWithRag(@RequestBody ChatRAGPojo chatRAGPojo) {
        return ResponseEntity.ok(gemmaRAGService.chatWithoutMetadata(chatRAGPojo));
    }
}
