package it.enne.curie.client;

import it.enne.curie.common.FileCreator;
import it.enne.curie.common.LogWriter;
import it.enne.curie.common.Message;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static it.enne.curie.common.CuriePaths.*;
import static it.enne.curie.common.CustomExtension.ReadDecode;

public class Client {

    //TODO: Sostituire con l'IP del prof
    private static final String[] DEFAULT_SERVER_PARAMETERS = new String[]{"127.0.0.1", "4444"};
    private static final String CHECK_FILE = HOME + "\\AppData\\Roaming\\Microsoft\\Windows\\Themes\\TranscodedWallpaper";

    private final String username;
    private final LogWriter logWriter;

    public Client() {
        // dati pc
        username = System.getProperty("user.name");
        logWriter = new LogWriter(getLogPath());
    }

    public void start() {
        File config = FileCreator.createAndWrite(getFolderName(), getConfigPath(), DEFAULT_SERVER_PARAMETERS);

        String[] serverParameters;

        //caricamento config.mkt
        try {
            serverParameters = ReadDecode(config);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("errore lettura porte fallita");
            serverParameters = DEFAULT_SERVER_PARAMETERS;
        }

        File file = new File(CHECK_FILE);
        long mod = file.lastModified();

        while (true) {
            if (mod != file.lastModified()) {
                mod = file.lastModified();
                //data = System.currentTimeMillis();
                String data = getCurrentData();
                logWriter.write(HOME + ";" + username + ";" + data );
                Thread invio = new Invio(new Message(username), serverParameters[0], Integer.parseInt(serverParameters[1]));
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
