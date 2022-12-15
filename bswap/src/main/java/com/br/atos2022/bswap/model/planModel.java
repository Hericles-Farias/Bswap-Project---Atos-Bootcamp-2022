package com.br.atos2022.bswap.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Plan")
public class planModel {
    //BRONZE/SILVER/GOLD
    @Id
    public String name;

    @JsonIgnoreProperties("currentPlan")
    @OneToMany(mappedBy = "currentPlan", cascade=CascadeType.ALL, fetch = FetchType.LAZY)//cascade = CascadeType.ALL,orphanRemoval=true,)
    public List<userModel> users = new ArrayList<>();

    //THE HIGHER THE PLAN RANK THE LOWER THE ENERGY PRICE
    public Integer energyPrice;
    //THE HIGHER THE PLAN RANK THE LOWER THE FIXED COST
    public Integer fixedCost;
    //monthly/semesterly/annualy
    public String renewPeriod;
    
}
