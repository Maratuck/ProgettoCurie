package it.enne.curie.server;

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

    public void checkresult() {
            if (result == 0) {
                //TODO: @enne139 da aggiungere vista banchi
                showMessageDialog(null, "prova");
            }
    }


}
