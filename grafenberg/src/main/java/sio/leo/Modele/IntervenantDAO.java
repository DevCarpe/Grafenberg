package sio.leo.Modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
                Integer.parseInt(rs.getString("PER_MATRICULE")),          
                rs.getString("CAT_NOM"),     
                categorie    
                );
                return intervenant;
            
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return intervenant;     
    }

     public ArrayList<Intervenant> getIntervenantsParIntervention(int idIntervention) {
        ArrayList<Intervenant> lesIntervenants = new ArrayList<>();
        
        
        String query = "SELECT p.*, c.CAT_COUT_HORAIRE " +
                       "FROM PERSONNEL p " +
                       "INNER JOIN CATEGORIE c ON p.CAT_CODE = c.CAT_CODE " +
                       "INNER JOIN INTERVENIR i ON p.PER_MATRICULE = i.PER_MATRICULE " +
                       "WHERE i.INT_ID = ?";

        try (Connection conn = connexionDAO.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

           
            pstmt.setInt(1, idIntervention);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int catCode = Integer.parseInt(rs.getString("CAT_CODE"));
                    double coutHoraire = rs.getDouble("CAT_COUT_HORAIRE");
                    Categorie categorie = new Categorie(catCode, coutHoraire);

                    Intervenant intervenant = new Intervenant(
                        Integer.parseInt(rs.getString("PER_MATRICULE")),
                        rs.getString("PER_NOM"),
                        categorie
                    );

                    lesIntervenants.add(intervenant);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lesIntervenants;
    }
    
}