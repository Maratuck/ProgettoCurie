package it.enne.curie.client;

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