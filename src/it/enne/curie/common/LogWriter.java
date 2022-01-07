package it.enne.curie.common;

import java.io.*;

public class LogWriter {

    private final File log;

    public LogWriter(String logPath) {
        log = new File(logPath);

        try {
            // cartella controllo
            File cartella = new File(CuriePaths.HOME + CuriePaths.FOLDER_NAME);
            if (!cartella.exists() || !cartella.isFile()) {
                cartella.mkdirs();
            }

            // file log controllo
            File file = new File(logPath);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("errore creazione file log");
        }
    }

    public void write(String line) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(log))), true)) {
            writer.append(line);

            //TODO: Check numero linee e in caso rimuovi le vecchie
        } catch (Exception e) {
            System.err.println("Errore scrittura nel log: " + line);
        }
    }

    public void openFile() {
        //TODO: Cercare come aprire il file di log con il programma predefinito di Windows
    }
}
