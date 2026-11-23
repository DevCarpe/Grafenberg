package sio.leo.Modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategorieDAO {
    private ConnexionDAO connexionDAO;

    public CategorieDAO() {
        this.connexionDAO = new ConnexionDAO();
    }

    // Retourne toutes les catégories de personnel
    public ArrayList<Categorie> getLesCategories() {
        ArrayList<Categorie> lesCategories = new ArrayList<Categorie>();
        String query = "SELECT CAT_CODE, CAT_COUTHORAIRE FROM CATEGORIE_PERSONNEL ORDER BY CAT_CODE";
        try (Connection conn = connexionDAO.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                lesCategories.add(new Categorie(
                        rs.getString("CAT_CODE"),
                        rs.getDouble("CAT_COUTHORAIRE")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lesCategories;
    }

    // Retourne une catégorie par son code (null si introuvable)
    public Categorie getCategorie(String code) {
        Categorie categorie = null;
        String query = "SELECT CAT_CODE, CAT_COUTHORAIRE FROM CATEGORIE_PERSONNEL WHERE CAT_CODE = ?";
        try (Connection conn = connexionDAO.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, code);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    categorie = new Categorie(
                            rs.getString("CAT_CODE"),
                            rs.getDouble("CAT_COUTHORAIRE"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorie;
    }

    // Retourne le coût horaire d'une catégorie (0.0 si introuvable)
    public double getCoutHeure(String code) {
        Categorie categorie = getCategorie(code);
        return (categorie != null) ? categorie.getCoutHoraire() : 0.0;
    }
}