// connetterlo, estrarre i dati ricevuti dal server

package it.enne.curie.client.prof;

import it.enne.curie.common.ConnectionParameters;
import it.enne.curie.common.FileCreator;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static it.enne.curie.common.CuriePaths.*;
import static it.enne.curie.common.CustomExtension.readDecoded;

public class Client {

    //TODO: Sostituire con l'IP del prof
    private static final ConnectionParameters DEFAULT_SERVER_PARAMETERS = new ConnectionParameters("127.0.0.1", ConnectionParameters.DEFAULT_PORT);

    public Client() {
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
        System.out.println("Client avviato");
        boolean errore = false;
        while (!errore) {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(2));
                //Thread.sleep(TimeUnit.MINUTES.toMillis(2));
                //data = System.currentTimeMillis();
                String data = getCurrentData();
                Thread ricezione = new Ricezione(serverParameters.getIp(), serverParameters.getPort());
                ricezione.start();
                System.out.println("modificato");
            } catch (InterruptedException e) {
                e.printStackTrace();
                errore = true;
            }
        }
        System.out.println("Client terminato");
    }
}