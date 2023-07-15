package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/jdbc?createDatabaseIfNotExist=true";

    private static final String USER = "root";

    private static final String PASSWORD = "root";

    private static SessionFactory sessionFactory;

    private static void initSessionFactory() {
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
        properties.put(Environment.URL, URL);
        properties.put(Environment.USER, USER);
        properties.put(Environment.PASS, PASSWORD);
        properties.put(Environment.SHOW_SQL, true);
        properties.put(Environment.FORMAT_SQL, true);
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");

        sessionFactory = new Configuration().
                addProperties(properties).
                addAnnotatedClass(User.class).
                buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            initSessionFactory();
        }
        return sessionFactory;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public Util() {
        throw new UnsupportedOperationException();
    }
}
