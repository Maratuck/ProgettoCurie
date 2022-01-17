package it.enne.curie.server;

import it.enne.curie.common.FileCreator;
import it.enne.curie.common.LogWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static it.enne.curie.common.CuriePaths.*;
import static it.enne.curie.common.CustomExtension.readDecoded;

public class Server {

    private final String icon;
    private final LogWriter logWriter;
    private File config;

    private String[] SERVER = new String[]{"127.0.0.1", "4444"};

    public Server() {
        icon = "src/it/enne/curie/resources/icona.png";
        logWriter = new LogWriter(getLogPath() + "s"); // solo per i test per fare in modo che non scriva nello stesso file di log del client
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
                        inputLine = in.readLine();
                        if (inputLine != null) {
                            String IpClient = ((InetSocketAddress) clientSocket.getRemoteSocketAddress()).getAddress().toString().substring(1);
                            String data = getCurrentData();
                            String message = inputLine + ",  " + IpClient + ",  " + data;
                            logWriter.write(message);
                            new NotificationMenu(message).checkresult();
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
        try {
            SERVER = readDecoded(getConfig());
            return Integer.parseInt(SERVER[1]);
        } catch (IOException e) {
            e.printStackTrace();
            return 4444;
        }
    }

    public File getConfig() {
        if (config == null) {
            config = FileCreator.createAndWrite(getFolderName(), getConfigPath(), SERVER);
        }

        return config;
    }

}
