package com.eshoppingbackend.EShopping.Backend.Exception;

public class WrongCredentialsException extends RuntimeException {
    public WrongCredentialsException(String mssg){
        super(mssg);
    }
}
