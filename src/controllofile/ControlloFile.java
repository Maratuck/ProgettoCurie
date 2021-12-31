package controllofile;

import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.util.Date;


public class ControlloFile{

    static String PTcartella = "\\BAA";
    static String PTlog = "\\cb.log";
    static String PTconfig = "\\config";
    static String PTicona = "\\icona.png";
    
    static String PTfilecontrollare = "\\AppData\\Roaming\\Microsoft\\Windows\\Themes\\TranscodedWallpaper";

    static String hostname;
    static String ip;
    static String username;
    static String home;


    static String serverIp = "127.0.0.1";          // ip server
    static int portNumber = 4444;           // porta server

    public static void log( String data ){

        File file = new File( home + PTcartella + PTlog);

        try {
            BufferedWriter br = new BufferedWriter( new FileWriter( file, true) );
            br.append( "\"" + home + "\"" + ";" + "\""+ username + "\"" + ";" + "\"" + hostname + "\"" + ";" + "\"" + ip + "\"" + ";" + "\"" + data + "\"" +"\n");
            br.close();
        } catch (Exception e) {
            System.err.println("errore aggiunta file log");
        } finally {
        }
    }

    public static void creazioneMenu() {
        //menu
        //System.setProperty("apple.laf.useScreenMenuBar", "true");  //per apple
        //Check the SystemTray is supported
        if (!SystemTray.isSupported()) {
            System.err.println("SystemTray is not supported");
            return;
        }

        final Image immagine = Toolkit.getDefaultToolkit().getImage(home + PTcartella +PTicona);
        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon = new TrayIcon( immagine);
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
            System.err.println("TrayIcon could not be added.");
        }
    }
    
    public static void main(String[] args) throws InterruptedException, IOException {

        String data;

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
            File file = new File( home + PTcartella + PTlog);
            if ( !file.exists() ) {
                file.createNewFile();
            }
        } catch ( Exception e) {
            System.err.println("errore creazione file log");
        }
        
        // file log controllo
        try {
            File file = new File( home + PTcartella + PTconfig);
            if ( !file.exists() ) {
                file.createNewFile();
                BufferedWriter bw = new BufferedWriter(new FileWriter( home + PTcartella + PTconfig));
                bw.write( "127.0.0.1\n4444\n");
                bw.close();
            }
        } catch ( Exception e) {
            System.err.println("errore creazione file config");
        }

        //caricamento config --PERCORSO DA SISTEMARE
        try {
            BufferedReader reader = new BufferedReader(new FileReader( home + PTcartella + PTconfig));
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

        String path1 = home + PTfilecontrollare;

        File file = new File( path1);
        long mod = file.lastModified();
        
        new MenuEvent( PTcartella, PTconfig, home);
        creazioneMenu();

        Thread tw;

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
