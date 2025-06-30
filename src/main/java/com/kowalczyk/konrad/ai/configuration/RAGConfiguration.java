package com.kowalczyk.konrad.ai.configuration;


import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2q.AllMiniLmL6V2QuantizedEmbeddingModel;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.injector.ContentInjector;
import dev.langchain4j.rag.content.injector.DefaultContentInjector;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.chroma.ChromaEmbeddingStore;
import org.springframework.context.annotation.Bean;

import static java.util.Arrays.asList;

public class RAGConfiguration {

    /**
     * EmbeddingModel bean that converts input text into vector representations (embeddings).
     * These embeddings are later used for similarity search in the vector database (e.g. Chroma).
     * This implementation uses a lightweight quantized model (AllMiniLmL6V2) optimized for local inference.
     */
    @Bean
    public EmbeddingModel embeddingModel() { //transform text into vectors
        return new AllMiniLmL6V2QuantizedEmbeddingModel();
    }

    /**
     * EmbeddingStore bean that connects to a local Chroma vector database instance.
     * This store is used to persist and retrieve vectorized text segments.
     */
    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        return ChromaEmbeddingStore.builder()
                .baseUrl("http://localhost:8000")
                .collectionName("documents")
                .build();
    }

    /**
     * EmbeddingStoreIngestor is responsible for:
     * - splitting documents into smaller segments,
     * - embedding those segments,
     * - and storing them in the vector database.
     */
    @Bean
    public EmbeddingStoreIngestor ingestor(EmbeddingStore<TextSegment> store, EmbeddingModel model) {
        return EmbeddingStoreIngestor.builder()
                .documentSplitter(DocumentSplitters.recursive(300, 0))
                .embeddingModel(model)
                .embeddingStore(store)
                .build();
    }

    /**
     * ContentRetriever retrieves relevant segments from the vector database
     * by embedding user queries and finding the most similar document chunks.
     */
    @Bean
    public ContentRetriever retriever(EmbeddingStore<TextSegment> store, EmbeddingModel model) {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(store)
                .embeddingModel(model)
                .build();
    }

    /**
     * ContentInjector configures which metadata fields (e.g., file name and segment index)
     * should be included in the prompt sent to the LLM during RAG (Retrieval-Augmented Generation).
     */
    @Bean
    public ContentInjector injector() {
        return DefaultContentInjector.builder()
                .metadataKeysToInclude(asList("file_name", "index"))
                .build();
    }

    /**
     * RetrievalAugmentor combines the retriever and injector to enhance
     * user queries with relevant context before passing them to the LLM.
     */
    @Bean
    public RetrievalAugmentor augmentor(ContentInjector injector, ContentRetriever retriever) {
        return DefaultRetrievalAugmentor.builder()
                .contentRetriever(retriever)
                .contentInjector(injector)
                .build();
    }
}
