package sio.leo.Controleur; // Adaptez au nom de votre package

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import sio.leo.Modele.ConnexionDAO;

// Si vous êtes sous Jakarta EE (WildFly 27+), remplacez "javax.servlet" par "jakarta.servlet aa"
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/test-connexion")
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter();
             Connection con = new ConnexionDAO().getConnection()) {
            
            if (con != null && !con.isClosed()) {
                out.println("<h1>Connexion JNDI réussie !</h1>");
            } else {
                out.println("<h1>Échec de la connexion (con est null).</h1>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h1>Erreur : " + e.getMessage() + "</h1>");
        }
    }
}
