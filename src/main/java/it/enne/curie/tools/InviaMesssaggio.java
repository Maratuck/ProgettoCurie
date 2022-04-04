package it.enne.curie.tools;

import it.enne.curie.common.ConnectionParameters;
import it.enne.curie.common.CuriePaths;
import it.enne.curie.common.CustomExtension;

import javax.swing.*;
import java.io.File;
import java.io.PrintWriter;
import java.net.Socket;

public class InviaMesssaggio {

    public static void main(String[] args) {

        String ip = "127.0.0.0";
        int port = 4444;

        try {
            String[] arr = CustomExtension.readDecoded( new File("./config.mkt"));
            ip = arr[0];
            port = Integer.parseInt(arr[1]);
        } catch (Exception e) {}

        try (Socket socket = new Socket( ip, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println(System.getProperty("user.name"));
            System.out.println("inviato");
            JOptionPane.showMessageDialog(null, "inviato");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "non inviato");
            e.printStackTrace();
        }

    }
}
