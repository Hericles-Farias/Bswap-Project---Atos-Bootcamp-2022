package com.br.atos2022.bswap.Exceptions;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(Integer id){
        super("Cold not find role with id: "+id);
    }

}