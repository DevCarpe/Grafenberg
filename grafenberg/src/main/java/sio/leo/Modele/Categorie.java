package sio.leo.Modele;

public class Categorie {
    private String code;
    private double coutHoraire;

    public Categorie(String code, double coutHoraire) {
        this.code = code;
        this.coutHoraire = coutHoraire;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getCoutHoraire() {
        return coutHoraire;
    }

    public void setCoutHoraire(double coutHoraire) {
        this.coutHoraire = coutHoraire;
    }
}