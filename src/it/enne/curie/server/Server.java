package it.enne.curie.server;

import it.enne.curie.common.CuriePaths;
import it.enne.curie.common.LogWriter;

import javax.swing.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static it.enne.curie.common.CuriePaths.*;
import static it.enne.curie.common.CustomExtension.*;

public class Server {

    private final String icon;
    private final LogWriter logWriter;

    private String[] SERVER = new String[]{"127.0.0.1","4444"};

    public Server() {
        icon = ".." + SEP + "resources" + SEP + "icona.png";
        logWriter = new LogWriter(CuriePaths.getLogPath()+"s"); // solo per i test per fare in modo che non scriva nello stesso file di log del client
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
                            String IpClient = ((InetSocketAddress) clientSocket.getRemoteSocketAddress()).getAddress().toString();
                            String data = getCurrentData();
                            logWriter.write(inputLine+";"+IpClient+";"+data);
                            JOptionPane.showMessageDialog(new JFrame(),
                                    inputLine+";"+IpClient+";"+data,
                                    "Cambio Sfondo",
                                    JOptionPane.WARNING_MESSAGE);
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
        // controlla esistenza
        try {
            // controllo cartella
            File cartella = new File(CuriePaths.HOME + CuriePaths.FOLDER_NAME);
            if (!cartella.exists() || !cartella.isFile()) {
                cartella.mkdirs();
            }
            // controllo file config
            File file = new File(getConfigPath());
            if (!file.exists()) {
                file.createNewFile();
                EncodeWrite(SERVER, file);
            }
        } catch (Exception e) {
            System.err.println("errore creazione file");
        }

        try {
            SERVER = ReadDecode(new File(getConfigPath()));
            return Integer.parseInt(SERVER[1]);
        } catch (IOException e) {
            e.printStackTrace();
            return 4444;
        }
    }

}
