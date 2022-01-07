package it.enne.curie.server;

import it.enne.curie.common.LogWriter;

import java.awt.*;

public class MenuEvent extends TrayIcon {

    private final LogWriter logWriter;

    public MenuEvent(LogWriter logWriter, String icon) {
        super(Toolkit.getDefaultToolkit().getImage(icon));

        this.logWriter = logWriter;
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
        readLogItem.addActionListener(e -> logWriter.openFile());

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

