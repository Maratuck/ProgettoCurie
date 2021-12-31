package controllofile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MenuEvent {
    
    static String PTcartella;
    static String PTconfig;
    static String home;

    public MenuEvent( String PTcartella, String PTconfig, String home) {
        this.PTcartella = PTcartella;
        this.PTconfig = PTconfig;
        this.home = home;
    }
    
    
    
    static class exitEvent implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            System.exit(0);
        }
    }

    static class infoEvent implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            //caricamento config
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader( home + PTcartella + PTconfig));
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

    static class modifyEvent implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            String ip = JOptionPane.showInputDialog(new JFrame(),
                    "ip: ");
            String port = JOptionPane.showInputDialog(new JFrame(),
                    "porta: ");
            BufferedWriter writer;
            try {
                writer = new BufferedWriter(new FileWriter( home + PTcartella + PTconfig));
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
