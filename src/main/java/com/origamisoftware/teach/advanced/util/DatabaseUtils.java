package com.origamisoftware.teach.advanced.util;

import com.ibatis.common.jdbc.ScriptRunner;
import com.origamisoftware.teach.advanced.services.DatabaseStockFavoritesService;
import com.origamisoftware.teach.advanced.services.DatabaseStockService;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.hibernate.SessionFactory;

/**
 * A class that contains database related utility methods.
 */
public class DatabaseUtils {
    

   
    // in a real program these values would be a configurable property and not hard coded.
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/stocks?useSSL=false";

    //  Database credentials
    private static final String USER = "monty";
    private static final String PASS = "some_pass";
    
    public static final String initializationFile = "./src/main/sql/db_initialization.sql";
    
    private static SessionFactory sessionFactory;
    private static Configuration configuration;
    
    /*
   * @return SessionFactory for use with database transactions
   */
    public static SessionFactory getSessionFactory() {

        // singleton pattern
        synchronized (DatabaseStockFavoritesService.class) {
            if (sessionFactory == null) {

                Configuration configuration = getConfiguration();

                ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .buildServiceRegistry();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            }
        }
        return sessionFactory;
    }
    
     /**
     * Create a new or return an existing database configuration object.
     *
     * @return a Hibernate Configuration instance.
     */
    private static Configuration getConfiguration() {

        synchronized (DatabaseUtils.class) {
            if (configuration == null) {
                configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");
            }
        }
        return configuration;
    }

    public static Connection getConnection() throws DatabaseConnectionException{
        Connection connection = null;
        Configuration configuration = getConfiguration();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String databaseUrl = configuration.getProperty("jdbc:mysql://localhost:3306/stocks?useSSL=false");
            String username = configuration.getProperty("monty");
            String password = configuration.getProperty("some_pass");
            connection =   DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException  | SQLException e)  {
           throw new  DatabaseConnectionException("Could not connection to database." + e.getMessage(), e);
        }
        return connection;
    }

    /**
     * A utility method that runs a db initialize script.
     * @param initializationScript    full path to the script to run to create the schema
     * @throws DatabaseInitializationException
     */
    public static void initializeDatabase(String initializationScript) throws DatabaseInitializationException {

        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            ScriptRunner runner = new ScriptRunner(connection, false, false);
            InputStream inputStream = new  FileInputStream(initializationScript);

            InputStreamReader reader = new InputStreamReader(inputStream);

            runner.runScript(reader);
            reader.close();
            connection.commit();
            connection.close();

        } catch (DatabaseConnectionException | SQLException |IOException e) {
           throw new DatabaseInitializationException("Could not initialize db because of:"
                   + e.getMessage(),e);
        }


    }


}
