package com.kowalczyk.konrad.ai;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final GemmaModelService chatService;

    public ChatController(GemmaModelService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public String chat(@RequestBody String userInput) {
        return chatService.chat(userInput);
    }
}
