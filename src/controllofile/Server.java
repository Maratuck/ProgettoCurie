package controllofile;

import static controllofile.ControlloFile.home;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import javax.swing.*;
import java.net.*;
import java.io.*;

public class Server {
    
    static String PTcartella = "\\BAA";
    static String PTlog = "\\cb.log";
    static String PTconfig = "\\config";
    static String PTicona = "\\icona.png";
    
    static String home;
    
    static String serverIp = "127.0.0.1"; 
    static int portNumber = 4444; 
    
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
    
    public static void main(String[] args) {
        
        home = System.getProperty("user.home");
        
        
        new MenuEvent( PTcartella, PTconfig, home);
        creazioneMenu();

        //caricamento config --PERCORSO DA SISTEMARE
       BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader( home + PTcartella + PTconfig));
            reader.readLine();                                  //salta la prima riga
            portNumber = Integer.parseInt(reader.readLine());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            serverIp = "127.0.0.1";
            portNumber = 4444;
        }



        while (true) {
            try (
                    ServerSocket serverSocket = new ServerSocket(portNumber);
                    Socket clientSocket = serverSocket.accept();
                    PrintWriter out =
                            new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()));
            ) {

                String inputLine = "";

                while (inputLine != null) {
                    inputLine = in.readLine();
                    if (inputLine != null) {
                        //System.out.println(inputLine);
                        JOptionPane.showMessageDialog(new JFrame(),
                                inputLine,
                                "Cambio Sfondo",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
            } catch (IOException e) {
                System.err.println("Exception caught when trying to listen on port "
                        + portNumber + " or listening for a connection");
                System.err.println(e.getMessage());
            }
        }
    }
}
