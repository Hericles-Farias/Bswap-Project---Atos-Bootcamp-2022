package com.br.atos2022.bswap.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jmx.export.annotation.ManagedResource;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Battery Specs")
public class batteryModel {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer battID;

    @Column(name="Capacity",nullable=false)
    private Integer batteryCapacity;//this will be our username in our application

    @Column(name="Type")
    private String type;

    //@JsonIgnoreProperties("battery")
    //@OneToMany(mappedBy = "battery", cascade = CascadeType.ALL,fetch = FetchType.LAZY)//or add the orphanRemoval if needed//cascade = CascadeType.ALL,
    
    // cascade = {
    //     CascadeType.PERSIST,
    //     CascadeType.MERGE
    // }
    // @ManyToMany(fetch = FetchType.LAZY,
    // cascade = CascadeType.ALL,
    // mappedBy = "batts")
    // @JsonIgnore //@JsonIgnoreProperties(value="batts")//@EqualsAndHashCode.Exclude @ToString.Exclude 
    // private List<userModel> users = new ArrayList<>();


    // public void addUser(userModel user){
    //     this.users.add(user);
    //     //user.addBattery(this);
    // }

    // public void addUser(userModel user){
    //     this.users.add(user);
    //     user.addBattery(this);

    // }

    public String toTypeCap(){
        return this.type+" - "+this.batteryCapacity+" kWh";
    }

}
