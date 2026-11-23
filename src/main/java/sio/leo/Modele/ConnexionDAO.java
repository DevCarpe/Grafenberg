package sio.leo.Modele;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

//public class ConnexionDAO {
//    
//    private final String URL = "jdbc:mysql://192.168.5.120:3307/grafenberg02";
//    private final String USER = "nour";
//    private final String PWD="pwnour";
//
//    public ConnexionDAO(){
//
//    }
//
//    public Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(URL, USER, PWD);
//    }
//
//    public PreparedStatement getPreparedStatement(String sql) throws SQLException{
//        return (PreparedStatement) getConnection().prepareStatement(sql);
//    }
//
//    public Statement getStatement() throws SQLException{
//        return (Statement) getConnection().createStatement();
//    }
//}

public class ConnexionDAO {
     public  Connection getConnection() {
               try {
        InitialContext ctx = new InitialContext();
     System.out.println("InitialContext OK");
//      La méthode ctx.lookup("java:/DS_gestionUsers") retourne un objet de type Object.
        Object obj = ctx.lookup("java:/DSgrafenberg");
System.out.println("LOOKUP OK : " + obj);
        DataSource ds = (DataSource) obj;
        Connection con = ds.getConnection();
      System.out.println("CONNECTION OK : " + (con != null));
        return con;
        } catch (NamingException | SQLException e) {
            System.err.println("ERREUR JNDI / JDBC :");
            e.printStackTrace();
            return null;
        }
    }
}