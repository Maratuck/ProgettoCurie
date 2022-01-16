package it.enne.curie.common;

import java.awt.*;
import java.io.*;

import static it.enne.curie.common.CuriePaths.*;

public class LogWriter {

    private final File log;
    private int rowCont;

    public LogWriter(String logPath) {
        log = new File(logPath);

        try {
            // cartella controllo
            File cartella = new File(getFolderName());
            boolean isCreated;
            if (!cartella.exists() || !cartella.isFile()) {
                isCreated = cartella.mkdirs();
                if (isCreated) {
                    System.out.println("folder created");
                }
            }

            // file log controllo
            File file = new File(logPath);
            if (!file.exists()) {
                isCreated = file.createNewFile();
                if (isCreated) {
                    System.out.println("log created");
                }
            }
        } catch (IOException e) {
            System.err.println("errore creazione file");
        }

        //conta righe file log
        rowCont = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(log))) {
            while ((br.readLine()) != null) {
                rowCont++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void write(String line) {
        boolean isWritable = log.setWritable(true, true);
        if (!isWritable) {
            System.err.println("errore scrittura log");
        }

        if (rowCont>=20) { // eliminazione prima riga file
            File temp = new File(log+"tmp");
            try (
                    PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(temp))));
                    BufferedReader br = new BufferedReader(new FileReader(log))
            ) {
                String let;
                br.readLine();
                while ((let = br.readLine()) != null) {
                    writer.write(let+"\n");
                }
                rowCont--;
            } catch (Exception e) {
                System.err.println("Errore eliminazione prima riga file log");
            }
            if (!log.delete() || !temp.renameTo(log)) {
                System.err.println("errore switch  file temporaneo file log");
            }
        }
        // aggiunta riga in fondo al file di log
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(log, true))))) {
            writer.append(line).append("\n");
            rowCont++;
        } catch (Exception e) {
            System.err.println("Errore scrittura nel log: " + line);
        }
        isWritable = log.setWritable(false,false);
        if (!isWritable) {
            System.err.println("log modificabile");
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
