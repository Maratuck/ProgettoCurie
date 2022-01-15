package it.enne.curie.server.finestra;

import java.io.*;

import static it.enne.curie.common.CuriePaths.*;

public class ClasseWriter {

    final int righe;
    final int colonne;
    final File file;
    boolean IsCreated;
    boolean isWritable;

    public ClasseWriter(int colonne, int righe, String classePath) {
        this.righe = righe;
        this.colonne = colonne;
        this.file = new File(classePath);
    }

    private void creazioneFile() {

        try {
            // controllo cartella
            File cartella = new File(getFolderName());
            if (!cartella.exists() || !cartella.isFile()) {
                IsCreated = cartella.mkdirs();
                if (IsCreated) {
                    System.out.println("folder created");
                }
            }
            // controllo file classe
            if (!file.exists()) {
                IsCreated = file.createNewFile();
                if (IsCreated) {
                    System.out.println("classe created");
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

        creazioneFile();
        isWritable = file.setWritable(true, true);
        if (!isWritable) {
            System.err.println("errore scrittura classe");
        }

        try ( ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream (file))) {

            writer.writeObject(classe);

        } catch (Exception e) {
            System.err.println("errore scrittura file classe");
        }

        isWritable = file.setWritable(false, false);
        if (!isWritable) {
            System.err.println("classe modificabile");
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
