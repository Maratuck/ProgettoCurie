package it.enne.curie.common;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CustomConfig {


    public static void CustomConfigWriter(String[] SERVER, int[] Map, File file) throws IOException {

        byte[] encodedip = Base64.getEncoder().encode(SERVER[0].getBytes(StandardCharsets.UTF_8));
        byte[] encodedport = Base64.getEncoder().encode(SERVER[1].getBytes(StandardCharsets.UTF_8));

        SERVER = new String[]{new String(encodedip), new String(encodedport)};
        String[] MAP = new String[]{String.valueOf(Map[0]), String.valueOf(Map[1])};

        String[][] CONFIG = new String[][]{SERVER, MAP};

        file.setWritable(true, true);
        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(file));
        writer.writeObject(CONFIG);
        writer.close();
        file.setWritable(false, false);
    }


    public static void CustomConfigWriter(String[] SERVER, File file) throws IOException {
        byte[] encodedip = Base64.getEncoder().encode(SERVER[0].getBytes(StandardCharsets.UTF_8));
        byte[] encodedport = Base64.getEncoder().encode(SERVER[1].getBytes(StandardCharsets.UTF_8));

        SERVER = new String[]{new String(encodedip), new String(encodedport)};
        String[] NULL = new String[0];

        String[][] CONFIG = new String[][]{SERVER, NULL};

        file.setWritable(true, true);
        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(file));
        writer.writeObject(CONFIG);
        writer.close();
        file.setWritable(false, false);
    }

    public static String[] CustomConfigServerReader(File file) throws IOException, ClassNotFoundException {
        String[][] CONFIG;

        String[] SERVER;

        ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file));
        CONFIG = (String[][]) reader.readObject();
        SERVER = CONFIG[0];

        byte[] decodedip = Base64.getDecoder().decode(SERVER[0].getBytes(StandardCharsets.UTF_8));
        byte[] decodedport = Base64.getDecoder().decode(SERVER[1].getBytes(StandardCharsets.UTF_8));

        return new String[]{new String(decodedip), new String(decodedport)};

    }

    public static int[] CustomConfigMapReader(File file) throws IOException, ClassNotFoundException {
        String[][] CONFIG;

        String[] MAP;

        ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file));
        CONFIG = (String[][]) reader.readObject();
        MAP = CONFIG[1];

        return new int[]{Integer.parseInt(MAP[0]), Integer.parseInt(MAP[1])};
    }
}
