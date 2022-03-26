package it.enne.curie.common;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Vector;

public class CustomExtension {

    private CustomExtension() {
    }

    public static void writeEncoded(String[] serverParameters, File file) throws IOException {

        file.setWritable(true, true);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            for (int i=0; i<serverParameters.length; i++) {
                // TODO : togliere commento versione finita
                //writer.write( new String( Base64.getEncoder().encode(serverParameters[i].getBytes(StandardCharsets.UTF_8)) ) );
                writer.write(serverParameters[i]);
                writer.newLine();
            }
        }

        file.setWritable(false, false);
    }

    public static String[] readDecoded(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            Vector<String> vet = new Vector();
            String riga = "";
            while ( (riga = reader.readLine())!=null ) {
                // TODO : togliere commento versione finita
                //riga = new String( Base64.getDecoder().decode(reader.readLine()) );
                vet.add(riga);
            }

            String[] out = new String[vet.size()];
            for (int i=0; i<vet.size(); i++) {
                out[i] = vet.get(i);
            }

            return out;
        }
    }
}
