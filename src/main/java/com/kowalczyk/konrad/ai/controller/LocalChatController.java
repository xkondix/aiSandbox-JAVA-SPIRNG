package com.kowalczyk.konrad.ai.controller;

import com.kowalczyk.konrad.ai.model.ArtistPojo;
import com.kowalczyk.konrad.ai.model.ChatRAGPojo;
import com.kowalczyk.konrad.ai.model.ChatRequestPojo;
import com.kowalczyk.konrad.ai.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/local")
public class LocalChatController {

    private final GemmaService gemmaService;
    private final GemmaMemoryService gemmaMemoryService;
    private final GemmaMemoryRedisService gemmaRedisService;
    private final GemmaMemoryRAGService gemmaRAGService;
    private final LlamaMathService llamaMathService;
    private final LlamaArtistService llamaArtistService;


    public LocalChatController(GemmaService gemmaService, GemmaMemoryService gemmaMemoryService,
                               GemmaMemoryRedisService gemmaRedisService, GemmaMemoryRAGService gemmaRAGService,
                               LlamaMathService llamaMathService, LlamaArtistService llamaArtistService) {
        this.gemmaService = gemmaService;
        this.gemmaMemoryService = gemmaMemoryService;
        this.gemmaRedisService = gemmaRedisService;
        this.gemmaRAGService = gemmaRAGService;
        this.llamaMathService = llamaMathService;
        this.llamaArtistService = llamaArtistService;
    }

    @PostMapping
    @RequestMapping("/chat")
    public String chat(@RequestBody String message) {
        return gemmaService.chat(message);
    }

    @PostMapping
    @RequestMapping("/chat/memory")
    public String chatMemory(@RequestBody String message) {
        return gemmaMemoryService.chatWithHistory(message);
    }

    @PostMapping
    @RequestMapping("/chat/memory/redis")
    public String chatMemoryRedis(@RequestBody ChatRequestPojo body) {
        return gemmaRedisService.chatWithRedis(body.username(), body.message());
    }

    @PostMapping
    @RequestMapping("/chat/memory/redis/assistant")
    public String chatMemoryRedisAiAssistant(@RequestBody ChatRequestPojo body) {
        return gemmaRedisService.chatWithRedisAiAssistant(body.username(), body.message());
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

    @PostMapping
    @RequestMapping("/chat/math")
    public ResponseEntity<String> chatWithMathCalculator(@RequestBody String message) {
        return ResponseEntity.ok(llamaMathService.chatMathCalc(message));
    }

    @PostMapping
    @RequestMapping("/chat/story")
    public ResponseEntity<String> chatWithStoryContext(@RequestBody ArtistPojo pojo) {
        return ResponseEntity.ok(llamaArtistService.generateStory(pojo.topic(), pojo.lines()));
    }

    @PostMapping
    @RequestMapping("/chat/story/message")
    public ResponseEntity<String> chatWithStoryContext(@RequestBody String message) {
        return ResponseEntity.ok(llamaArtistService.generateStoryMessage(message));
    }
}
