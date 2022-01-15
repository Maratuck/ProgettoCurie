package it.enne.curie.common;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CustomExtension {

    public static void CustomExtensionWriter(String[] SERVER, File file) throws IOException {
        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(file));

        byte[] encodedip = Base64.getEncoder().encode(SERVER[0].getBytes(StandardCharsets.UTF_8));
        byte[] encodedport = Base64.getEncoder().encode(SERVER[1].getBytes(StandardCharsets.UTF_8));

        SERVER = new String[]{new String(encodedip), new String(encodedport)};

        boolean isWritable = file.setWritable(true, true);
        if (!isWritable) {
            System.err.println("errore scrittura config");
        }
        writer.writeObject(SERVER);
        isWritable = file.setWritable(false, false);
        if (!isWritable) {
            System.err.println("config modificabile");
        }
    }

    public static String[] CustomExtensionReader(File file) throws IOException, ClassNotFoundException {
        String[] SERVER;

        ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file));
        SERVER = (String[]) reader.readObject();

        byte[] decodedip = Base64.getDecoder().decode(SERVER[0].getBytes(StandardCharsets.UTF_8));
        byte[] decodedport = Base64.getDecoder().decode(SERVER[1].getBytes(StandardCharsets.UTF_8));

        SERVER = new String[]{new String(decodedip), new String(decodedport)};
        return SERVER;
    }
}
