package it.enne.curie.client.prof;

import it.enne.curie.common.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Ricezione extends Thread {

    private Message message;
    private final String address;
    private final int port;

    public Ricezione(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public boolean ricevi() {
        try (Socket socket = new Socket(address, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            message = new Message(in.readLine());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void run() {
        if (!ricevi()) {
            System.out.println("non ricevuto");
        } else {
            System.out.println("ricevuto");
        }
    }

}