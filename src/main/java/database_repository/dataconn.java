package database_repository;

import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class dataconn {

    private static HikariDataSource dataSource;

    public static void initDataSource() {
        if (dataSource != null) {
            return; // Already initialized
        }

        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://localhost:3306/HM_system");
            config.setUsername("root");
            config.setPassword("fycs");
            config.setMaximumPoolSize(10);
            config.setConnectionTimeout(30000);
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");

            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            initDataSource(); // auto-init if not done
        }
        return dataSource.getConnection();
    }
}
