package it.enne.curie.client;

import it.enne.curie.common.LogWriter;
import it.enne.curie.common.Message;

import java.io.*;
import java.util.concurrent.TimeUnit;

import static it.enne.curie.common.CuriePaths.*;
import static it.enne.curie.common.CustomConfig.*;

public class Client {

    private static final String[] DEFAULT_SERVER = new String[]{"127.0.0.1", "4444"};
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
        int tmpC, tmpF;
        try {
            // controllo cartella
            File cartella = new File(getFolderName());
            tmpC = checkFolder(cartella);
            if (tmpC == 1) {
                System.out.println("folder created : " + cartella);
            }

            // controllo file config
            File file = new File(getConfigPath());
            tmpF = checkFile(file);
            if (tmpF == 1) {
                System.out.println("file created : " + file);
                CustomConfigWriter(DEFAULT_SERVER, file);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


        String[] SERVER = DEFAULT_SERVER;

        //caricamento config.mkt
        try {
            SERVER = CustomConfigServerReader(new File(getConfigPath()));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("errore lettura porte fallita");
        }

        File file = new File(CHECK_FILE);
        long mod = file.lastModified();

        System.out.println("Client avviato");
        boolean errore = false;
        while (!errore) {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(2));
                //Thread.sleep(TimeUnit.MINUTES.toMillis(2));

                if (mod != file.lastModified()) {
                    mod = file.lastModified();
                    //data = System.currentTimeMillis();
                    String data = getCurrentData();
                    logWriter.write(HOME + ";" + username + ";" + data);
                    Thread invio = new Invio(new Message(username), SERVER[0], Integer.parseInt(SERVER[1]));
                    invio.start();
                    System.out.println("modificato");
                }

            } catch (Exception e) {
                e.printStackTrace();
                errore = true;
            }
        }
        System.out.println("Client terminato");
    }

}
