package it.enne.curie.server;

import it.enne.curie.common.ConnectionParameters;
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

    private ConnectionParameters parameters = new ConnectionParameters("127.0.0.1", ConnectionParameters.DEFAULT_PORT);

    public Server() {
        icon = "./icona.png";
        logWriter = new LogWriter(getLogPath() + "s"); // solo per i test per fare in modo che non scriva nello stesso file di log del client
    }

    public void start() {
        new MenuEvent(logWriter, icon).setup();

        int portNumber = getPortNumber();

        System.out.println("Server avviato");
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);

            boolean errore = false;
            while (!errore) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    String inputLine = "";

                    while (inputLine != null) {
                        inputLine = in.readLine();
                        if (inputLine != null) {
                            String IpClient = ((InetSocketAddress) clientSocket.getRemoteSocketAddress()).getAddress().toString().substring(1);
                            String data = getCurrentData();
                            String message = inputLine + ",  " + IpClient + ",  " + data;
                            logWriter.write(message);
                            new NotificationMenu(message).checkresult( IpClient);
                        }
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
            config = FileCreator.createAndWrite(getFolderName(), getConfigPath(), new String[]{parameters.getIp(), Integer.toString(parameters.getPort()), "5", "8"});
        }

        return config;
    }

}
