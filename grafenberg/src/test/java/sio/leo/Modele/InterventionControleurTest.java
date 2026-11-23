package sio.leo.Modele;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import sio.leo.Controleur.InterventionControleur;

// Tests du controleur avec des DAO simules : aucune connexion a MySQL.
// Comme CalculCoutsTest, cette classe se lance avec sa methode main.
public class InterventionControleurTest {

    static int nbErreurs = 0;

    public static void main(String[] args) {
        System.out.println("=== Tests InterventionControleur ===");

        testListerInterventions();
        testInterventionComplete();
        testInterventionInexistante();
        testAjoutIntervention(false);
        testAjoutIntervention(true);
        testDonneesInvalides();
        testTypeOperationInexistant();
        testMatriculeInexistant();
        testEchecInsertion();

        System.out.println();
        if (nbErreurs == 0) {
            System.out.println("Tous les tests sont OK");
        } else {
            throw new AssertionError(nbErreurs + " test(s) en erreur");
        }
    }

    // Compare aussi bien les nombres que les dates, listes et valeurs nulles.
    static void verifier(String nomTest, Object attendu, Object obtenu) {
        if (Objects.equals(attendu, obtenu)) {
            System.out.println("OK      " + nomTest);
        } else {
            System.out.println("ERREUR  " + nomTest + " : attendu " + attendu + " mais obtenu " + obtenu);
            nbErreurs++;
        }
    }

    static void testListerInterventions() {
        DonneesTest donnees = new DonneesTest();
        verifier("liste vide", new ArrayList<Intervention>(), donnees.controleur.listerInterventions());

        donnees.interventionDAO.interventions.add(donnees.intervention);
        verifier("liste des interventions", donnees.interventionDAO.interventions,
                donnees.controleur.listerInterventions());
    }

    static void testInterventionComplete() {
        DonneesTest donnees = new DonneesTest();
        donnees.interventionDAO.interventions.add(donnees.intervention);

        Intervention resultat = donnees.controleur.getInterventionComplete(12477);

        verifier("intervention retrouvee", donnees.intervention, resultat);
        verifier("numero recherche", 12477, donnees.interventionDAO.numeroRecherche);
        verifier("numero utilise pour charger l'equipe", 12477, donnees.intervenantDAO.numeroRecherche);
        verifier("chargement de l'equipe", donnees.intervenantDAO.equipe,
                resultat == null ? null : resultat.getIntervenants());
    }

    static void testInterventionInexistante() {
        DonneesTest donnees = new DonneesTest();
        verifier("intervention inexistante", null, donnees.controleur.getInterventionComplete(99999));
        verifier("aucun chargement d'equipe si intervention absente", 0,
                donnees.intervenantDAO.nbChargementsEquipe);
    }

    static void testAjoutIntervention(boolean nuit) {
        DonneesTest donnees = new DonneesTest();
        String nom = nuit ? "ajout de nuit" : "ajout de jour";

        int numero = donnees.controleur.ajouterIntervention(donnees.date, 2.5, nuit, 3, donnees.matricules);
        Intervention resultat = donnees.interventionDAO.interventionInseree;

        verifier(nom + " - numero retourne", 12478, numero);
        verifier(nom + " - une seule insertion", 1, donnees.interventionDAO.nbInsertions);
        verifier(nom + " - intervention transmise au DAO", true, resultat != null);
        if (resultat == null) {
            return;
        }
        verifier(nom + " - numero enregistre", 12478, resultat.getNumero());
        verifier(nom + " - date", donnees.date, resultat.getDate());
        verifier(nom + " - duree", 2.5, resultat.getDuree());
        verifier(nom + " - nuit", nuit, resultat.isNuit());
        verifier(nom + " - type d'operation", donnees.type, resultat.getTypeOperation());
        verifier(nom + " - intervenants", donnees.intervenantDAO.equipe, resultat.getIntervenants());
    }

    static void testDonneesInvalides() {
        DonneesTest donnees = new DonneesTest();
        verifier("date absente", -1,
                donnees.controleur.ajouterIntervention(null, 2, false, 3, donnees.matricules));
        verifier("duree nulle", -1,
                donnees.controleur.ajouterIntervention(donnees.date, 0, false, 3, donnees.matricules));
        verifier("duree negative", -1,
                donnees.controleur.ajouterIntervention(donnees.date, -1, false, 3, donnees.matricules));
        verifier("liste des matricules absente", -1,
                donnees.controleur.ajouterIntervention(donnees.date, 2, false, 3, null));
        verifier("liste des matricules vide", -1,
                donnees.controleur.ajouterIntervention(donnees.date, 2, false, 3, new ArrayList<String>()));
        verifier("donnees invalides - aucune recherche de type", 0, donnees.typeOperationDAO.nbRecherches);
        verifier("donnees invalides - aucune insertion", 0, donnees.interventionDAO.nbInsertions);
    }

