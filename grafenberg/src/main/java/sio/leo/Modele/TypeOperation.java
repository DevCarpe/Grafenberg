package sio.leo.Modele;

public class TypeOperation {
    private int code;
    private int charges;
    private Bloc bloc;
    
    public TypeOperation(){

    }
    public TypeOperation(int p_code, int p_charges, Bloc p_bloc){
        this.code = p_code;
        this.charges = p_charges;
        this.bloc = p_bloc;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public int getCharges() {
        return charges;
    }
    public void setCharges(int charges) {
        this.charges = charges;
    }
    public Bloc getBloc() {
        return bloc;
    }
    public void setBloc(Bloc bloc) {
        this.bloc = bloc;
    }
    
}