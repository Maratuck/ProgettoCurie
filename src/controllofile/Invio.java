package controllofile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Invio extends Thread{

    String hostname;
    String ip;
    String username;
    String home;
    String data;

    public Invio( String home, String username, String hostname, String ip, String data) {
        this.home = home;
        this.username = username;
        this.hostname = hostname;
        this.ip = ip;
        this.data = data;
    }

    public boolean invia( String home, String username, String hostname, String ip, String data) {
        String hostName = "127.0.0.1";  // local host
        int portNumber = 4444;

        //caricamento config --PERCORSO DA SISTEMARE
        try {
            BufferedReader reader = new BufferedReader(new FileReader("PERCORSO"));
            hostName = reader.readLine();
            portNumber = Integer.parseInt(reader.readLine());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (
                Socket kkSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
        ) {
            out.println(home + " "+ username+ " " +hostname+ " " + ip+ " " + data);
            return true;

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
        }
        return false;
    }

    public void start() {
        if ( !invia( home, username, hostname, ip, data) ) {
            System.out.println("non inviato");
        }
    }

}
