package com.kowalczyk.konrad.ai.model;

import java.util.Map;

public record FilePathIngestPojo(String filePath, Map<String, String> metadata) {

    @Override
    public String toString() {
        return "FilePathIngestPojo[" +
                "filePath=" + filePath + ", " +
                "metadata=" + metadata + ']';
    }
}
