package controllofile;

import javax.swing.*;
import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) {
        int portNumber = 0;


        //caricamento config --PERCORSO DA SISTEMARE
       BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    "PERCORSO"));
            reader.readLine();                                  //salta la prima riga
            portNumber = Integer.parseInt(reader.readLine());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
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
                System.out.println("Exception caught when trying to listen on port "
                        + portNumber + " or listening for a connection");
                System.out.println(e.getMessage());
            }
        }
    }
}
