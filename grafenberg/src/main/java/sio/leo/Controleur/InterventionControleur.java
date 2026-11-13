package sio.leo.Controleur;

import java.util.ArrayList;
import sio.leo.Modele.Intervenant;
import sio.leo.Modele.IntervenantDAO;
import sio.leo.Modele.Intervention;
import sio.leo.Modele.InterventionDAO;
import sio.leo.Modele.TypeOperation;
import sio.leo.Modele.TypeOperationDAO;

public class InterventionControleur {

    private InterventionDAO interventionDAO;
    private IntervenantDAO intervenantDAO;
    private TypeOperationDAO typeOperationDAO;

    public InterventionControleur() {
        this(new InterventionDAO(), new IntervenantDAO(), new TypeOperationDAO());
    }

    // Permet de fournir des DAO simules pour les tests sans base de donnees.

    /**
     * Constructeur avec injection de dépendances.
     * Permet de fournir des DAO simulés (mocks), notamment pour les tests unitaires sans base de données.
     *
     * @param interventionDAO  le DAO pour la gestion des interventions
     * @param intervenantDAO   le DAO pour la gestion des intervenants
     * @param typeOperationDAO le DAO pour la gestion des types d'opérations
     */
    public InterventionControleur(InterventionDAO interventionDAO, IntervenantDAO intervenantDAO,
            TypeOperationDAO typeOperationDAO) {
        this.interventionDAO = interventionDAO;
        this.intervenantDAO = intervenantDAO;
        this.typeOperationDAO = typeOperationDAO;
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

    public int ajouterIntervention(java.util.Date date, double duree, boolean nuit, int codeTypeOperation, ArrayList<String> matricules) {
        // gestion de certaines erreurs possibles
        if (date == null || duree <= 0 || matricules == null || matricules.isEmpty()) {
            return -1;
        }

        TypeOperation type = this.typeOperationDAO.getTypeOperation(codeTypeOperation);
        if (type == null) {
            return -1; // type d'opération inexistant
        }

        ArrayList<Intervenant> intervenants = new ArrayList<Intervenant>();
        for (String matricule : matricules) {
            Intervenant intervenant = this.intervenantDAO.getIntervenant(matricule);
            if (intervenant == null) {
                return -1; // matricule inexistant
            }
            intervenants.add(intervenant);
        }

        int numero = this.interventionDAO.getProchainNumero();
        Intervention intervention = new Intervention(intervenants, numero, date, nuit, type);
        intervention.setDuree(duree);

        return this.interventionDAO.insererIntervention(intervention) ? numero : -1;
    }
}
