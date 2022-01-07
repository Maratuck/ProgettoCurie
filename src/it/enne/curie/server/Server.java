package it.enne.curie.server;

import it.enne.curie.common.CuriePaths;
import it.enne.curie.common.LogWriter;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import static it.enne.curie.common.CuriePaths.SEP;
import static it.enne.curie.common.CuriePaths.getConfigPath;

public class Server {

    private final String icon;
    private final LogWriter logWriter;

    public Server() {
        icon = ".." + SEP + "resources" + SEP + "icona.png";
        logWriter = new LogWriter(CuriePaths.getLogPath());
    }

    public void start() {
        new MenuEvent(logWriter, icon).setup();

        int portNumber = getPortNumber();
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    String inputLine = "";

                    while (inputLine != null) {
                        //TODO: Modifica lettura da String a oggetto Message
                        //TODO: Ricavare IP client, data con System.currenttime...
                        inputLine = in.readLine();
                        if (inputLine != null) {
                            JOptionPane.showMessageDialog(new JFrame(),
                                    inputLine,
                                    "Cambio Sfondo",
                                    JOptionPane.WARNING_MESSAGE);
                            logWriter.write(inputLine);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Errore nell'accettazione di un client: " + e.getMessage());
        }
    }

    //leggere la porta dal file
    private int getPortNumber() {
        //TODO: Controllare se il file esiste e in caso crearlo

        try (BufferedReader reader = new BufferedReader(new FileReader(getConfigPath()))) {
            //salta la prima riga
            reader.readLine();
            return Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
            return 4444;
        }
    }

}
