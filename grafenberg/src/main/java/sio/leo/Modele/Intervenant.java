package sio.leo.Modele;

public class Intervenant {
    private int matricule;
    private String nom;
    private Categorie categorie;

    

    public Intervenant(int matricule, String nom, Categorie categorie) {
        this.matricule = matricule;
        this.nom = nom;
        this.categorie = categorie;
    }

    
    public int getMatricule() {
        return matricule;
    }
    public void setMatricule(int matricule) {
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