package sio.leo.Modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InterventionDAO {
    private ConnexionDAO connexionDAO;
    private TypeOperationDAO typeOperationDAO;

    private static final String SELECT_INTERVENTION =
            "SELECT INT_NUMERO, TOP_CODE, INT_DATE, INT_NUIT, INT_DUREE FROM INTERVENTION";

    public InterventionDAO() {
        this.connexionDAO = new ConnexionDAO();
        this.typeOperationDAO = new TypeOperationDAO();
    }

    // Retourne le coût horaire d'une catégorie de personnel ('A', 'B' ou 'C')
    public double getCoutHeure(String cat) {
        double cout = 0.0;
        String query = "SELECT CAT_COUTHORAIRE FROM CATEGORIE_PERSONNEL WHERE CAT_CODE = ?";
        try (Connection conn = connexionDAO.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, cat);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    cout = rs.getDouble("CAT_COUTHORAIRE");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cout;
    }

    // Retourne toutes les interventions (sans leurs intervenants)
    public ArrayList<Intervention> getLesInterventions() {
        ArrayList<Intervention> lesInterventions = new ArrayList<Intervention>();
        String query = SELECT_INTERVENTION + " ORDER BY INT_DATE DESC";
        try (Connection conn = connexionDAO.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                lesInterventions.add(creerIntervention(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lesInterventions;
    }

    // Retourne une intervention par son numéro (null si introuvable)
    public Intervention getIntervention(int numero) {
        Intervention intervention = null;
        String query = SELECT_INTERVENTION + " WHERE INT_NUMERO = ?";
        try (Connection conn = connexionDAO.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, numero);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    intervention = creerIntervention(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return intervention;
    }

    // Construit une Intervention à partir de la ligne courante du ResultSet
    private Intervention creerIntervention(ResultSet rs) throws SQLException {
        TypeOperation type = typeOperationDAO.getTypeOperation(rs.getInt("TOP_CODE"));

        Intervention intervention = new Intervention(
                new ArrayList<Intervenant>(),   // rempli ensuite par le contrôleur
                rs.getInt("INT_NUMERO"),
                rs.getDate("INT_DATE"),
                convertirNuit(rs.getString("INT_NUIT")),
                type);

        intervention.setDuree(convertirDuree(rs.getString("INT_DUREE")));
        return intervention;
    }

}



}

