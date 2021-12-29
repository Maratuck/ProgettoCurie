package controllofile;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.util.Date;


public class ControlloFile{

    static String PTcartella = "\\BAA";
    static String hostname;
    static String ip;
    static String username;
    static String home;
    static int cNI;                  // contatore messagi non inviati
    static boolean ckRI;             // variabile per vederese se si sta provando a inviare
    static boolean ckI;              // variabile per vederese se si puo iniare
    static String hostName;          // ip server
    static int portNumber;           // porta server

    public static void log( String data ){

        File file = new File( home + PTcartella + "\\cb.log");

        try {
            BufferedWriter br = new BufferedWriter( new FileWriter( file, true) );
            br.append( "\"" + home + "\"" + ";" + "\""+ username + "\"" + ";" + "\"" + hostname + "\"" + ";" + "\"" + ip + "\"" + ";" + "\"" + data + "\"" +"\n");
            br.close();
        } catch (Exception e) {
            System.err.println("errore aggiunta file log");
        } finally {
        }
    }

    public static void salvMan( String data ){

        File file = new File( home + PTcartella + "\\ma.log");

        try {
            BufferedWriter br = new BufferedWriter( new FileWriter( file, true) );
            br.append( "\"" + home + "\"" + ";" + "\""+ username + "\"" + ";" + "\"" + hostname + "\"" + ";" + "\"" + ip + "\"" + ";" + "\"" + data + "\"" +"\n");
            br.close();
        } catch (Exception e) {
            System.err.println("errore aggiunta file manc");
        } finally {
        }
    }

    public static boolean invia( String home, String username, String hostname, String ip, String data) {
        //String hostName = "127.0.0.1";  // local host
        //hostName = "192.168.1.220";
        //int portNumber = 4444;
        try (
                Socket kkSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
        ) {
            out.println(home + " "+ username+ " " +hostname+ " " + ip+ " " + data);
            return true;

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
        }
        return false;
    }

    public static void main(String[] args) throws InterruptedException, IOException {

        String data;

        cNI = 0;
        ckI = true;
        ckRI = false;
        
        //menu
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        //Check the SystemTray is supported
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }

        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon =
                new TrayIcon(Toolkit.getDefaultToolkit().getImage("PERCORSO ICONA"));
        final SystemTray tray = SystemTray.getSystemTray();

        // Create a pop-up menu components
        MenuItem infoItem = new MenuItem("Info");
        MenuItem modifyItem = new MenuItem("Modifica");
        MenuItem exitItem = new MenuItem("Exit");

        //action
        exitItem.addActionListener(new MenuEvent.exitEvent());
        infoItem.addActionListener(new MenuEvent.infoEvent());
        modifyItem.addActionListener(new MenuEvent.modifyEvent());


        //Add components to pop-up menu
        popup.add(infoItem);
        popup.addSeparator();
        popup.add(modifyItem);
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }

        //dati rete
        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
            ip = addr.getHostAddress();
        } catch ( Exception e) {
            hostname = "errore";
            ip = "errore";
        }


        //caricamento config --PERCORSO DA SISTEMARE
        try {
            BufferedReader reader = new BufferedReader(new FileReader("percorso"));
            hostName = reader.readLine();
            portNumber = Integer.parseInt(reader.readLine());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // dati pc
        username = System.getProperty("user.name");
        home = System.getProperty("user.home");

        // cartella controllo
        try {
            File cartella = new File( home + PTcartella);
            if ( !cartella.exists() || !cartella.isFile() ) {
                cartella.mkdir();
            }
        } catch ( Exception e) {
            System.err.println("errore creazione cartella");
        }

        // file log controllo
        try {
            File file = new File( home + PTcartella + "\\cb.log");
            if ( !file.exists() ) {
                file.createNewFile();
            }
        } catch ( Exception e) {
            System.err.println("errore creazione file log");
        }

        // file manc controllo
        try {
            File file = new File( home + PTcartella + "\\ma.log");
            if ( !file.exists() ) {
                file.createNewFile();
            }
        } catch ( Exception e) {
            System.err.println("errore creazione file mac");
        }

        // controlla messagi mancanti
        try {
            BufferedReader bw = new BufferedReader( new FileReader( home + PTcartella + "\\ma.log"));
            String linea;
            while ( (linea = bw.readLine()) != null) {
                cNI ++;
            }
            bw.close();
        } catch ( Exception e) {
            System.err.println("errore lettura mancanti");
        }


        String path1 = home + "\\AppData\\Roaming\\Microsoft\\Windows\\Themes\\TranscodedWallpaper";

        File file = new File( path1);
        long mod = file.lastModified();

        Thread tw;

        while (true) {
            if ( mod!=file.lastModified() ) {
                mod = file.lastModified();
                //data = System.currentTimeMillis();
                Date dataObject = new Date();
                data = dataObject.getDate() + "/" + (dataObject.getMonth()+1) + "/" + (dataObject.getYear()+1900) + " " +dataObject.getHours() + ":"+ dataObject.getMinutes();
                log( data);
                /*
                if ( !invia( home, username, hostname, ip, data) ) {
                    System.out.println("non inviato");
                    salvMan( data);
                    cNI ++;
                }
                */
                tw = new Invio( home, username, hostname, ip, data);
                tw.start();
                System.out.println("modificato");
            }
            Thread.sleep(2*1*1000); // aspetta 2 minuti
        }

    }


}
