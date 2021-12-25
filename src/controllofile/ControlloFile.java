package controllofile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;


public class ControlloFile {
    
    public static void log( String home, String username, String hostname, String ip, String data ) {
        
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
    
    public static void invia( String home, String username, String hostname, String ip, String data) {
        String hostName = "192.168.1.220";
        int portNumber = 4444;
        try (
                Socket kkSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
        ) {
            out.println(home + " "+ username+ " " +hostname+ " " + ip+ " " + data);

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
        
    }

    public static void main(String[] args) throws InterruptedException {
        
        String hostname;
        String ip;
        String username;
        String home;
        String data;
        
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
                //data = System.currentTimeMillis();
                Date dataObject = new Date();
                data = dataObject.getDate() + "/" + (dataObject.getMonth()+1) + "/" + (dataObject.getYear()+1900) + " " +dataObject.getHours() + ":"+ dataObject.getMinutes();
                log( home, username, hostname, ip, data);
                invia( home, username, hostname, ip, data);
                System.out.println("modificato");

            } else {

            }
            Thread.sleep(2*1*1000); // aspetta 2 minuti
        }
        
    }
    
}
