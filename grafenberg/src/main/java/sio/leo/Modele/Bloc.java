package sio.leo.Modele;

public class Bloc {
    private int num;
    private double charges;
    

    public Bloc() {
        this(0,0);
    }

    public Bloc(int num, double charges) {
        this.num = num;
        this.charges = charges;
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public double getCharges() {
        return charges;
    }
    public void setCharges(double charges) {
        this.charges = charges;
    }

}