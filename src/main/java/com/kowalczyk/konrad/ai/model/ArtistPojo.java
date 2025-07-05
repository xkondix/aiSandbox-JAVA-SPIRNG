package com.kowalczyk.konrad.ai.model;

public record ArtistPojo(String topic, int lines) {

    @Override
    public String toString() {
        return "ArtistPojo[" +
                "topic=" + topic + ", " +
                "lines=" + lines + ']';
    }

}
