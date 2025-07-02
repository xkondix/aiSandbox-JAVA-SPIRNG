package com.kowalczyk.konrad.ai.controller;


import com.kowalczyk.konrad.ai.model.FilePathIngestPojo;
import com.kowalczyk.konrad.ai.model.TextIngestPojo;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ingest")
public class DocumentController {

    private final EmbeddingStore<TextSegment> embeddingStore;
    private final EmbeddingStoreIngestor ingestor;
    private final EmbeddingModel embeddingModel;


    public DocumentController(EmbeddingStore<TextSegment> embeddingStore, EmbeddingStoreIngestor ingestor,
                              EmbeddingModel embeddingModel) {
        this.embeddingStore = embeddingStore;
        this.ingestor = ingestor;
        this.embeddingModel = embeddingModel;
    }

    @PostMapping("/text")
    public ResponseEntity<String> ingestPlainText(@RequestBody TextIngestPojo pojo) {
        Document document = Document.from(pojo.text());
        ingestor.ingest(document);
        return ResponseEntity.ok("Text ingested successfully (no metadata)");
    }

    @PostMapping("/path")
    public ResponseEntity<String> ingestPlainText(@RequestBody FilePathIngestPojo pojo) throws IOException {
        String text = Files.readString(Paths.get(pojo.filePath()));
        Document document = Document.from(text);
        ingestor.ingest(document);
        return ResponseEntity.ok("File ingested successfully (no metadata)");
    }

    @PostMapping("/text/metadata")
    public ResponseEntity<String> ingestTextWithMetadata(@RequestBody TextIngestPojo pojo) {
        List<TextSegment> segments = DocumentSplitters.recursive(300, 0).split(Document.from(pojo.text()));
        List<TextSegment> textSegments = createSegmentsWithMetadata(segments, pojo.metadata());
        List<Embedding> embeddings = embeddingModel.embedAll(textSegments).content();
        embeddingStore.addAll(embeddings, textSegments);
        return ResponseEntity.ok("Text ingested successfully with metadata");
    }

    @PostMapping("/path/metadata")
    public ResponseEntity<String> ingestPathWithMetadata(@RequestBody FilePathIngestPojo pojo) throws IOException {
        String text = Files.readString(Paths.get(pojo.filePath()));
        List<TextSegment> segments = DocumentSplitters.recursive(300, 0).split(Document.from(text));
        List<TextSegment> textSegments = createSegmentsWithMetadata(segments, pojo.metadata());
        List<Embedding> embeddings = embeddingModel.embedAll(textSegments).content();
        embeddingStore.addAll(embeddings, textSegments);
        return ResponseEntity.ok("File ingested successfully with metadata");
    }

    private List<TextSegment> createSegmentsWithMetadata(List<TextSegment> segments, Map<String, String> customMetadata) {
        List<TextSegment> textSegments = new ArrayList<>();
        for (int i = 0; i < segments.size(); i++) {
            Map<String, String> metadata = new HashMap<>();
            if (customMetadata != null) metadata.putAll(customMetadata);
            metadata.putIfAbsent("index", String.valueOf(i));
            textSegments.add(TextSegment.from(segments.get(i).text(), Metadata.from(metadata)));
        }
        return textSegments;
    }

}
