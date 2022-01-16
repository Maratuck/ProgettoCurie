package it.enne.curie.common;

import java.awt.*;
import java.io.*;

import static it.enne.curie.common.CuriePaths.*;

public class LogWriter {

    private final File log;
    private int rowCont;

    public LogWriter(String logPath) {
        log = new File(logPath);

        int tmpC, tmpF;
        try {
            // controllo cartella
            File cartella = new File(getFolderName());
            tmpC = checkFolder(cartella);
            if (tmpC == 1) {
                System.out.println("folder created : " + cartella);
            }

            // controllo file config
            File file = new File(logPath);
            tmpF = checkFile(file);
            if (tmpF == 1) {
                System.out.println("file created : " + file);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
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
        boolean isWritable;

        isWritable = log.setWritable(true,true);
        if (isWritable) {
            if (rowCont >= 20) { // eliminazione prima riga file
                File temp = new File(log + "tmp");
                try (
                        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(temp))));
                        BufferedReader br = new BufferedReader(new FileReader(log))
                ) {
                    String let;
                    br.readLine();
                    while ((let = br.readLine()) != null) {
                        writer.write(let + "\n");
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
            isWritable = log.setWritable(false, false);
            if (!isWritable) {
                System.err.println("log modificabile");
            }
        } else {
            System.err.println("errore scrittura log");
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
