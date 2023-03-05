package Exceptions;

public class InsufficientFundsException extends Exception{
    public InsufficientFundsException(String errormessage) {
        super(errormessage);
    }
}
