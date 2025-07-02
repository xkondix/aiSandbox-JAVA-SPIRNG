package com.kowalczyk.konrad.ai.model;

import java.util.Map;

public record TextIngestPojo(String text, Map<String, String> metadata) {

    @Override
    public String toString() {
        return "TextIngestPojo[" +
                "text=" + text + ", " +
                "metadata=" + metadata + ']';
    }
}
