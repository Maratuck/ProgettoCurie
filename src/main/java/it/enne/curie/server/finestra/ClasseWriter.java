package it.enne.curie.server.finestra;

import java.io.*;

import static it.enne.curie.common.CuriePaths.getFolderName;

public class ClasseWriter {

    final int righe;
    final int colonne;
    final File file;

    public ClasseWriter(int colonne, int righe, String classePath) {
        this.righe = righe;
        this.colonne = colonne;
        this.file = new File(classePath);
    }

    private void creazioneFile() {
        boolean isCreated;

        try {
            // controllo cartella
            File cartella = new File(getFolderName());
            if (!cartella.exists() || !cartella.isFile()) {
                isCreated = cartella.mkdirs();
                if (isCreated) {
                    System.out.println("folder created");
                }
            }
            // controllo file config
            if (!file.exists()) {
                isCreated = file.createNewFile();
                if (isCreated) {
                    System.out.println("config created");
                }

                ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream (file));

                String[][] classe = new String[colonne][righe];
                writer.writeObject(classe);
            }
        } catch (Exception e) {
            System.err.println("errore creazione file classe");
        }

    }

    public String[][] read() {

        creazioneFile();
        try ( ObjectInputStream reader = new ObjectInputStream(new FileInputStream (file))) {

            return (String[][]) reader.readObject();

        } catch (Exception e) {
            System.err.println("errore lettura file classe");
        }
        return null;
    }

    public void write(String[][] classe) {
        boolean isWritable;

        creazioneFile();
        isWritable = file.setWritable(true, true);
        if (isWritable) {

            try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(file))) {

                writer.writeObject(classe);

            } catch (Exception e) {
                System.err.println("errore scrittura file classe");
            }

            isWritable = file.setWritable(false, false);
            if (!isWritable) {
                System.err.println("classe modificabile");
            }
        } else {
            System.err.println("errore scrittura file classe");
        }

    }

    public void modifica(int x, int y, String ip) {

        String[][] classe = read();

        try {
            classe[x][y] = ip;
        } catch (Exception e) {
            System.err.println("errore modifica file classe");
        }

        write(classe);
    }



}
