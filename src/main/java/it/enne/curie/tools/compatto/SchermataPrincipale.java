package it.enne.curie.tools.compatto;

import it.enne.curie.common.CuriePaths;
import it.enne.curie.common.CustomExtension;

import javax.swing.*;

import java.io.File;
import java.io.PrintWriter;
import java.net.Socket;

public class SchermataPrincipale extends JFrame{
    private JButton INSTALLACLIENTButton;
    private JTextField tfIP;
    private JButton INSTALLASERVERButton;
    private JTextField tfPORT;
    private JTextField tfX;
    private JTextField tfY;
    private JButton CREACONFIGButton;
    private JPanel pannelloPrincipale;
    private JButton INVIAMESSAGIOButton;
    private JButton CONFIGURAFILECLASSIButton;

    public SchermataPrincipale() {
        super();
        try {
            String[] arr = CustomExtension.readDecoded(new File(CuriePaths.CONFIG));
            tfIP.setText( arr.length>0 ? arr[0] : "non c'è");
            tfPORT.setText( arr.length>1 ? arr[1] : "non c'è");
            tfX.setText( arr.length>2 ? arr[2] : "non c'è");
            tfY.setText( arr.length>3 ? arr[3] : "non c'è");
        } catch (Exception e) {}

        INSTALLASERVERButton.addActionListener(e -> {
            new SchermataInstallazioneServer();
        });
        INSTALLACLIENTButton.addActionListener(e -> {
            new SchermataInstallazioneClient();
        });
        CREACONFIGButton.addActionListener(e -> {
            new SchermataCreaConfig();
        });
        INVIAMESSAGIOButton.addActionListener(e -> {
            String ip = "127.0.0.0";
            int port = 4444;

            try {
                String[] arr = CustomExtension.readDecoded( new File("./config.mkt"));
                ip = arr[0];
                port = Integer.parseInt(arr[1]);
            } catch (Exception ee) {}

            try (Socket socket = new Socket( ip, port);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                out.println(System.getProperty("user.name"));
                System.out.println("inviato");
                JOptionPane.showMessageDialog(null, "inviato");
            } catch (Exception ee) {
                JOptionPane.showMessageDialog(null, "non inviato");
                ee.printStackTrace();
            }
        });

        CONFIGURAFILECLASSIButton.addActionListener(e -> {
            new ModificaFileClasse();
        });


        setContentPane(pannelloPrincipale);
        setSize(600,600);
        setDefaultCloseOperation( EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setVisible(true);
    }


}
