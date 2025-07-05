package com.kowalczyk.konrad.ai.assistant;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface MathAssistant {

    @UserMessage("Please calculate: {{expression}}")
    String calculate(@V("expression") String expression);
}
