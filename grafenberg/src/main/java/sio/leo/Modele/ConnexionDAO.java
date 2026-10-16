package sio.leo.Modele;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionDAO {
    
<<<<<<< HEAD
    private final String URL = "jdbc:mysql://192.168.5.120/GRAFENBERG02";
    private final String USER = "root";
    private final String PWD="";
=======
    private final String URL = "jdbc:mysql://192.168.5.10:3306/grafenberg02";
    private final String USER = "nour";
    private final String PWD="pwnour";
>>>>>>> d2ee48ed843f1cacaa4a95395a3108ba56a32700

    public ConnexionDAO(){

    }

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
