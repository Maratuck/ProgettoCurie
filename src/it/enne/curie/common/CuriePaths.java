package it.enne.curie.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;



public class CuriePaths {

    public static final String HOME = System.getProperty("user.home");
    public static final String SEP = File.separator;
    public static final String FOLDER_NAME = "BAA";
    public static final String LOG_NAME = "cb.log";
    public static final String CONFIG = "config.mkt";

    public static final SimpleDateFormat formatoData = new SimpleDateFormat( "yyyy-MM-dd',  'HH:mm:ss" );

    private CuriePaths() {
    }

    public static String getLogPath() {
        return HOME + SEP + FOLDER_NAME + SEP + LOG_NAME;
    }

    public static String getConfigPath() {
        return HOME + SEP + FOLDER_NAME + SEP + CONFIG;
    }

    public static String getFolderName() { return HOME + SEP + FOLDER_NAME; }

    public static String getCurrentData() { return formatoData.format( Calendar.getInstance().getTime() ); }

    public static int checkFolder(File cartella) {
        // 0  : esiste
        //-1  : non e stato possibile crearlo
        // 1  : è stato creato
        try {
            if (!cartella.exists()) {
                if (cartella.mkdirs()) {
                    return 1;
                }
                throw new Error("errore creazione cartella : " + cartella);
            }
            return 0;
        } catch (Exception e) {
            throw new Error("errore creazione cartella : " + cartella);
        }
    }

    public static int checkFile(File file) {
        // 0  : esiste
        //-1  : non e stato possibile crearlo
        // 1  : è stato creato

        try {
            if (!file.exists()) {
                if (file.createNewFile()) {
                    return 1;
                }
                throw new Error("errore creazione file : " + file);
            }
            return 0;
        } catch (Exception e) {
            throw new Error("errore creazione file : " + file);
        }
    }
}
