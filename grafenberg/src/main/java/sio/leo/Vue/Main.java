package sio.leo.Vue;

import java.sql.Connection;
import java.sql.SQLException;

import sio.leo.Modele.ConnexionDAO;

public class Main {
    public static void main(String[] args) {
       ConnexionDAO connexionDAO = new ConnexionDAO();

        System.out.println("Tentative de connexion à la base de données...");

        // 2. Utilisation du try-with-resources pour ouvrir et fermer automatiquement la connexion
        try (Connection conn = connexionDAO.getConnection()) {
            
            if (conn != null && !conn.isClosed()) {
                System.out.println("Connexion réussie avec succès !");
                
                // Optionnel : Afficher des informations sur la base connectée
                System.out.println("Connecté à : " + conn.getMetaData().getDatabaseProductName());
            } else {
                System.out.println("Échec de la connexion (la connexion est nulle ou fermée).");
            }

        } catch (SQLException e) {
            // 3. Gestion des erreurs en cas de problème réseau, mauvais identifiants, etc.
            System.err.println("Erreur de connexion : " + e.getMessage());
            e.printStackTrace();
        }
    }
}