package controllofile;

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
    
    String serverIp = "127.0.0.1";  // local host
    int portNumber = 4444;

    public Invio( String home, String username, String hostname, String ip, String data, String serverIp, int portNumber) {
        this.home = home;
        this.username = username;
        this.hostname = hostname;
        this.ip = ip;
        this.data = data;
        this.serverIp = serverIp;
        this.portNumber = portNumber;
    }

    public boolean invia( String home, String username, String hostname, String ip, String data) {


        try (
                Socket kkSocket = new Socket(serverIp, portNumber);
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
        ) {
            out.println(home + " "+ username+ " " +hostname+ " " + ip+ " " + data);
            return true;

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + serverIp);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + serverIp);
        }
        return false;
    }

    public void start() {
        if ( !invia( home, username, hostname, ip, data) ) {
            System.out.println("non inviato");
        }
    }

}
