 package sio.leo.Modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TypeOperationDAO {
    private ConnexionDAO connexionDAO;
    private BlocDAO blocDAO;

    public TypeOperationDAO() {
        this.connexionDAO = new ConnexionDAO();
        this.blocDAO = new BlocDAO();
    }
    
    public ArrayList<TypeOperation> getAll() {
    ArrayList<TypeOperation> listeTypes = new ArrayList<>();
    String query = "SELECT TOP_CODE, BLO_NUM, TOP_CHARGES FROM TYPE_OPERATION";
    
    try (Connection conn = connexionDAO.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query);
         ResultSet rs = pstmt.executeQuery()) {
        
        while (rs.next()) {
            TypeOperation typeOp = new TypeOperation(
                rs.getInt("TOP_CODE"),
                rs.getInt("TOP_CHARGES"),
                blocDAO.getBloc(rs.getInt("BLO_NUM"))
            );
            listeTypes.add(typeOp);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return listeTypes;
}

    // Retourne un type d'opération par son code (null si introuvable)
    public TypeOperation getTypeOperation(int code) {
        TypeOperation typeOperation = null;
        String query = "SELECT TOP_CODE, BLO_NUM, TOP_CHARGES FROM TYPE_OPERATION WHERE TOP_CODE = ?";
        try (Connection conn = connexionDAO.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, code);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    typeOperation = new TypeOperation(rs.getInt("TOP_CODE"), 
                    rs.getInt("TOP_CHARGES"),
                    blocDAO.getBloc(rs.getInt("BLO_NUM")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeOperation;
    }
}