package sio.leo.Modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InterventionDAO {
    private ConnexionDAO connexionDAO; 
    
    public InterventionDAO() 
    {
        this.connexionDAO = new ConnexionDAO(); 
    }

    
    //  Retourne le coût horaire d'une catégorie de personnel ('A', 'B' ou 'C')
    
    public double getCoutHeure(String cat) 
    {
        double cout = 0.0; 
        String query = "SELECT CAT_COUTHORAIRE FROM CATEGORIE_PERSONNEL WHERE CAT_CODE = ?"
            try (Connection conn = connexionDAO.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1 , cat);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()){
                cout = rs.getDouble("CAT_COUTHORAIRE");
            }


        }catch (SQLException e) {
            e.printStackTrace();
        }  
        return cout;
        
    }

}

