package com.br.atos2022.bswap.Exceptions;

public class BatteryNotFoundException extends RuntimeException{
    public BatteryNotFoundException(Integer battID){
        super("Cold not find battery: "+battID);
    }

}
