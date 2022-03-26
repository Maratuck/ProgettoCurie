package it.enne.curie.tools;

import it.enne.curie.common.CuriePaths;
import it.enne.curie.common.CustomExtension;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;


public class CreaFileConfig {

    public static void main(String[] args) {

        String ip;
        int port;
        int x, y;

        Scanner scan = new Scanner(System.in);

        System.out.print("inserire ip server :");
        ip = scan.nextLine();
        System.out.print("inserire porta :");
        port = scan.nextInt();
        System.out.print("inserire numero di banchi per riga (X) :");
        x = scan.nextInt();
        System.out.print("inserire numero di banchi per colonna (Y) :");
        y = scan.nextInt();
        System.out.println("---------------------------------------");
        System.out.println("ip : " + ip);
        System.out.println("port : " + port);
        System.out.println("X : " + x);
        System.out.println("Y : " + y);
        System.out.println("---------------------------------------");

        File file = new File("./config.mkt");

        try {
            file.delete();
            file.createNewFile();
            CustomExtension.writeEncoded( new String[]{ip, Integer.toString(port), Integer.toString(x), Integer.toString(y)}, file);
        } catch (IOException e) {
            System.err.println("errore creazione file");
        }
        System.out.println("file creato e scritto");

    }

}
