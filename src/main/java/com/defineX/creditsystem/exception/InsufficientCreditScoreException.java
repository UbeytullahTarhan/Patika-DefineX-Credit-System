package com.defineX.creditsystem.exception;

public class InsufficientCreditScoreException extends RuntimeException{
    public InsufficientCreditScoreException() {
        super("Sorry, your credit request was not approved.You do not have enough credit score in your account. ");
    }
}
