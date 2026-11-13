package sio.leo.Modele;

import java.sql.Date;
import java.util.ArrayList;

import sio.leo.Controleur.CalculCouts;

// Test du calcul des couts (classe CalculCouts)
// On reprend les exemples de l'annexe 1 du sujet
// Pour lancer : Run dans VS Code ou avec java dans le terminal
public class CalculCoutsTest {

    static int nbErreurs = 0;

    public static void main(String[] args) {
        System.out.println("=== Tests CalculCouts ===");

        testJourSemaine();
        testNuitDimanche();
        testNuitSemaine();
        testJourFerie();

        System.out.println();
        if (nbErreurs == 0) {
            System.out.println("Tous les tests sont OK");
        } else {
            System.out.println(nbErreurs + " test(s) en erreur");
        }
    }

    // compare le resultat attendu et le resultat obtenu
    static void verifier(String nomTest, double attendu, double obtenu) {
        if (attendu == obtenu) {
            System.out.println("OK      " + nomTest + " = " + obtenu);
        } else {
            System.out.println("ERREUR  " + nomTest + " : attendu " + attendu + " mais obtenu " + obtenu);
            nbErreurs++;
        }
    }

    // exemple 2 de l'annexe : mardi 02/06/2026, de jour, 2 heures, type 3 sur le bloc 1
    static void testJourSemaine() {
        // les categories A, B et C avec leur cout horaire
        Categorie catA = new Categorie("A", 24);
        Categorie catB = new Categorie("B", 40);
        Categorie catC = new Categorie("C", 73);

        ArrayList<Intervenant> equipe = new ArrayList<>();
        equipe.add(new Intervenant(38, "RIEUX", catB));
        equipe.add(new Intervenant(40, "BARNARD", catA));
        equipe.add(new Intervenant(26, "KNOCK", catC));
        equipe.add(new Intervenant(30, "CABROL", catA));

        Bloc bloc = new Bloc(1, 1524);
        TypeOperation type = new TypeOperation();
        type.setCode(3);
        type.setCharges(343);
        type.setBloc(bloc);

        Intervention inter = new Intervention(equipe, 12477, Date.valueOf("2026-06-02"), false, type);
        inter.setDuree(2);


        double coutPersonnel = CalculCouts.calculerCoutMainDoeuvre(inter, equipe);
        double coutRevient = CalculCouts.calculerCoutDeRevient(inter);

        // (40 + 24 + 73 + 24) x 2h = 322
        verifier("jour semaine - cout du personnel", 322, coutPersonnel);
        // 322 + 343 x 2 (charges operation payees a l heure) + 1524 = 2532
        verifier("jour semaine - cout de revient", 2532, coutRevient);
    }

    // exemple 1 de l'annexe : dimanche de nuit, 1 heure -> tarif x3
    static void testNuitDimanche() {
        Categorie catA = new Categorie("A", 24);
        Categorie catB = new Categorie("B", 40);
        Categorie catC = new Categorie("C", 73);

        ArrayList<Intervenant> equipe = new ArrayList<>();
        equipe.add(new Intervenant(26, "KNOCK", catC));
        equipe.add(new Intervenant(40, "BARNARD", catA));
        equipe.add(new Intervenant(30, "CABROL", catA));
        equipe.add(new Intervenant(42, "HOUSE", catB));

        Bloc bloc = new Bloc(1, 1524);
        TypeOperation type = new TypeOperation();
        type.setCode(3);
        type.setCharges(343);
        type.setBloc(bloc);

        // le 31/05/2026 est un dimanche
        Intervention inter = new Intervention(equipe, 12461, Date.valueOf("2026-05-31"), true, type);
        inter.setDuree(1);


        double coutPersonnel = CalculCouts.calculerCoutMainDoeuvre(inter, equipe);
        double coutRevient = CalculCouts.calculerCoutDeRevient(inter);

        // 219 + 72 + 72 + 120 = 483
        verifier("dimanche nuit - cout du personnel", 483, coutPersonnel);
        // 483 + 343 + 1524 = 2350
        verifier("dimanche nuit - cout de revient", 2350, coutRevient);
    }

    // un jeudi de nuit -> tarif x2
    static void testNuitSemaine() {
        Categorie catA = new Categorie("A", 24);

        ArrayList<Intervenant> equipe = new ArrayList<>();
        equipe.add(new Intervenant(3, "Personnel A03", catA));

        Intervention inter = new Intervention(equipe, 3, Date.valueOf("2026-09-03"), true, null);
        inter.setDuree(8);

        // 24 x 2 x 8h = 384
        verifier("nuit semaine - cout du personnel", 384, CalculCouts.calculerCoutMainDoeuvre(inter, equipe));
    }

    // le 14 juillet est ferie -> tarif x3 meme de jour
    static void testJourFerie() {
        Categorie catB = new Categorie("B", 40);

        ArrayList<Intervenant> equipe = new ArrayList<>();
        equipe.add(new Intervenant(1, "Personnel B01", catB));

        Intervention inter = new Intervention(equipe, 50, Date.valueOf("2026-07-14"), false, null);
        inter.setDuree(1);

        // 40 x 3 x 1h = 120
        verifier("14 juillet - cout du personnel", 120, CalculCouts.calculerCoutMainDoeuvre(inter, equipe));
    }
}
