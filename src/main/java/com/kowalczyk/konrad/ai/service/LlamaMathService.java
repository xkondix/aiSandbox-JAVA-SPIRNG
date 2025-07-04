package com.kowalczyk.konrad.ai.service;

import com.kowalczyk.konrad.ai.assistant.MathAssistant;
import com.kowalczyk.konrad.ai.component.MathToolComponent;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LlamaMathService {

    private final MathAssistant assistant;

    public LlamaMathService(@Qualifier("llamaModel") OllamaChatModel llamaChatModel, MathToolComponent toolComponent) {

        this.assistant = AiServices.builder(MathAssistant.class)
                .chatModel(llamaChatModel)
                .tools(toolComponent)
                .build();
    }
    public String chatMathCalc(String message) {
        return assistant.calculate(message);
    }
}
