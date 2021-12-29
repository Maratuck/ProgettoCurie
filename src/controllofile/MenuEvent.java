package controllofile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Inet4Address;

public class MenuEvent {
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
                reader = new BufferedReader(new FileReader(
                        "PERCORSO CONFIG"));
                reader.readLine();
                int portNumber = Integer.parseInt(reader.readLine());
                reader.close();
                JOptionPane.showMessageDialog(new JFrame(),
                        "Ip: "+ Inet4Address.getLocalHost().getHostAddress() +"\nPort: "+ portNumber,
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException exception) {

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
                writer = new BufferedWriter(new FileWriter("PERCORSO CONFIG"));
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
