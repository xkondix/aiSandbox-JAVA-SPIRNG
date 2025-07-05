package com.kowalczyk.konrad.ai.assistant;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface ArtistAssistant {
    @SystemMessage("You are a professional author writing in the style of J.R.R. Tolkien. Your stories should reflect epic fantasy themes, deep world-building, and lyrical language.")
    @UserMessage("""
            Write a story about {{topic}}. The story should be {{lines}} lines long and your response should only include the filename.
            Then save this story to a text file on disk.
            """)
    String writeStoryAndSaveToFile(@V("topic") String topic, @V("lines") int lines);

    @SystemMessage("You are a professional writer")
    String writeStoryAboutTopic(String story);

}
