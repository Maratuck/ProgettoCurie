package it.enne.curie.common;

public record Message(String username) {

    @Override
    public String toString() {
        return username;
    }
}