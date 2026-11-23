package sio.leo.Modele;

// Test de la classe BlocDAO
// Il faut etre connecte a la base de donnees (celle du lycee)
// Les valeurs attendues viennent du script d'insertion (annexe 2) :
// bloc 1 = 1524, bloc 2 = 915, bloc 3 = 1700
public class BlocDAOTest {

    static int nbErreurs = 0;

    public static void main(String[] args) {
        System.out.println("=== Tests BlocDAO ===");

        BlocDAO blocDAO = new BlocDAO();

        testBloc(blocDAO, 1, 1524);
        testBloc(blocDAO, 2, 915);
        testBloc(blocDAO, 3, 1700);
        testBlocInexistant(blocDAO);

        System.out.println();
        if (nbErreurs == 0) {
            System.out.println("Tous les tests sont OK");
        } else {
            System.out.println(nbErreurs + " test(s) en erreur");
        }
    }

    // verifie qu'un bloc existe et que ses charges sont bonnes
    static void testBloc(BlocDAO blocDAO, int numero, double chargesAttendues) {
        Bloc bloc = blocDAO.getBloc(numero);

        if (bloc == null) {
            System.out.println("ERREUR  bloc " + numero + " : bloc introuvable (verifier la connexion a la base)");
            nbErreurs++;
        } else if (bloc.getNum() != numero) {
            System.out.println("ERREUR  bloc " + numero + " : mauvais numero " + bloc.getNum());
            nbErreurs++;
        } else if (bloc.getCharges() != chargesAttendues) {
            System.out.println("ERREUR  bloc " + numero + " : charges attendues " + chargesAttendues + " mais obtenu " + bloc.getCharges());
            nbErreurs++;
        } else {
            System.out.println("OK      bloc " + numero + " : charges = " + bloc.getCharges());
        }
    }

    // un bloc qui n'existe pas doit renvoyer null
    static void testBlocInexistant(BlocDAO blocDAO) {
        Bloc bloc = blocDAO.getBloc(99);

        if (bloc == null) {
            System.out.println("OK      bloc 99 : n'existe pas, renvoie null");
        } else {
            System.out.println("ERREUR  bloc 99 : aurait du renvoyer null");
            nbErreurs++;
        }
    }
}
