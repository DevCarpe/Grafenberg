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
        this.interventionDAO = new InterventionDAO();
        this.intervenantDAO = new IntervenantDAO();
        this.typeOperationDAO = new TypeOperationDAO(); 
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

<<<<<<< HEAD
        TypeOperation type = this.typeOperationDAO.getTypeOperation(codeTypeOperation);
        if (type == null) {
            return -1; // type d'opération inexistant
=======

    public int ajouterIntervention(java.util.Date date, double duree, boolean nuit, int codeTypeOperation, ArrayList<String> matricules) {
        //gestion de certaines erreurs possibles
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
>>>>>>> 5ff9669a27969a4e1818af24553ada9040179507
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