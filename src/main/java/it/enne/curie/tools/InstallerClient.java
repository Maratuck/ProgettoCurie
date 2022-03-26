package it.enne.curie.tools;

import it.enne.curie.common.CuriePaths;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InstallerClient {

    public static boolean copia(String sorgente, String destinazione) {

        try {
            Files.copy( Paths.get(sorgente), Paths.get(destinazione));
        } catch (IOException e) {
            //e.printStackTrace();
            return false;
        }

        return new File(destinazione).exists();
    }

    public static void main(String[] args) {

        String pathJarSource = "./conf.txt";
        String pathJarDestination = CuriePaths.getFolderName() + CuriePaths.SEP + "conf.txt";

        System.out.println("Avvio installazione client");

        if ( copia( pathJarSource, pathJarSource)) {
            System.out.println("copiato " + pathJarSource + " in " + pathJarDestination);
        } else {
            System.err.println("fallita copiatura " + pathJarSource + " in " + pathJarDestination);
            return;
        }

        String pathConfSource = "./Programma.jar";
        String pathConfDestination = "C:/Users/Default/AppData/Roaming/Microsoft/Windows/Start Menu/Programs/Startup/Programma.jar";

        if ( copia( pathConfSource, pathConfDestination)) {
            System.out.println("copiato " + pathConfSource + " in " + pathConfDestination);
        } else {
            System.err.println("fallita copiatura " + pathConfSource + " in " + pathConfDestination);
            return;
        }

        System.out.println("Installazione completata");
    }

}
