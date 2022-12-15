package com.br.atos2022.bswap.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class schedulingDTO {
    
    public Integer schedID;

    public String Status;

    public Timestamp date;

    private String battSpec;

    private String user;

    private Integer userID;

    public String getDateFormated(){

        Timestamp mydate = this.date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String mydatestr=dateFormat.format(mydate);
        return mydatestr;
    }

}