    static void testTypeOperationInexistant() {
        DonneesTest donnees = new DonneesTest();
        verifier("type d'operation inexistant", -1,
                donnees.controleur.ajouterIntervention(donnees.date, 2, false, 999, donnees.matricules));
        verifier("type inexistant - aucune insertion", 0, donnees.interventionDAO.nbInsertions);
    }

    static void testMatriculeInexistant() {
        DonneesTest donnees = new DonneesTest();
        // Un matricule valide suivi d'un inconnu ne doit provoquer aucune insertion partielle.
        ArrayList<String> matricules = new ArrayList<>(Arrays.asList("38", "inconnu"));
        verifier("matricule inexistant", -1,
                donnees.controleur.ajouterIntervention(donnees.date, 2, false, 3, matricules));
        verifier("matricule inexistant - aucune insertion", 0, donnees.interventionDAO.nbInsertions);
    }

    static void testEchecInsertion() {
        DonneesTest donnees = new DonneesTest();
        donnees.interventionDAO.insertionReussie = false;
        verifier("echec de l'insertion", -1,
                donnees.controleur.ajouterIntervention(donnees.date, 2, false, 3, donnees.matricules));
        verifier("echec - insertion tentee une seule fois", 1, donnees.interventionDAO.nbInsertions);
    }

    // Chaque test dispose de ses propres donnees pour rester independant des autres.
    static class DonneesTest {
        final Date date = Date.valueOf("2026-06-02");
        final TypeOperation type = new TypeOperation();
        final InterventionDAOSimule interventionDAO = new InterventionDAOSimule();
        final IntervenantDAOSimule intervenantDAO = new IntervenantDAOSimule();
        final TypeOperationDAOSimule typeOperationDAO = new TypeOperationDAOSimule();
        final ArrayList<String> matricules = new ArrayList<>(Arrays.asList("38", "40"));
        final Intervention intervention;
        final InterventionControleur controleur;

        DonneesTest() {
            type.setCode(3);
            type.setCharges(343);
            type.setBloc(new Bloc(1, 1524));
            typeOperationDAO.type = type;
            intervenantDAO.equipe.add(new Intervenant("38", "RIEUX", new Categorie("B", 40)));
            intervenantDAO.equipe.add(new Intervenant("40", "BARNARD", new Categorie("A", 24)));
            intervention = new Intervention(new ArrayList<Intervenant>(), 12477, date, false, type);
            intervention.setDuree(2);
            controleur = new InterventionControleur(interventionDAO, intervenantDAO, typeOperationDAO);
        }
    }

    static class InterventionDAOSimule extends InterventionDAO {
        final ArrayList<Intervention> interventions = new ArrayList<>();
        Intervention interventionInseree;
        int numeroRecherche;
        int nbInsertions;
        boolean insertionReussie = true;

        @Override
        public ArrayList<Intervention> getLesInterventions() {
            return interventions;
        }

        @Override
        public Intervention getIntervention(int numero) {
            numeroRecherche = numero;
            for (Intervention intervention : interventions) {
                if (intervention.getNumero() == numero) {
                    return intervention;
                }
            }
            return null;
        }

        @Override
        public int getProchainNumero() {
            return 12478;
        }

        @Override
        public boolean insererIntervention(Intervention intervention) {
            interventionInseree = intervention;
            nbInsertions++;
            return insertionReussie;
        }
    }

    static class IntervenantDAOSimule extends IntervenantDAO {
        final ArrayList<Intervenant> equipe = new ArrayList<>();
        int numeroRecherche;
        int nbChargementsEquipe;

        @Override
        public Intervenant getIntervenant(String matricule) {
            for (Intervenant intervenant : equipe) {
                if (intervenant.getMatricule().equals(matricule)) {
                    return intervenant;
                }
            }
            return null;
        }

        @Override
        public ArrayList<Intervenant> getIntervenantsParIntervention(int numeroIntervention) {
            numeroRecherche = numeroIntervention;
            nbChargementsEquipe++;
            return equipe;
        }
    }

    static class TypeOperationDAOSimule extends TypeOperationDAO {
        TypeOperation type;
        int nbRecherches;

        @Override
        public TypeOperation getTypeOperation(int code) {
            nbRecherches++;
            return type != null && type.getCode() == code ? type : null;
        }
    }
}
