package sio.leo.Vue;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


import sio.leo.Controleur.CalculCouts;
import sio.leo.Controleur.IntervenantControleur;
import sio.leo.Controleur.InterventionControleur;
import sio.leo.Modele.ConnexionDAO;
import sio.leo.Modele.Intervenant;
import sio.leo.Modele.Intervention;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static InterventionControleur interventionControleur;
    private static IntervenantControleur intervenantControleur;

    public static void main(String[] args) {
        if (!testerConnexion()) {
            System.out.println("Le programme ne peut pas démarrer sans base de données.");
            return;
        }

        interventionControleur = new InterventionControleur();
        intervenantControleur = new IntervenantControleur();

        int choix;
        do {
            afficherMenu();
            choix = lireEntier("Votre choix : ");

            switch (choix) {
                case 1:
                    listerInterventions();
                    break;
                case 2:
                    listerIntervenants();
                    break;
                case 3:
                    calculerCoutRevient();
                    break;
                case 4:
                    listerIntervenantsParIntervention();
                    break;
                case 5:
                    ajouterIntervention();
                    break;
                case 0:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide, réessayez.");
            }
        } while (choix != 0);

        scanner.close();
    }

    // Vérifie que la base de données est joignable avant d'afficher le menu
    private static boolean testerConnexion() {
        ConnexionDAO connexionDAO = new ConnexionDAO();
        System.out.println("Tentative de connexion à la base de données...");

        try (Connection conn = connexionDAO.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Connexion réussie (" + conn.getMetaData().getDatabaseProductName() + ").");
                return true;
            }
            System.out.println("Échec de la connexion (la connexion est nulle ou fermée).");
        } catch (SQLException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
        }
        return false;
    }

    private static void afficherMenu() {
        System.out.println();
        System.out.println("===== GRAFENBERG =====");
        System.out.println("1. Lister les interventions");
        System.out.println("2. Lister les intervenants");
        System.out.println("3. Calculer le coût de revient d'une intervention");
        System.out.println("4. Lister les intervenants d'une intervention");
        System.out.println("5. Ajouter une intervention");
        System.out.println("0. Quitter");
        System.out.println("======================");
    }

    private static void listerInterventions() {
        ArrayList<Intervention> lesInterventions = interventionControleur.listerInterventions();
        if (lesInterventions.isEmpty()) {
            System.out.println("Aucune intervention trouvée.");
            return;
        }

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println();
        //print f permet de définir un format puis des arguments lors de l'écriture d'uhn texte
        // %s : string  
        // le chiffre : largeur minimale
        // le "-" l'alignement à gauche
        // %n, le saut de ligne
        System.out.printf("%-8s %-12s %-8s %-8s%n", "N°", "Date", "Durée", "Nuit");
        System.out.println("------------------------------------");
        for (Intervention i : lesInterventions) {
            String date = (i.getDate() != null) ? format.format(i.getDate()) : "-";
            System.out.printf("%-8d %-12s %-8.2f %-8s%n",
                    i.getNumero(), date, i.getDuree(), i.isNuit() ? "Oui" : "Non");
        }
    }

    private static void listerIntervenants() {
        afficherIntervenants(intervenantControleur.listerTousLesIntervenants());
    }

    private static void listerIntervenantsParIntervention() {
        int numero = lireEntier("Numéro de l'intervention : ");
        afficherIntervenants(intervenantControleur.listerIntervenants(numero));
    }

    
    private static void afficherIntervenants(ArrayList<Intervenant> lesIntervenants) {
        if (lesIntervenants.isEmpty()) {
            System.out.println("Aucun intervenant trouvé.");
            return;
        }

        System.out.println();
        System.out.printf("%-12s %-20s %-10s %-12s%n", "Matricule", "Nom", "Catégorie", "Coût/heure");
        System.out.println("--------------------------------------------------------");
        for (Intervenant p : lesIntervenants) {
            System.out.printf("%-12s %-20s %-10s %-12.2f%n",
                    p.getMatricule(), p.getNom(),
                    p.getCategorie().getCode(), p.getCategorie().getCoutHoraire());
        }
    }

    private static void calculerCoutRevient() {
        int numero = lireEntier("Numéro de l'intervention : ");
        Intervention intervention = interventionControleur.getInterventionComplete(numero);

        if (intervention == null) {
            System.out.println("Aucune intervention avec le numéro " + numero + ".");
            return;
        }

        double cout = CalculCouts.calculerCoutDeRevient(intervention);
        // .2f --> nombre flottant a deux chiffres après la virgule
        System.out.printf("Coût de revient de l'intervention %d : %.2f euros%n", numero, cout);
    }

    // Relit tant que la saisie n'est pas un entier valide
    private static int lireEntier(String message) {
        while (true) {
            System.out.print(message);
            String saisie = scanner.nextLine().trim();
            try {
                return Integer.parseInt(saisie);
            } catch (NumberFormatException e) {
                System.out.println("Veuillez saisir un nombre entier.");
            }
        }
    }

    private static void ajouterIntervention() {
    System.out.println();
    System.out.println("--- Nouvelle intervention ---");

    try {
        System.out.print("Date (jj/mm/aaaa) : ");
        //SimpleDateFormat sert à faire le pont entre un texte au format particulier et un objet Date
        //trim pour supprimer les espaces en trop
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine().trim());

        System.out.print("Durée en heures : ");
        double duree = Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));

        System.out.print("Intervention de nuit (o/n) : ");
        boolean nuit = scanner.nextLine().trim().equalsIgnoreCase("o");

        System.out.print("Code du type d'opération : ");
        int codeType = Integer.parseInt(scanner.nextLine().trim());

        ArrayList<Integer> matricules = new ArrayList<Integer>();

        // -1 pour éviter toute possibilité de correspondre à un intervenant
        System.out.println("Matricules des intervenants (-1 pour terminer) :");
        while (true) {
            System.out.print("  Matricule : ");
            int matricule = Integer.parseInt(scanner.nextLine().trim());
            if (matricule == -1) {
                break;
            }
            matricules.add(matricule);
        }

        int numero = interventionControleur.ajouterIntervention(date, duree, nuit, codeType, matricules);
        if (numero < 0) {
            System.out.println("Échec de l'ajout de l'intervention.");
        } else {
            System.out.println("Intervention " + numero + " ajoutée avec succès.");
        }
    } catch (ParseException e) {
        System.out.println("Date invalide.");
    }
}
}