package sio.leo.Modele;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionDAO {
    
    private final String URL = "jdbc:mysql://";
    private final String USER = "root";
    private final String PWD="";

    public ConnexionDAO(){}

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PWD);
    }

    public PreparedStatement getPreparedStatement(String sql) throws SQLException{
        return (PreparedStatement) getConnection().prepareStatement(sql);
    }

    public Statement getStatement() throws SQLException{
        return (Statement) getConnection().createStatement();
    }
}
