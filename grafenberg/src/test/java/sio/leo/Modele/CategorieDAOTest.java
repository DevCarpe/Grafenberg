package sio.leo.Modele;

import java.util.ArrayList;

// Test de la classe CategorieDAO
// Il faut etre connecte a la base de donnees (celle du lycee)
// Les valeurs attendues viennent du script d'insertion (annexe 2) :
// A = 24, B = 40, C = 73
public class CategorieDAOTest {

    static int nbErreurs = 0;

    public static void main(String[] args) {
        System.out.println("=== Tests CategorieDAO ===");

        CategorieDAO categorieDAO = new CategorieDAO();

        testLesCategories(categorieDAO);
        testCategorie(categorieDAO, "A", 24);
        testCategorie(categorieDAO, "B", 40);
        testCategorie(categorieDAO, "C", 73);
        testCategorieInexistante(categorieDAO);
        testCoutHeure(categorieDAO);

        System.out.println();
        if (nbErreurs == 0) {
            System.out.println("Tous les tests sont OK");
        } else {
            System.out.println(nbErreurs + " test(s) en erreur");
        }
    }

    // la liste doit contenir les 3 categories A, B et C dans l'ordre
    static void testLesCategories(CategorieDAO categorieDAO) {
        ArrayList<Categorie> lesCategories = categorieDAO.getLesCategories();

        if (lesCategories.size() != 3) {
            System.out.println("ERREUR  liste des categories : 3 attendues mais " + lesCategories.size() + " trouvees");
            nbErreurs++;
        } else if (!lesCategories.get(0).getCode().equals("A")
                || !lesCategories.get(1).getCode().equals("B")
                || !lesCategories.get(2).getCode().equals("C")) {
            System.out.println("ERREUR  liste des categories : pas dans l'ordre A, B, C");
            nbErreurs++;
        } else {
            System.out.println("OK      liste des categories : " + lesCategories.size() + " categories (A, B, C)");
        }
    }

    // verifie une categorie et son cout horaire
    static void testCategorie(CategorieDAO categorieDAO, String code, double coutAttendu) {
        Categorie categorie = categorieDAO.getCategorie(code);

        if (categorie == null) {
            System.out.println("ERREUR  categorie " + code + " : introuvable (verifier la connexion a la base)");
            nbErreurs++;
        } else if (!categorie.getCode().equals(code)) {
            System.out.println("ERREUR  categorie " + code + " : mauvais code " + categorie.getCode());
            nbErreurs++;
        } else if (categorie.getCoutHoraire() != coutAttendu) {
            System.out.println("ERREUR  categorie " + code + " : cout attendu " + coutAttendu + " mais obtenu " + categorie.getCoutHoraire());
            nbErreurs++;
        } else {
            System.out.println("OK      categorie " + code + " : cout horaire = " + categorie.getCoutHoraire());
        }
    }

    // une categorie qui n'existe pas doit renvoyer null
    static void testCategorieInexistante(CategorieDAO categorieDAO) {
        Categorie categorie = categorieDAO.getCategorie("Z");

        if (categorie == null) {
            System.out.println("OK      categorie Z : n'existe pas, renvoie null");
        } else {
            System.out.println("ERREUR  categorie Z : aurait du renvoyer null");
            nbErreurs++;
        }
    }

    // getCoutHeure renvoie le cout de la categorie, ou 0 si elle n'existe pas
    static void testCoutHeure(CategorieDAO categorieDAO) {
        double coutB = categorieDAO.getCoutHeure("B");
        double coutZ = categorieDAO.getCoutHeure("Z");

        if (coutB == 40) {
            System.out.println("OK      getCoutHeure(B) = " + coutB);
        } else {
            System.out.println("ERREUR  getCoutHeure(B) : attendu 40 mais obtenu " + coutB);
            nbErreurs++;
        }

        if (coutZ == 0) {
            System.out.println("OK      getCoutHeure(Z) = " + coutZ + " (categorie inexistante)");
        } else {
            System.out.println("ERREUR  getCoutHeure(Z) : attendu 0 mais obtenu " + coutZ);
            nbErreurs++;
        }
    }
}
