package com.example.api.Exceptions;

public class OutOfStockException extends RuntimeException {

    OutOfStockException(String msg){
        super(msg);
    }
}
