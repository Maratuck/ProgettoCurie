package it.enne.curie.tools.compatto;

import it.enne.curie.common.CustomExtension;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SchermataCreaConfig extends JFrame{

    private JPanel pannelloPrincipale;
    private JTextField textFieldIP;
    private JTextField textFieldPORT;
    private JTextField textFieldX;
    private JTextField textFieldY;
    private JButton creaConfigButton;

    public SchermataCreaConfig() {


        try {
            textFieldIP.setText(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        textFieldPORT.setText("45674");

        creaConfigButton.addActionListener( e -> {
            File file = new File("./config.mkt");
            try {
                file.delete();
                file.createNewFile();
                CustomExtension.writeEncoded( new String[]{textFieldIP.getText(), textFieldPORT.getText(), textFieldX.getText(), textFieldY.getText()}, file);
            } catch (IOException ee) {
                JOptionPane.showMessageDialog(null, "error : creazione file config non riuscito");
            }
            JOptionPane.showMessageDialog(null, "creazione file config");
        });

        setContentPane(pannelloPrincipale);
        setSize(600,600);
        setLocationRelativeTo(null);

        setVisible(true);
    }
}
