
package sio.leo.Controleur;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sio.leo.Modele.ConnexionDAO;
import sio.leo.Modele.Intervenant;
import sio.leo.Modele.IntervenantDAO;
import sio.leo.Modele.InterventionDAO;
import sio.leo.Modele.Intervention;
import sio.leo.Modele.TypeOperation;
import sio.leo.Modele.TypeOperationDAO;

@WebServlet("/saisieIntervention")
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (Connection con = new ConnexionDAO().getConnection()) {
            if (con != null && !con.isClosed()) {
                request.setAttribute("etatConnexion", "Connexion JNDI réussie !");
            } else {
                request.setAttribute("etatConnexion", "Échec de la connexion.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("etatConnexion", "Erreur : " + e.getMessage());
        }

        TypeOperationDAO typeOperationDAO = new TypeOperationDAO();
        List<TypeOperation> listeTypes = typeOperationDAO.getAll();
        request.setAttribute("typesOperation", listeTypes);

        IntervenantDAO intervenantDAO = new IntervenantDAO();
        ArrayList<Intervenant> listeIntervenants = intervenantDAO.getLesIntervenants();
        request.setAttribute("lesIntervenants", listeIntervenants);

        request.getRequestDispatcher("/WEB-INF/saisieIntervention.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 1. Récupération des champs simples
        int numero = Integer.parseInt(request.getParameter("numero"));
        Date date = Date.valueOf(request.getParameter("date"));
        boolean nuit = (request.getParameter("nuit") != null);

        // 2. Récupération du TypeOperation complet via son code
        int codeTypeOp = Integer.parseInt(request.getParameter("typeop"));
        TypeOperationDAO typeOpDAO = new TypeOperationDAO();
        TypeOperation typeOperation = typeOpDAO.getTypeOperation(codeTypeOp);

        // 3. Construction de la liste des Intervenants sélectionnés
        ArrayList<Intervenant> intervenantsChoisis = new ArrayList<>();
        String[] matricules = request.getParameterValues("intervenants");

        if (matricules != null) {
            IntervenantDAO interDAO = new IntervenantDAO();
            for (String mat : matricules) {
                // Note : si PER_MATRICULE est un String dans votre DAO, utilisez interDAO.getIntervenant(mat)
                Intervenant inter = interDAO.getIntervenant(mat);
                if (inter != null) {
                    intervenantsChoisis.add(inter);
                }
            }
        }
        double duree = Double.parseDouble(request.getParameter("duree")); 
        // 4. Appel du constructeur d'Intervention
        Intervention nouvelleIntervention = new Intervention(
                intervenantsChoisis,
                numero,
                date,
                nuit,
                typeOperation,
                duree
        );
        
        //5. Enregistrement en base (décommentez et adaptez selon votre InterventionDAO)
        try {
            InterventionDAO interventionDAO = new InterventionDAO();
            interventionDAO.ajouterIntervention(nouvelleIntervention);
        } catch (java.sql.SQLException e) {
            throw new ServletException("Erreur SQL lors de l'ajout : " + e.getMessage(), e);
        }

        // Stockage de l'objet créé pour confirmation et réaffichage du formulaire
        request.setAttribute("interventionCree", nouvelleIntervention);
        request.getRequestDispatcher("/WEB-INF/confirm.jsp").forward(request, response);

    }
}
