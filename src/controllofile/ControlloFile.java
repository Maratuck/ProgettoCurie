package controllofile;

import java.io.File;
import java.io.FileWriter;
import java.net.InetAddress;


public class ControlloFile {
    
    public static void log( String home, String username, String hostname, String ip, long data ) {
        
        File file = new File( home + "\\cb.log");
        
        
        try {
            if ( file.exists() ) {
                file.createNewFile();
            }
        } catch ( Exception e) {
            System.err.println("errore creazione file");
        }
            
        try {
            FileWriter fr = new FileWriter( file, true);
            fr.append( "\"" + home + "\"" + ";" + "\""+ username + "\"" + ";" + "\"" + hostname + "\"" + ";" + "\"" + ip + "\"" + ";" + data + "\n");
            fr.close();
        } catch (Exception e) {
            System.err.println("errore aggiunta file");
        }
        
    }
    
    public static void invia( String home, String username, String hostname, String ip, long data, String pathImg ) {
        
        // comandi per inviare
        
    }

    public static void main(String[] args) throws InterruptedException {
        
        String hostname;
        String ip;
        String username;
        String home;
        

        long data;
        
        //dati rete
        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
            ip = addr.getHostAddress();
        } catch ( Exception e) {
            hostname = "errore";
            ip = "errore";
        }
        
        // dati pc 
        username = System.getProperty("user.name");
        home = System.getProperty("user.home");


        String path1 = home + "\\Desktop\\prova.txt";

        File file = new File( path1);
        long mod = file.lastModified();
        
        while (true) {
            if ( mod!=file.lastModified() ) {
                mod = file.lastModified();
                data = System.currentTimeMillis();
                log( home, username, hostname, ip, data);
                invia( home, username, hostname, ip, data, "ciao");
                System.out.println("modificato");
            }
            Thread.sleep(2*1*1000); // aspetta 2 minuti
        }
        
    }
    
}
