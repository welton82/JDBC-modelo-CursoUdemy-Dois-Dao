package exception;

public class Excessao extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public Excessao(String msg){
        super(msg);
    }
}
