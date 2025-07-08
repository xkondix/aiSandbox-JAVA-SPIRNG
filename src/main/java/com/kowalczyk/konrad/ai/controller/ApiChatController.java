package com.kowalczyk.konrad.ai.controller;

import com.kowalczyk.konrad.ai.service.GPTService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiChatController {
    private final GPTService gptModelService;

    public ApiChatController(GPTService gptModelService) {
        this.gptModelService = gptModelService;
    }

    @PostMapping
    @RequestMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody String userInput) {
        return ResponseEntity.ok(gptModelService.chat(userInput));
    }

}
