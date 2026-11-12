package sio.leo.Modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IntervenantDAO {
    private ConnexionDAO connexionDAO;

    public IntervenantDAO() {
        this.connexionDAO = new ConnexionDAO();
    }

    // Retourne un intervenant par son matricule (null si introuvable)
    public Intervenant getIntervenant(int matricule) {
        Intervenant intervenant = null;
        String query = "SELECT p.PER_MATRICULE, p.PER_NOM, p.CAT_CODE, c.CAT_COUTHORAIRE "+ "FROM PERSONNEL p "+ "INNER JOIN CATEGORIE_PERSONNEL c ON p.CAT_CODE = c.CAT_CODE "+ "WHERE p.PER_MATRICULE = ?";
        try (Connection conn = connexionDAO.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // PER_MATRICULE est un CHAR(32) : on le passe en String
            pstmt.setString(1, String.valueOf(matricule));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    intervenant = creerIntervenant(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return intervenant;
    }

    // Retourne les intervenants d'une intervention
    public ArrayList<Intervenant> getIntervenantsParIntervention(int numIntervention) {
        ArrayList<Intervenant> lesIntervenants = new ArrayList<Intervenant>();
        String query = "SELECT p.PER_MATRICULE, p.PER_NOM, p.CAT_CODE, c.CAT_COUTHORAIRE "
                     + "FROM PERSONNEL p "
                     + "INNER JOIN CATEGORIE_PERSONNEL c ON p.CAT_CODE = c.CAT_CODE "
                     + "INNER JOIN INTERV_PERSONNEL i ON p.PER_MATRICULE = i.PER_MATRICULE "
                     + "WHERE i.INT_NUMERO = ?";
        try (Connection conn = connexionDAO.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, numIntervention);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lesIntervenants.add(creerIntervenant(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lesIntervenants;
    }

    // Construit un Intervenant avec sa Categorie à partir de la ligne courante
    private Intervenant creerIntervenant(ResultSet rs) throws SQLException {
        Categorie categorie = new Categorie(
                rs.getString("CAT_CODE"),
                rs.getDouble("CAT_COUTHORAIRE"));

        return new Intervenant(
                Integer.parseInt(rs.getString("PER_MATRICULE").trim()),
                rs.getString("PER_NOM"),
                categorie);
    }

    public ArrayList<Intervenant> getLesIntervenants() {
        ArrayList<Intervenant> lesIntervenants = new ArrayList<Intervenant>();
        String query = "SELECT p.PER_MATRICULE, p.PER_NOM, p.CAT_CODE, c.CAT_COUTHORAIRE "
                    + "FROM PERSONNEL p "
                    + "INNER JOIN CATEGORIE_PERSONNEL c ON p.CAT_CODE = c.CAT_CODE "
                    + "ORDER BY p.PER_NOM";
        try (Connection conn = connexionDAO.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                lesIntervenants.add(creerIntervenant(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lesIntervenants;
    }
}