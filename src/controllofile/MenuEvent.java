package controllofile;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MenuEvent {

    String PTconfig;
    String PTicona;

    public MenuEvent( String PTconfig, String PTicona) {
        this.PTconfig = PTconfig;
        this.PTicona = PTicona;    
        
        if (!SystemTray.isSupported()) {
            System.err.println("SystemTray is not supported");
            return;
        }

        final Image immagine = Toolkit.getDefaultToolkit().getImage( PTicona);
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
    
    
    
    class exitEvent implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            System.exit(0);
        }
    }

    class infoEvent implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            //caricamento config
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader( PTconfig));
                String serverIp =  reader.readLine();
                int portNumber = Integer.parseInt(reader.readLine());
                reader.close();
                JOptionPane.showMessageDialog(new JFrame(),
                        "Ip: "+ serverIp +"\nPort: "+ portNumber,
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException exception) {
                System.err.println("errore configurazione config");
            }
        }
    }

    class modifyEvent implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            String ip = JOptionPane.showInputDialog(new JFrame(),
                    "ip: ");
            String port = JOptionPane.showInputDialog(new JFrame(),
                    "porta: ");
            BufferedWriter writer;
            try {
                writer = new BufferedWriter(new FileWriter( PTconfig));
                writer.write(ip);
                writer.newLine();
                writer.write(port);
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(new JFrame(),
                    "Per applicare le modifiche è necessario riavviare!",
                    "WARNING",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
