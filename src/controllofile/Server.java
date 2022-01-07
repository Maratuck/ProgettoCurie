package controllofile;

import javax.swing.*;
import java.net.*;
import java.io.*;

public class Server {
    
    static String PTcartella = "\\BAA";
    static String PTlog = "\\cb.log";
    static String PTconfig = "\\config";
    static String PTicona = "\\icona.png";
    
    static String home;
    
    static String serverIp = "127.0.0.1"; 
    static int portNumber = 4444; 
    
    public static void main(String[] args) {
        
        home = System.getProperty("user.home");

        PTlog = home + PTcartella + PTlog;
        PTconfig = home + PTcartella + PTconfig;        
        
        new MenuEvent( PTconfig, PTicona);
        
        //caricamento config --PERCORSO DA SISTEMARE
       BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader( PTconfig));
            reader.readLine();                                  //salta la prima riga
            portNumber = Integer.parseInt(reader.readLine());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            serverIp = "127.0.0.1";
            portNumber = 4444;
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
                System.err.println("Exception caught when trying to listen on port "
                        + portNumber + " or listening for a connection");
                System.err.println(e.getMessage());
            }
        }
    }
}
