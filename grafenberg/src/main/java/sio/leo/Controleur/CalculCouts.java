package sio.leo.Controleur;

import sio.leo.Modele.Intervenant;
import sio.leo.Modele.Intervention;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalculCouts {

    private static final int[][] JOURS_FERIES_FIXES = {
        {1, Calendar.JANUARY},   // 1er janvier
        {1, Calendar.MAY},       // 1er mai
        {8, Calendar.MAY},       // 8 mai
        {14, Calendar.JULY},     // 14 juillet
        {15, Calendar.AUGUST},   // 15 août
        {1, Calendar.NOVEMBER},  // 1er novembre
        {11, Calendar.NOVEMBER}, // 11 novembre
        {25, Calendar.DECEMBER}  // 25 décembre
    };

    private static boolean estWeekend(Date dateOp) {
        if (dateOp == null) {
            return false;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateOp);
        int jourSemaine = cal.get(Calendar.DAY_OF_WEEK);
        return (jourSemaine == Calendar.SATURDAY || jourSemaine == Calendar.SUNDAY);
    }

    private static boolean estJourFerie(Date dateOp) {
        if (dateOp == null) {
            return false;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateOp);
        int jour = cal.get(Calendar.DAY_OF_MONTH);
        int mois = cal.get(Calendar.MONTH);

        for (int[] ferie : JOURS_FERIES_FIXES) {
            if (ferie[0] == jour && ferie[1] == mois) {
                return true;
            }
        }
        return false;
    }

    public static double calculerCoutMainDoeuvre(Intervention intervention, List<Intervenant> intervenants) {
        double totalMainDoeuvre = 0.0;
        double duree = intervention.getDuree();
        boolean estNuit = "OUI".equalsIgnoreCase(intervention.getNuit());
        Date dateOp = intervention.getDate();

        boolean majorationWEFerie = estWeekend(dateOp) || estJourFerie(dateOp);
        boolean majorationNuitSeule = estNuit && !majorationWEFerie;

        for (Intervenant intervenant : intervenants) {
            double tauxHoraire = intervenant.getCoutHoraire();

            if (majorationWEFerie) {
                tauxHoraire = tauxHoraire * 3.0;
            } else if (majorationNuitSeule) {
                tauxHoraire = tauxHoraire * 2.0;
            }

            totalMainDoeuvre = totalMainDoeuvre + (tauxHoraire * duree);
        }

        return totalMainDoeuvre;
    }

    public static double calculerCoutDeRevient(Intervention intervention, List<Intervenant> intervenants, double chargesOperation, double chargesBloc) {
        double coutMO = calculerCoutMainDoeuvre(intervention, intervenants);
        return coutMO + chargesOperation + chargesBloc;
    }
}
