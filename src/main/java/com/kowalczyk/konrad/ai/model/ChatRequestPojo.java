package com.kowalczyk.konrad.ai.model;


public record ChatRequestPojo(String username, String message) {

    @Override
    public String toString() {
        return "ChatRequestPojo[" +
                "username=" + username + ", " +
                "message=" + message + ']';
    }

}
