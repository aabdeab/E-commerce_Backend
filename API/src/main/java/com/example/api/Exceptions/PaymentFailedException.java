package com.example.api.Exceptions;

public class PaymentFailedException extends RuntimeException{

    PaymentFailedException(String msg){
        super(msg);
    }
}
