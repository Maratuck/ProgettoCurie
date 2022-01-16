package it.enne.curie.server;

import it.enne.curie.common.LogWriter;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static it.enne.curie.common.CuriePaths.*;
import static it.enne.curie.common.CustomConfig.*;

public class Server {

    private final String icon;
    private final LogWriter logWriter;
    private final String CONFIG;

    private String[] SERVER = new String[]{"","4444"};
    private final int[] DEFAULT_MAP = new int[]{5,3};

    public Server() {
        icon = "src/it/enne/curie/resources/icona.png";
        logWriter = new LogWriter(getLogPath()+"s"); // solo per i test per fare in modo che non scriva nello stesso file di log del client
        CONFIG = getConfigPath() + "s"; // solo per i test per fare in modo che non scriva nello stesso file di config del client

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
                            String message = inputLine+",  "+IpClient+",  "+data;
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

        int tmpC, tmpF;
        try {
            // controllo cartella
            File cartella = new File(getFolderName());
            tmpC = checkFolder(cartella);
            if (tmpC == 1) {
                System.out.println("folder created : " + cartella);
            }

            // controllo file config
            File file = new File(CONFIG);
            tmpF = checkFile(file);
            if (tmpF == 1) {
                System.out.println("file created : " + file);
                CustomConfigWriter(SERVER, DEFAULT_MAP, file);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        try {
            SERVER = CustomConfigServerReader(new File(CONFIG));
            return Integer.parseInt(SERVER[1]);
        } catch (Exception e) {
            e.printStackTrace();
            return 4444;
        }
    }

}
