package sio.leo.Modele;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionDAO {
   
    /**
 * Classe utilitaire fondamentale pour la gestion de la base de données MySQL.
 * Elle centralise les paramètres de connexion JDBC (URL vers le serveur 192.168.5.120:3307/grafenberg02,
 * identifiants) et fournit les méthodes pour ouvrir une connexion active, 
 * préparer des requêtes sécurisées (PreparedStatement) ou exécuter des statements simples.
 */

    
    private final String URL = "jdbc:mysql://192.168.5.120:3307/grafenberg02";
    private final String USER = "nour";
    private final String PWD="pwnour";

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
