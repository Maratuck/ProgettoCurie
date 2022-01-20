package it.enne.curie.server;

import javax.swing.*;
import java.awt.*;

public class MenuEvent extends TrayIcon {

    public MenuEvent(String icon) {
        super(new ImageIcon(icon, "tray icon").getImage(), "Controllo Sfondo");
    }

    public void setup() {
        if (!SystemTray.isSupported()) {
            System.err.println("SystemTray is not supported");
            return;
        }

        PopupMenu popup = new PopupMenu();
        SystemTray tray = SystemTray.getSystemTray();

        // Create a pop-up menu components
        MenuItem readLogItem = new MenuItem("Info");

        //action
        readLogItem.addActionListener(e -> {
            //TODO: richiesta DB
        });

        //Add components to pop-up menu
        popup.add(readLogItem);

        setPopupMenu(popup);

        try {
            tray.add(this);
        } catch (AWTException e) {
            System.err.println("TrayIcon could not be added.");
        }
    }
}

