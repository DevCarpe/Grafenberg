package sio.leo.Modele;

import java.util.ArrayList;
import java.util.Date;

public class Intervention {
    private ArrayList<Intervenant> Intervenants = new ArrayList<Intervenant>();
    private int numero;
    private Date date ;
    private double duree;
    private boolean nuit;

    
    public Intervention(int numero, Date date, double duree, boolean nuit) {
        this.numero = numero;
        this.date = date;
        this.duree = duree;
        this.nuit = nuit;
    }


    public ArrayList<Intervenant> getIntervenants() {
        return Intervenants;
    }
    public void setIntervenants(ArrayList<Intervenant> intervenants) {
        Intervenants = intervenants;
    }
    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public double getDuree() {
        return duree;
    }
    public void setDuree(double duree) {
        this.duree = duree;
    }
    public boolean isNuit() {
        return nuit;
    }
    public void setNuit(boolean nuit) {
        this.nuit = nuit;
    }
}