<%-- 
    Document   : saisieIntervention.
    Created on : 23 nov. 2026, 14:45:43
    Author     : coulon
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="sio.leo.Modele.TypeOperation" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="sio.leo.Modele.Intervenant" %>
<%@ page import="sio.leo.Modele.Intervention" %>

<%
    Intervention interCree = (Intervention) request.getAttribute("interventionCree");
    if (interCree != null) {
%>
    <div style="background-color: #d4edda; color: #155724; padding: 10px; margin-bottom: 15px; border: 1px solid #c3e6cb;">
        <strong>Succès !</strong> L'intervention n° <%= interCree.getNumero() %> du <%= interCree.getDate() %> a bien été créée avec <%= interCree.getIntervenants().size() %> intervenant(s).
    </div>
<%
    }
%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Saisir une intervention</h1>
         <form action="saisieIntervention" method="post">
        <label for="date">Date :</label>
        <input type="date" id="date" name="date" required><br><br>

        <label for="duree">duree :</label>
        <input type="text" id="duree" name="duree" required><br><br>
        
        <label for="numero">Numero :</label>
        <input type="text" id="numero" name="numero" required><br><br>
        
        <label for="nuit">Intervention de nuit :</label>
        <input type="checkbox" id="nuit" name="nuit" ><br><br>
        
        <label for="typeop">Type d'opération :</label>
<select id="typeop" name="typeop" required>
    <option value="">-- Sélectionnez un code --</option>
    <%
        List<TypeOperation> liste = (List<TypeOperation>) request.getAttribute("typesOperation");
        if (liste != null) {
            for (TypeOperation op : liste) {
    %>
                <option value="<%= op.getCode() %>">Opération n° <%= op.getCode() %></option>
    <%
            }
        }
    %>
</select><br><br>

<label>Intervenants à affecter :</label><br>
<%
    ArrayList<Intervenant> intervenants = (ArrayList<Intervenant>) request.getAttribute("lesIntervenants");
    if (intervenants != null) {
        for (Intervenant inter : intervenants) {
%>
            <input type="checkbox" id="inter_<%= inter.getMatricule() %>" name="intervenants" value="<%= inter.getMatricule() %>" >
            <label for="inter_<%= inter.getMatricule() %>"><%= inter.getNom() %> (Matricule : <%= inter.getMatricule() %>)</label><br>
<%
        }
    }
%>
<br>


        <button type="submit">Envoyer</button>
    </form>
    </body>
</html>
