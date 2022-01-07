package it.enne.curie.common;

public class Message {

    private final String username;

    public Message(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return username;
    }
}
