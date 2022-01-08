package it.enne.curie.common;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CustomExtension {
    public static void EncodeWrite(String[] SERVER, File file) throws IOException {
        byte[] encodedip = Base64.getEncoder().encode(SERVER[0].getBytes(StandardCharsets.UTF_8));
        byte[] encodedport = Base64.getEncoder().encode(SERVER[1].getBytes(StandardCharsets.UTF_8));

        file.setWritable(true, true);

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(new String(encodedip));
        writer.newLine();
        writer.write(new String(encodedport));
        writer.close();

        file.setWritable(false, false);
    }

    public static String[] ReadDecode(File file) throws IOException {
        String[] SERVER = new String[2];

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String encodedip = reader.readLine();
        String encodedport = reader.readLine();
        reader.close();

        byte[] ip = Base64.getDecoder().decode(encodedip);
        byte[] port = Base64.getDecoder().decode(encodedport);

        SERVER[0] = new String(ip);
        SERVER[1] = new String(port);
        return SERVER;
    }
}
