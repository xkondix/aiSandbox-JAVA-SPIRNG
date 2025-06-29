package com.kowalczyk.konrad.ai.controller;

import com.kowalczyk.konrad.ai.service.GemmaModelHistoryService;
import com.kowalczyk.konrad.ai.service.GemmaModelService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final GemmaModelService chatService;
    private final GemmaModelHistoryService gemmaModelHistoryService;

    public ChatController(GemmaModelService chatService, GemmaModelHistoryService gemmaModelHistoryService) {
        this.chatService = chatService;
        this.gemmaModelHistoryService = gemmaModelHistoryService;
    }

    @PostMapping
    @RequestMapping("/chat")
    public String chat(@RequestBody String userInput) {
        return chatService.chat(userInput);
    }

    @PostMapping
    @RequestMapping("/chat/memory")
    public String chatMemory(@RequestBody String userInput) {
        return gemmaModelHistoryService.chatWithHistory(userInput);
    }
}
