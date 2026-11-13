package sio.leo.Modele;
/**
 * Représente un intervenant au sein de l'application.
 * Chaque intervenant possède un matricule unique, un nom et est rattaché à une catégorie personnel .
 * 
 * @author Outayeb
 * @version 1.0
 */
public class Intervenant {
    private String matricule;
    private String nom;
    private Categorie categorie;

    
    /**
     * Constructeur complet pour créer un intervenant.
     * 
     * @param matricule Le numéro de matricule unique de l'intervenant
     * @param nom Le nom de l'intervenant
     * @param categorie La catégorie personnel (définissant son coût horaire)
     */
    public Intervenant(String matricule, String nom, Categorie categorie) {
        this.matricule = matricule;
        this.nom = nom;
        this.categorie = categorie;
    }

    /**
     * Retourne le matricule de l'intervenant.
     * @return un String représentant le matricule
     */    
    public String getMatricule() {
        return  matricule;
    }

    /**
     * Modifie le matricule de l'intervenant.
     * @param matricule Le nouveau matricule à attribuer
     */
    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public Categorie getCategorie() {
        return categorie;
    }
    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
    
    
}