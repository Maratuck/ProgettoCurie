package it.enne.curie.common;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CustomExtension {

    private CustomExtension() {
    }

    public static void writeEncoded(String[] serverParameters, File file) throws IOException {
        byte[] encodedIP = Base64.getEncoder().encode(serverParameters[0].getBytes(StandardCharsets.UTF_8));
        byte[] encodedPort = Base64.getEncoder().encode(serverParameters[1].getBytes(StandardCharsets.UTF_8));

        file.setWritable(true, true);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(new String(encodedIP));
            writer.newLine();
            writer.write(new String(encodedPort));
        }

        file.setWritable(false, false);
    }

    public static String[] readDecoded(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            byte[] ip = Base64.getDecoder().decode(reader.readLine());
            byte[] port = Base64.getDecoder().decode(reader.readLine());

            return new String[]{new String(ip), new String(port)};
        }
    }
}
