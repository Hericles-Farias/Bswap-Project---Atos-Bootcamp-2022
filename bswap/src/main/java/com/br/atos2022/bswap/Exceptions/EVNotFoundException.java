package com.br.atos2022.bswap.Exceptions;

public class EVNotFoundException extends RuntimeException {
    
    public EVNotFoundException(String plate){
        super("Cold not find EV: "+plate);
    }
}
