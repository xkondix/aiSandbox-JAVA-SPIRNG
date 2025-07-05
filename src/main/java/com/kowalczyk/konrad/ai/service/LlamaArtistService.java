package com.kowalczyk.konrad.ai.service;

import com.kowalczyk.konrad.ai.assistant.ArtistAssistant;
import com.kowalczyk.konrad.ai.component.ArtistToolComponent;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LlamaArtistService {

    private final ArtistAssistant assistant;

    public LlamaArtistService(@Qualifier("llamaModel") OllamaChatModel llamaChatModel, ArtistToolComponent artistTool) {
        this.assistant = AiServices.builder(ArtistAssistant.class)
                .chatModel(llamaChatModel)
                .tools(artistTool)
                .build();
    }

    public String generateStory(String topic, int lines) {
        return assistant.writeStoryAndSaveToFile(topic, lines);
    }

    public String generateStoryMessage(String message) {
        return assistant.writeStoryAboutTopic(message);
    }
}
