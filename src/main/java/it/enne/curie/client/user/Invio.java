package it.enne.curie.client.user;

import it.enne.curie.common.Message;

import java.io.PrintWriter;
import java.net.Socket;

public class Invio extends Thread {

    private final Message message;
    private final String address;
    private final int port;

    public Invio(Message message, String address, int port) {
        this.message = message;
        this.address = address;
        this.port = port;
    }

    public boolean invia() {
        try (Socket socket = new Socket(address, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void run() {
        if (!invia()) {
            System.out.println("non inviato");
        } else {
            System.out.println("inviato");
        }
    }

}
