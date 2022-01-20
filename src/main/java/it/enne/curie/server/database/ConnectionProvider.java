package it.enne.curie.server.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionProvider {

    private final HikariDataSource source;

    public ConnectionProvider(DatabaseProperty property) {
        source = loadHikari(property);
    }

    public HikariDataSource loadHikari(DatabaseProperty property) {
        Properties properties = new Properties();

        properties.setProperty("dataSourceClassName", "com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        properties.setProperty("dataSource.serverName", property.getHost());
        properties.setProperty("dataSource.user", property.getUsername());
        properties.setProperty("dataSource.password", property.getPassword());
        properties.setProperty("dataSource.databaseName", property.getDatabase());

        return new HikariDataSource(new HikariConfig(properties));
    }

    public Connection getConnection() throws SQLException {
        return source.getConnection();
    }
}
