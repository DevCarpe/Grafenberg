package sio.leo.Modele;

public class Intervenant {
    private String matricule;
    private String nom;
    private Categorie categorie;

    

    public Intervenant(String matricule, String nom, Categorie categorie) {
        this.matricule = matricule;
        this.nom = nom;
        this.categorie = categorie;
    }

    
    public String getMatricule() {
        return  matricule;
    }
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