package it.enne.curie.common;

public class ConnectionParameters {

    public static final int DEFAULT_PORT = 4444;

    private final String ip;
    private final int port;
    private final String[] parameters;

    public ConnectionParameters(String ip, int port) {
        this.ip = ip;
        this.port = port;
        parameters = new String[]{ip, String.valueOf(port)};
    }

    public static ConnectionParameters fromArray(String[] parameters) {
        return new ConnectionParameters(parameters[0], Integer.parseInt(parameters[1]));
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String[] getParameters() {
        return parameters;
    }
}
