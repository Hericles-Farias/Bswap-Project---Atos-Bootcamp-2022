package com.br.atos2022.bswap.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Scheduling")
public class schedulingModel {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    public Integer schedID;

    @JsonIgnoreProperties("schedules")
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    public userModel user;
    
    @Column(columnDefinition="TIMESTAMP (0)")
    public Timestamp date;

    private String battSpec;

    @Column(name="Status")
    public String Status;//CONFIRMED, CANCELLED, FINISHED!



    public String getDateFormated(){

        Timestamp mydate = this.date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String mydatestr=dateFormat.format(mydate);
        return mydatestr;
    }

}
