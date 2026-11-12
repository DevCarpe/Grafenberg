package sio.leo.Controleur;

import java.util.ArrayList;
import sio.leo.Modele.Intervenant;
import sio.leo.Modele.IntervenantDAO;
import sio.leo.Modele.Intervention;
import sio.leo.Modele.InterventionDAO;

public class InterventionControleur {

    private InterventionDAO interventionDAO;
    private IntervenantDAO intervenantDAO;

    public InterventionControleur() {
        this.interventionDAO = new InterventionDAO();
        this.intervenantDAO = new IntervenantDAO();
    }

    // Récupère toutes les interventions depuis la base de données
    public ArrayList<Intervention> listerInterventions() {
        return this.interventionDAO.getLesInterventions();
    }

    // Récupère une intervention précise et charge ses intervenants
    public Intervention getInterventionComplete(int numeroIntervention) {
        Intervention intervention = this.interventionDAO.getIntervention(numeroIntervention);
        
        if (intervention != null) {
            ArrayList<Intervenant> lesIntervenants = this.intervenantDAO.getIntervenantsParIntervention(numeroIntervention);
            intervention.setIntervenants(lesIntervenants);
        }
        
        return intervention;
    }

    
    
}
