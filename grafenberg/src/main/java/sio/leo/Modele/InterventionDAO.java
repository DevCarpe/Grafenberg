package sio.leo.Modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


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

    //nuit stockée dans un String dans la BD et boolen dans la classe donc conversion 
    private boolean convertirNuit(String valeur) {
        if (valeur == null) {
            return false;
        }
        String v = valeur.trim().toLowerCase();
        return v.equals("1") || v.equals("true") || v.equals("o")
                || v.equals("oui") || v.equals("vrai");
    }

    private double convertirDuree(String valeur) {
        if (valeur == null || valeur.trim().isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(valeur.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
// Retourne le prochain numéro d'intervention disponible (MAX + 1)
public int getProchainNumero() {
    int numero = 1;
    String query = "SELECT COALESCE(MAX(INT_NUMERO), 0) + 1 FROM INTERVENTION";
    try (Connection conn = connexionDAO.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query);
         ResultSet rs = pstmt.executeQuery()) {

        if (rs.next()) {
            numero = rs.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return numero;
}

// Insère une intervention et ses intervenants. Retourne true si tout a réussi.
public boolean insererIntervention(Intervention intervention) {
    String queryIntervention = "INSERT INTO INTERVENTION (INT_NUMERO, TOP_CODE, INT_DATE, INT_NUIT, INT_DUREE) "
                             + "VALUES (?, ?, ?, ?, ?)";
    String queryIntervenant = "INSERT INTO INTERV_PERSONNEL (PER_MATRICULE, INT_NUMERO) VALUES (?, ?)";

    try (Connection conn = connexionDAO.getConnection()) {
        conn.setAutoCommit(false); // transaction : tout ou rien
        try {
            try (PreparedStatement pstmt = conn.prepareStatement(queryIntervention)) {
                pstmt.setInt(1, intervention.getNumero());
                pstmt.setInt(2, intervention.getTypeOperation().getCode());
                pstmt.setDate(3, new java.sql.Date(intervention.getDate().getTime()));
                pstmt.setString(4, intervention.isNuit() ? "1" : "0");
                pstmt.setString(5, String.valueOf(intervention.getDuree()));
                pstmt.executeUpdate();
            }

            try (PreparedStatement pstmt = conn.prepareStatement(queryIntervenant)) {
                for (Intervenant intervenant : intervention.getIntervenants()) {
                    pstmt.setString(1, String.valueOf(intervenant.getMatricule()));
                    pstmt.setInt(2, intervention.getNumero());
                    pstmt.executeUpdate();
                }
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            conn.rollback();
            e.printStackTrace();
            return false;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}





