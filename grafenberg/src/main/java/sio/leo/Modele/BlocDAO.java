package sio.leo.Modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BlocDAO {
    private ConnexionDAO connexionDAO;

    public BlocDAO() {
        this.connexionDAO = new ConnexionDAO();
    }

    // Retourne un bloc par son numéro
    public Bloc getBloc(int numero) {
        Bloc bloc = null;
        String query = "SELECT BLO_NUM, BLO_CHARGES FROM BLOC WHERE BLO_NUM = ?";
        try (Connection conn = connexionDAO.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, numero);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    bloc = new Bloc();
                    bloc.setNum(rs.getInt("BLO_NUM"));
                    bloc.setCharges(rs.getInt("BLO_CHARGES"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bloc;
    }
}