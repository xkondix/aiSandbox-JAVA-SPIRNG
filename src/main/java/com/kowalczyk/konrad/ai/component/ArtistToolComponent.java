package com.kowalczyk.konrad.ai.component;

import dev.langchain4j.agent.tool.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ArtistToolComponent {
    private static final Logger log = LoggerFactory.getLogger(ArtistToolComponent.class);
    private static final String OUTPUT_DIR = "generated-stories";
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    public ArtistToolComponent() {
        File dir = new File(OUTPUT_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Tool("Save the given story to a file on disk and return name of the story")
    public String saveStoryToFile(String story) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filename = OUTPUT_DIR + "/story_" + COUNTER.getAndIncrement() + "_" + timestamp + ".txt";

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(story);
            log.info("Story saved to file: {}", filename);
            return filename;
        } catch (IOException e) {
            log.error("Failed to write poem to file", e);
            return "Exception occurs";
        }

    }

}
