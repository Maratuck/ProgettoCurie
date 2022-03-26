package it.enne.curie.tools;

import it.enne.curie.common.CuriePaths;
import it.enne.curie.common.CustomExtension;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;


public class CreaFileConfig {

    public static void main(String[] args) {


        try {
            System.out.println( CustomExtension.readDecoded(new File(CuriePaths.getConfigPath()))[1] );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
