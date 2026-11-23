package sio.leo.Modele;

import java.util.ArrayList;
import java.util.Date;

public class Intervention {
    private ArrayList<Intervenant> Intervenants = new ArrayList<Intervenant>();
    private int numero;
    private Date date ;
    private double duree;
    private boolean nuit;
    private TypeOperation typeOperation;

    
    public Intervention(ArrayList<Intervenant> intervenants, int numero, Date date, boolean nuit,
            TypeOperation typeOperation) {
        Intervenants = intervenants;
        this.numero = numero;
        this.date = date;
        this.nuit = nuit;
        this.typeOperation = typeOperation;
    }


    public TypeOperation getTypeOperation() {
        return typeOperation;
    }


    public void setTypeOperation(TypeOperation typeOperation) {
        this.typeOperation = typeOperation;
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