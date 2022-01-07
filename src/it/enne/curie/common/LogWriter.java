package it.enne.curie.common;

import java.awt.*;
import java.io.*;

public class LogWriter {

    private final File log;
    private int rowCont;

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
            System.err.println("errore creazione file");
        }

        //conta rige file log
        rowCont = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(log))) {
            String line;
            while ((line = br.readLine()) != null) {
                rowCont++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void write(String line) {
        if (rowCont<20) {
            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(log, true))))) {
                writer.append(line);
                rowCont++;
            } catch (Exception e) {
                System.err.println("Errore scrittura nel log: " + line);
            }
        } else {
            File temp = new File(log+"tmp");
            try (
                    PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(temp))));
                    BufferedReader br = new BufferedReader(new FileReader(log));
            ) {
                String let;
                br.readLine();
                while ((let = br.readLine()) != null) {
                    writer.write(let+"\n");
                }
                writer.write(line);
                writer.close();
                br.close();
                log.delete();
                temp.renameTo(log);
            } catch (Exception e) {
                System.err.println("Errore scrittura nel log: " + line);
            }
        }
    }

    public void openFile() {
        try {
            Desktop.getDesktop().open(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
