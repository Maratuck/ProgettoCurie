package it.enne.curie.server;

import it.enne.curie.common.ConnectionParameters;
import it.enne.curie.common.FileCreator;
import it.enne.curie.common.Message;
import it.enne.curie.server.database.DatabaseManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static it.enne.curie.common.CuriePaths.getConfigPath;
import static it.enne.curie.common.CuriePaths.getFolderName;
import static it.enne.curie.common.CustomExtension.readDecoded;

public class Server {

    private final String icon;
    private final DatabaseManager databaseManager;
    private File config;

    private ConnectionParameters parameters = new ConnectionParameters("127.0.0.1", ConnectionParameters.DEFAULT_PORT);

    public Server() {
        icon = "src/it/enne/curie/resources/icona.png";
        databaseManager = new DatabaseManager();
    }

    public void start() {
        new MenuEvent(icon).setup();

        int portNumber = getPortNumber();

        System.out.println("Server avviato");
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);

            boolean errore = false;
            while (!errore) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    String inputLine = in.readLine();
                    if (inputLine != null) {
                        String ip = ((InetSocketAddress) clientSocket.getRemoteSocketAddress()).getAddress().toString().substring(1);
                        databaseManager.addLog(new Message(inputLine), ip);
                        new NotificationMenu(inputLine).checkresult(ip);
                    }
                } catch (Exception e) {
                    errore = true;
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Errore nell'accettazione di un client: " + e.getMessage());
        }
        System.out.println("Server terminato");
    }

    //leggere la porta dal file
    private int getPortNumber() {
        try {
            parameters = ConnectionParameters.fromArray(readDecoded(getConfig()));
            return parameters.getPort();
        } catch (IOException e) {
            e.printStackTrace();
            return ConnectionParameters.DEFAULT_PORT;
        }
    }

    public File getConfig() {
        if (config == null) {
            config = FileCreator.createAndWrite(getFolderName(), getConfigPath(), parameters.getParameters());
        }

        return config;
    }

}
