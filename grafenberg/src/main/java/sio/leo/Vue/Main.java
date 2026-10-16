package sio.leo.Vue;

import sio.leo.Modele.InterventionDAO;

public class Main {
    public static void main(String[] args) {
        InterventionDAO dao = new InterventionDAO();
        double cout = dao.getCoutHeure("A");
        System.out.println("Le cout horaire de la categorie A est : " + cout + " €");
        System.out.println("test");
    }
}
