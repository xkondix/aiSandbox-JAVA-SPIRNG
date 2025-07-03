package com.kowalczyk.konrad.ai.model;

public record ChatRAGPojo(String message, TypeRAG rag) {

    @Override
    public String toString() {
        return "ChatRAGPojo[" +
                "message=" + message + ", " +
                "rag=" + rag + ']';
    }

}
