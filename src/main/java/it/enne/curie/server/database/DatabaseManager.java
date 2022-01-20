package it.enne.curie.server.database;

import it.enne.curie.common.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DatabaseManager {

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS log()";
    private static final String ADD_LOG = "INSERT INTO log VALUES(username, pc, time) VALUES(?, (SELECT id FROM pc WHERE ip = ?), ?)";

    private final ConnectionProvider connectionProvider;

    public DatabaseManager() {
        //TODO: Credenziali fisse e da config
        this.connectionProvider = new ConnectionProvider(new DatabaseProperty(
                "127.0.0.1",
                "gufo",
                "root",
                "root"
        ));

        createTables();
    }

    public ConnectionProvider getConnectionProvider() {
        return connectionProvider;
    }

    private void createTables() {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_TABLE)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addLog(Message message, String ip) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_LOG)) {
            statement.setString(1, message.toString());
            statement.setString(2, ip);
            statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
