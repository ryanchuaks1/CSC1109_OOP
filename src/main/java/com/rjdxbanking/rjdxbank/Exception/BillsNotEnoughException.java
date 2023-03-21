package com.rjdxbanking.rjdxbank.Exception;

public class BillsNotEnoughException extends Exception{
    public BillsNotEnoughException(String message) {
        super(message);
    }

    public BillsNotEnoughException(String message, Throwable cause) {
        super(message, cause);
    }

    public BillsNotEnoughException(Throwable cause) {
        super(cause);
    }

    public BillsNotEnoughException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BillsNotEnoughException() {
    }
}
