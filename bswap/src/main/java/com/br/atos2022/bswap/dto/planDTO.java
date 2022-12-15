package com.br.atos2022.bswap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class planDTO {
    
    //id
    private String name;

    private Integer energyPrice;
    //THE HIGHER THE PLAN RANK THE LOWER THE FIXED COST
    private Integer fixedCost;
    //monthly/semesterly/annualy
    private String renewPeriod;
    

}
