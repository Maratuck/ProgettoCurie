package it.enne.curie.common;

import java.io.File;
import java.io.IOException;

import static it.enne.curie.common.CustomExtension.EncodeWrite;

public class FileCreator {

    private FileCreator() {
    }

    public static File createAndWrite(String folderName, String configPath, String[] rows) {
        // Controllo esistenza della cartella
        File cartella = new File(folderName);
        if (!cartella.exists() || !cartella.isFile()) {
            // Creazione cartella e cartelle padri
            cartella.mkdirs();
        }

        // Controllo esistenza del file
        File file = new File(configPath);
        if (!file.exists()) {
            // Creazione file
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Errore nella creazione del file " + configPath);
                e.printStackTrace();
            }

            if (rows == null) return file;

            // Scrittura file
            try {
                EncodeWrite(rows, file);
            } catch (IOException e) {
                System.err.println("Errore nella scrittura sul file " + configPath);
                e.printStackTrace();
            }
        }

        return file;
    }

    public static File create(String folderName, String configPath) {
        return createAndWrite(folderName, configPath, null);
    }
}
