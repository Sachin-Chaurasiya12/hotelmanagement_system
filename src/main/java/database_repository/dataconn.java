/*
 *  Copyright 2025 Sachin chaurasiya.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  ---------------------------------------------------------------------------
 *  File : dataconn.java
 *  description : handles the database connection between sql and java file
 *  as well as other java folders for data fetching.
 *  Author : Sachin Chaurasiya  
 *  Created : August 2025
 */

package database_repository;
/*
 * These are the imports which returns the connection pool 
 * and Sql database connection
 */
import org.ini4j.Ini;

import java.io.File;
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
        /*
         * Returns the connection between MySQL and java program 
         * Also contains the Connection pool.
         */
        try {
            Ini ini = new Ini(new File("D:\\Hotelmanagementsystem\\hotelmanagement_system\\config.ini"));
            String url = ini.get("database", "database.url");
            String user = ini.get("database", "database.user");
            String pass = ini.get("database", "database.password");
            int poolsize = Integer.parseInt(ini.get("database", "database.PoolSize"));
            int timeout = Integer.parseInt(ini.get("database", "database.timeout"));
            String drivername = ini.get("database", "database.drivername");
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(url);
            config.setUsername(user);
            config.setPassword(pass);
            config.setMaximumPoolSize(poolsize);
            config.setConnectionTimeout(timeout);
            config.setDriverClassName(drivername);

            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
     * This helps to initialize the connection source in other file
     * if not connected.
     */
    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            initDataSource(); // auto-init if not done
        }
        return dataSource.getConnection();
    }
}
