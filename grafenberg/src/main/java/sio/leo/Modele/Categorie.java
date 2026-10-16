package sio.leo.Modele;

public class Categorie {
    private int code;
    private double coutHoraire;
    
    public Categorie(int code, double coutHoraire) {
        this.code = code;
        this.coutHoraire = coutHoraire;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public double getCoutHoraire() {
        return coutHoraire;
    }
    public void setCoutHoraire(double coutHoraire) {
        this.coutHoraire = coutHoraire;
    } 
    
}