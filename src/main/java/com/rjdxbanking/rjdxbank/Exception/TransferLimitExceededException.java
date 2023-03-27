package com.rjdxbanking.rjdxbank.Exception;

public class TransferLimitExceededException extends Exception {

    double limit;
    public TransferLimitExceededException(double limit) {
        this.limit = limit;
    }

    public double getLimit(){
        return this.limit;
    }

}
