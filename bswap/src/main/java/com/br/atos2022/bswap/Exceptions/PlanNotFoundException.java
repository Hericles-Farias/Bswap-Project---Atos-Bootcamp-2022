package com.br.atos2022.bswap.Exceptions;

public class PlanNotFoundException extends RuntimeException {

    public PlanNotFoundException(String name){
        super("Cold not find plan: "+name);
    }

}
