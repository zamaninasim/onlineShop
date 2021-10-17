package ir.maktab.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dao {
    private Connection connection;

    public Dao() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_shop", "root", "Nasim1374");
    }

    public Connection getConnection() {
        return connection;
    }
}
