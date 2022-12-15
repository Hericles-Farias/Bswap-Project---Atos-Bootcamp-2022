package com.br.atos2022.bswap.Exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Integer id){
        super("Cold not find user with id: "+id);
    }

}