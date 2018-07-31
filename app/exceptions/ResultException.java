package exceptions;

public class ResultException extends RuntimeException {
    
    public Object[] codemessage;
    
    
    public ResultException(Object[] codemessage) {
        this.codemessage = codemessage;
    }
    
    public ResultException(int code, String message) {
        this(new Object[]{code, message});
    }
    
}
