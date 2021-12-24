package controllofile;

import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException {

        int portNumber = 4444;
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
                        System.out.println(inputLine);
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