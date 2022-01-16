package it.enne.curie.server;

import it.enne.curie.server.finestra.FinestraPosizioni;

import javax.swing.*;
import java.io.IOException;

public class NotificationMenu extends JOptionPane {

    final int result;

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

    public void checkresult(String ipClient) throws IOException, ClassNotFoundException {
            if (result == 0) {
                new FinestraPosizioni(ipClient);
            }
    }


}
