package com.kowalczyk.konrad.ai.assistant;

import dev.langchain4j.service.UserMessage;
public interface MathAssistant {
    @UserMessage("Please calculate: {expression}")
    String calculate(String expression);
}
