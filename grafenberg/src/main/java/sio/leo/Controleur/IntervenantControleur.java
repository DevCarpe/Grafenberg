package sio.leo.Controleur;

import java.util.ArrayList;
import sio.leo.Modele.Intervenant;
import sio.leo.Modele.IntervenantDAO;

public class IntervenantControleur {

    private IntervenantDAO intervenantDAO;

    public IntervenantControleur() {
        this.intervenantDAO = new IntervenantDAO();
    }

    public ArrayList<Intervenant> listerIntervenants(int idIntervention) {
        return this.intervenantDAO.getIntervenantsParIntervention(idIntervention);
    }
}
