package controllofile;

import java.io.*;
import java.net.InetAddress;
import java.util.Date;


public class ControlloFile{

    static String PTcartella = "\\BAA";
    static String PTlog = "\\cb.log";
    static String PTconfig = "\\config";
    static String PTicona = ".\\icona.png";
    
    static String PTfilecontrollare = "\\AppData\\Roaming\\Microsoft\\Windows\\Themes\\TranscodedWallpaper";

    static String hostname;
    static String ip;
    static String username;
    static String home;


    static String serverIp = "127.0.0.1";          // ip server
    static int portNumber = 4444;           // porta server

    public static void log( String data ){

        File file = new File( PTlog);

        try {
            BufferedWriter br = new BufferedWriter( new FileWriter( file, true) );
            br.append( "\"" + home + "\"" + ";" + "\""+ username + "\"" + ";" + "\"" + hostname + "\"" + ";" + "\"" + ip + "\"" + ";" + "\"" + data + "\"" +"\n");
            br.close();
        } catch (Exception e) {
            System.err.println("errore aggiunta file log");
        } finally {
        }
    }
    
    public static void main(String[] args) throws InterruptedException, IOException {

        
        String data;
        Thread tw;
        
        // dati pc
        username = System.getProperty("user.name");
        home = System.getProperty("user.home");
        
        //dati rete
        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
            ip = addr.getHostAddress();
        } catch ( Exception e) {
            hostname = "errore";
            ip = "errore";
            System.err.println("errore cercasi informazioni scheda di rete");        
        }
        
        //configurazione percori
        PTfilecontrollare = home + PTfilecontrollare;
        

        PTlog = home + PTcartella + PTlog;
        PTconfig = home + PTcartella + PTconfig;

        // cartella controllo
        try {
            File cartella = new File( PTcartella);
            if ( !cartella.exists() || !cartella.isFile() ) {
                cartella.mkdir();
            }
        } catch ( Exception e) {
            System.err.println("errore creazione cartella");
        }

        // file log controllo
        try {
            File file = new File( PTlog);
            if ( !file.exists() ) {
                file.createNewFile();
            }
        } catch ( Exception e) {
            System.err.println("errore creazione file log");
        }
        
        // file log controllo
        try {
            File file = new File( PTconfig);
            if ( !file.exists() ) {
                file.createNewFile();
                BufferedWriter bw = new BufferedWriter(new FileWriter( PTconfig));
                bw.write( "127.0.0.1\n4444\n");
                bw.close();
            }
        } catch ( Exception e) {
            System.err.println("errore creazione file config");
        }

        //caricamento config --PERCORSO DA SISTEMARE
        try {
            BufferedReader reader = new BufferedReader(new FileReader( PTconfig));
            serverIp = reader.readLine();
            portNumber = Integer.parseInt(reader.readLine());
            reader.close();
        } catch (EOFException e) {
            e.printStackTrace();
        } catch (Exception e) {
            serverIp = "127.0.0.1";
            portNumber = 4444;
            System.err.println("errore lettura porte falita");
        }     
        
        //creazione menu
        new MenuEvent( PTconfig, PTicona);
        
        File file = new File( PTfilecontrollare);
        long mod = file.lastModified();

        while (true) {
            if ( mod!=file.lastModified() ) {
                mod = file.lastModified();
                //data = System.currentTimeMillis();
                Date dataObject = new Date();
                data = dataObject.getDate() + "/" + (dataObject.getMonth()+1) + "/" + (dataObject.getYear()+1900) + " " +dataObject.getHours() + ":"+ dataObject.getMinutes();
                log( data);
                tw = new Invio( home, username, hostname, ip, data, serverIp, portNumber);
                tw.start();
                System.out.println("modificato");
            }
            Thread.sleep(2*1*1000); // aspetta 2 minuti
        }

    }


}
