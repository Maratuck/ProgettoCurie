package it.enne.curie.tools;

import it.enne.curie.common.CuriePaths;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InstallerServer {

    public static boolean copia(String sorgente, String destinazione) {

        try {
            Files.copy( Paths.get(sorgente), Paths.get(destinazione));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return new File(destinazione).exists();
    }

    public static void main(String[] args) {

        String pathJarSource = "./config.mkt";
        String pathJarDestination = CuriePaths.getFolderName() + CuriePaths.SEP + "config.mkt";

        System.out.println("Avvio installazione client");

        if ( copia( pathJarSource, pathJarSource)) {
            System.out.println("copiato " + pathJarSource + " in " + pathJarDestination);
        } else {
            System.err.println("fallita copiatura " + pathJarSource + " in " + pathJarDestination);
            return;
        }

        String pathConfSource = "./Server.jar";
        String pathConfDestination = "C:/ProgramData/Microsoft/Windows/Start Menu/Programs/StartUp/Server.jar";

        if ( copia( pathConfSource, pathConfDestination)) {
            System.out.println("copiato " + pathConfSource + " in " + pathConfDestination);
        } else {
            System.err.println("fallita copiatura " + pathConfSource + " in " + pathConfDestination);
            return;
        }

        /*
        String pathIcoSource= "./icona.png";
        String pathIcoDestination = "C:/ProgramData/Microsoft/Windows/Start Menu/Programs/StartUp/icona.png";

        if ( copia( pathIcoSource, pathIcoDestination)) {
            System.out.println("copiato " + pathIcoSource + " in " + pathIcoDestination);
        } else {
            System.err.println("fallita copiatura " + pathIcoSource + " in " + pathIcoDestination);
            return;
        }
         */

        System.out.println("Installazione completata");
        JOptionPane.showMessageDialog(null, "Installazione completata");
    }

}
