package sio.leo.Modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IntervenantDAO {
    private ConnexionDAO connexionDAO; 

     public IntervenantDAO() 
    {
        this.connexionDAO = new ConnexionDAO(); 
    }

    public Intervenant getIntervenant(int matricule){
            Intervenant intervenant = null;
            String query = "SELECT p.*, c.CAT_COUT_HORAIRE FROM PERSONNEL p  INNER JOIN CATEGORIE c ON p.CAT_CODE =c.CAT_CODE WHERE PER_MATRICULE = ?";
            try (Connection conn = connexionDAO.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1 , matricule);
            ResultSet rs = pstmt.executeQuery();
            

                if(rs.next()){

                int catCode = Integer.parseInt(rs.getString("CAT_CODE"));
                double coutHoraire = rs.getDouble("CAT_COUT_HORAIRE");
                Categorie categorie = new Categorie(catCode, coutHoraire); 

                intervenant = new Intervenant(
                Integer.parseInt(rs.getString("PER_MATRICULE")),           // Remplacez par le nom exact de votre colonne ID
                rs.getString("CAT_NOM"),       // Remplacez par le nom exact de votre colonne Nom
                categorie    // Ajoutez les autres colonnes de la même manière
                );
                return intervenant;
            
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return intervenant;     
    }
    
}