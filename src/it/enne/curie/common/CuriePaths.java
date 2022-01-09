package it.enne.curie.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class CuriePaths {

    public static final String HOME = System.getProperty("user.home");
    public static final String SEP = File.separator;
    public static final String FOLDER_NAME = "BAA";
    public static final String LOG_NAME = "cb.log";

    public static final SimpleDateFormat formatoData = new SimpleDateFormat( "yyyy-MM-dd' 'HH:mm:ss" );

    private CuriePaths() {
    }

    public static String getLogPath() {
        return HOME + SEP + FOLDER_NAME + SEP + LOG_NAME;
    }

    public static String getConfigPath() {
        return HOME + SEP + FOLDER_NAME + SEP + "config.mkt";
    }

    public static String getFolderName() { return HOME + SEP + FOLDER_NAME; }

    public static String getCurrentData() { return formatoData.format( Calendar.getInstance().getTime() ); }
}
