package controllofile;

import javax.swing.*;
import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) {
        int portNumber = 0;
        
        //MENU
        //System.setProperty("apple.laf.useScreenMenuBar", "true");  //per mac
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


        //caricamento config --PERCORSO DA SISTEMARE
       BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    "PERCORSO"));
            reader.readLine();                                  //salta la prima riga
            portNumber = Integer.parseInt(reader.readLine());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
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
                System.out.println("Exception caught when trying to listen on port "
                        + portNumber + " or listening for a connection");
                System.out.println(e.getMessage());
            }
        }
    }
}
