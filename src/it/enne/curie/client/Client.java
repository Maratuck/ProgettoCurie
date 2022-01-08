package it.enne.curie.client;

import it.enne.curie.common.CuriePaths;
import it.enne.curie.common.LogWriter;
import it.enne.curie.common.Message;

import java.io.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static it.enne.curie.common.CuriePaths.*;

public class Client {

    //TODO: Sostituire con l'IP del prof
    private static final String DEFAULT_SERVER_IP = "127.0.0.1";
    private static final int DEFAULT_SERVER_PORT = 4444;
    private static final String CHECK_FILE = HOME + "\\AppData\\Roaming\\Microsoft\\Windows\\Themes\\TranscodedWallpaper";

    private final String username;
    private final LogWriter logWriter;

    public Client() {
        // dati pc
        username = System.getProperty("user.name");
        logWriter = new LogWriter(getLogPath());
    }

    public void start() {
        //TODO: @Ale config in common

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
                PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getConfigPath()))), true);
                writer.println(DEFAULT_SERVER_IP);
                writer.println(DEFAULT_SERVER_PORT);
                writer.close();
            }
        } catch (Exception e) {
            System.err.println("errore creazione file");
        }

        String serverAddress = DEFAULT_SERVER_IP;
        int port = DEFAULT_SERVER_PORT;

        //caricamento config.txt
        try {
            BufferedReader reader = new BufferedReader(new FileReader(getConfigPath()));
            serverAddress = reader.readLine();
            port = Integer.parseInt(reader.readLine());
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("errore lettura porte fallita");
        }

        File file = new File(CHECK_FILE);
        long mod = file.lastModified();

        while (true) {
            if (mod != file.lastModified()) {
                mod = file.lastModified();
                //data = System.currentTimeMillis();
                String data = getCurrentData();
                logWriter.write(HOME + ";" + username + ";" + data );
                Thread invio = new Invio(new Message(username), serverAddress, port);
                invio.start();
                System.out.println("modificato");
            }

            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(2));
                //Thread.sleep(TimeUnit.MINUTES.toMillis(2));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
