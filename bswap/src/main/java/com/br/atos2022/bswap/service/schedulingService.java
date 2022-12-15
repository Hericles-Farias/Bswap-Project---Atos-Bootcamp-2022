package com.br.atos2022.bswap.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.atos2022.bswap.dto.schedulingDTO;
import com.br.atos2022.bswap.mapper.schedulingMapper;
import com.br.atos2022.bswap.model.schedulingModel;
import com.br.atos2022.bswap.repository.schedulingRepository;

import jakarta.transaction.Transactional;

@Service
public class schedulingService {
    
    @Autowired
    private schedulingRepository schedRep;

        
    @Autowired
    private schedulingMapper schedMap;

    //private final schedulingMapper schedMap=schedulingMapper.INSTANCE;


    public List<schedulingModel> findAllBetweenRange(Timestamp startDate, Timestamp endDate){

        return schedRep.findByDateGreaterThanEqualAndDateLessThanEqual(startDate, endDate);
    }
    public List<schedulingModel> findAllBetweenRangeStr(String startDate, String endDate){

        Timestamp startDatex;
        Timestamp endDatex;
        //List<schedulingDTO>schedsDTO=new ArrayList<schedulingDTO>();
        List<schedulingModel> scheds = new ArrayList<schedulingModel>();
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date parsedStart = dateFormat.parse(startDate);
            Date parsedEnd = dateFormat.parse(endDate);
            startDatex=new Timestamp(parsedStart.getTime());
            endDatex=new Timestamp(parsedEnd.getTime());
            scheds=schedRep.findByDateGreaterThanEqualAndDateLessThanEqual(startDatex, endDatex);      

        }catch(ParseException e){
            //log here!
            e.printStackTrace();
        }
        return scheds;
    }
    

    public List<schedulingDTO> findAllBetweenRangeDTO(String startDate, String endDate){

        Timestamp startDatex;
        Timestamp endDatex;
        List<schedulingDTO>schedsDTO=new ArrayList<schedulingDTO>();
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date parsedStart = dateFormat.parse(startDate);
            Date parsedEnd = dateFormat.parse(endDate);
            startDatex=new Timestamp(parsedStart.getTime());
            endDatex=new Timestamp(parsedEnd.getTime());
            List<schedulingModel> scheds=schedRep.findByDateGreaterThanEqualAndDateLessThanEqual(startDatex, endDatex);
            schedsDTO=schedMap.toSchedulingDTOs(scheds);        

        }catch(ParseException e){
            //log here!
            e.printStackTrace();
        }
        return schedsDTO;
        
    }

    @Transactional
    public schedulingModel save(schedulingModel sched){
        return schedRep.save(sched);
    }

    public List<schedulingDTO>findAllDTO(){
        List<schedulingModel> scheds=schedRep.findAll();
        return schedMap.toSchedulingDTOs(scheds);
    }
    
    public Optional<schedulingModel>findBySchedID(Integer schedID){
        return schedRep.findBySchedID(schedID);
    }

    
}
