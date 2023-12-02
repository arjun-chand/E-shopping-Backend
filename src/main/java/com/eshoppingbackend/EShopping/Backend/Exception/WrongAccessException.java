package com.eshoppingbackend.EShopping.Backend.Exception;

public class WrongAccessException extends  RuntimeException{
    public WrongAccessException(String msg){
        super(msg);
    }
}
