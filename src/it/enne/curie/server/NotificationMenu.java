package it.enne.curie.server;

import it.enne.curie.server.finestra.FinestraPosizioni;

import javax.swing.*;

public class NotificationMenu extends JOptionPane {

    int result;

    public NotificationMenu(String message) {
        String[] messageOptions = new String[]{"Visualizza", "Ok"};
        result = showOptionDialog(null,
               message,
               "Cambio Sfondo",
               JOptionPane.DEFAULT_OPTION,
               JOptionPane.WARNING_MESSAGE,
               null,
                messageOptions,
               messageOptions[1]);
    }

    public void checkresult(String ipClient) {
            if (result == 0) {
                new FinestraPosizioni(ipClient);
            }
    }


}
