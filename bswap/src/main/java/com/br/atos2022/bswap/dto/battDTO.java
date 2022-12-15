package com.br.atos2022.bswap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class battDTO {
    
    private Integer battID;

    private Integer batteryCapacity;

    private String type;

    public String toTypeCap(){
        return this.type+" - "+this.batteryCapacity+" kWh";
    }

}
