package com.br.atos2022.bswap.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class dateHandler {

    public List<String>addMinutes(int dt, int T, Date date){
        
        List<String> dates = new ArrayList<>();
        long curTimeInMs = date.getTime();
        for(int i=0;i<T;i++){
            Date dateAfter = new Date(curTimeInMs + (i*dt*60000));
            String dateAfterString = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(dateAfter);
            dates.add(dateAfterString);
            //curTimeInMs = dateAfter.getTime();
        }
        
        return dates;
    }
    
    public List<String> allDatesWithinRange() {
        
        ZonedDateTime z = ZonedDateTime.of(LocalDateTime.now(),ZoneOffset.UTC);
        ZonedDateTime z1 = z.withZoneSameInstant(ZoneId.of("America/Sao_Paulo"));
        ZonedDateTime z2= z1.plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String z2str=z2.format(formatter);
        String[] z3str=z2str.split(" ",2);
        String z4str = z3str[0]+" 07:00:00";
        
        Date start;
        try{
            start = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(z4str); 
        }catch (ParseException e){
            throw new IllegalArgumentException(e);
        }
        System.out.println(start);
        List<String>dates=addMinutes(10,90,start);
        return dates;
    }

    
}
