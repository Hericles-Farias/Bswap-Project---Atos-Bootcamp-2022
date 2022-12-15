package com.br.atos2022.bswap.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Electric Vehicle")
public class evModel {
    
    @Id
    public String plate;

    public String model;
    public String brand;
    
    @JsonIgnoreProperties("cars")
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    public userModel user;

    // @JsonIgnoreProperties("evs")
    // @ManyToOne(fetch=FetchType.LAZY)
    // @JoinColumn(name="battery_id")
    // public batteryModel battery;

    public evModel(String plate, String model, String brand, Integer batteryCapacity, String type){
        this.plate=plate;
        this.model=model;
        this.brand=brand;
    }

    @Override
    public boolean equals(Object o){
        if (this ==o) return true;
        if (!(o instanceof evModel)) return false;
        return plate !=null && plate.equals(((evModel) o).getPlate());
    }

    @Override
    public int hashCode(){
        return getClass().hashCode();
    }

}
