package com.eshoppingbackend.EShopping.Backend.Exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String msg){
        super(msg);
    }
}
