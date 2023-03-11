package Exceptions;

public class AccountNotSufficientException extends  Exception{
    public AccountNotSufficientException(String message) {
        super(message);
    }
}
