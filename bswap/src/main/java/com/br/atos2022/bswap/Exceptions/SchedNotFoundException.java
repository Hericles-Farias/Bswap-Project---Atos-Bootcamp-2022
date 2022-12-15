package com.br.atos2022.bswap.Exceptions;


public class SchedNotFoundException extends RuntimeException {
    
    public SchedNotFoundException(Integer schedID){
        super("Cold not find scheduling with id: "+schedID);
    }

}
