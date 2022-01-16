package it.enne.curie.server.finestra;

import java.io.*;

import static it.enne.curie.common.CuriePaths.*;

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

        try {
            // controllo cartella
            File cartella = new File(getFolderName());
            if (!cartella.exists() || !cartella.isFile()) {
                cartella.mkdirs();
            }
            // controllo file config
            if (!file.exists()) {
                file.createNewFile();

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
        file.setWritable(true, true);

        try ( ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream (file))) {

            writer.writeObject(classe);

        } catch (Exception e) {
            System.err.println("errore scrittura file classe");
        }

        file.setWritable(false, false);

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
