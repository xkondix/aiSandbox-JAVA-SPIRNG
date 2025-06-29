package com.kowalczyk.konrad.ai.service;

import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Service
public class GPTModelService {

    private final OpenAiChatModel gptChatModel;

    public GPTModelService(OpenAiChatModel gptChatModel) {
        this.gptChatModel = gptChatModel;
    }

    public String chat(String message) {
        return gptChatModel.chat(message);
    }

}
