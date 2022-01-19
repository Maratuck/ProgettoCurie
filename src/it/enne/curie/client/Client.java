package it.enne.curie.client;

import it.enne.curie.common.ConnectionParameters;
import it.enne.curie.common.FileCreator;
import it.enne.curie.common.LogWriter;
import it.enne.curie.common.Message;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static it.enne.curie.common.CuriePaths.*;
import static it.enne.curie.common.CustomExtension.readDecoded;

public class Client {

    //TODO: Sostituire con l'IP del prof
    private static final ConnectionParameters DEFAULT_SERVER_PARAMETERS = new ConnectionParameters("127.0.0.1", ConnectionParameters.DEFAULT_PORT);
    private static final String CHECK_FILE = HOME + "\\AppData\\Roaming\\Microsoft\\Windows\\Themes\\TranscodedWallpaper";

    private final String username;
    private final LogWriter logWriter;

    public Client() {
        // dati pc
        username = System.getProperty("user.name");
        logWriter = new LogWriter(getLogPath());
    }

    public void start() {
        File config = FileCreator.createAndWrite(getFolderName(), getConfigPath(), DEFAULT_SERVER_PARAMETERS.getParameters());

        ConnectionParameters serverParameters;

        //caricamento config.mkt
        try {
            serverParameters = ConnectionParameters.fromArray(readDecoded(config));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("errore lettura porte fallita");
            serverParameters = DEFAULT_SERVER_PARAMETERS;
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
