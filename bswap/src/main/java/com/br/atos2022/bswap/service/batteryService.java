package com.br.atos2022.bswap.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.atos2022.bswap.dto.battDTO;
import com.br.atos2022.bswap.mapper.battMapper;
import com.br.atos2022.bswap.model.batteryModel;
import com.br.atos2022.bswap.repository.batteryRepository;

import jakarta.transaction.Transactional;

@Service
public class batteryService {
    
    @Autowired
    private batteryRepository battRep;

    @Autowired
    private battMapper battMap;

    @Transactional
    public batteryModel save(batteryModel battModel){
        return battRep.save(battModel);
    }

    public List<batteryModel> findAll(){
        return battRep.findAll();
    }

    public List<String> getAllBattSepcs(){
        List<batteryModel>batts= battRep.findAll();
        List<String>specs = new ArrayList<>();
        
        for(batteryModel batt:batts){
            specs.add(batt.toTypeCap());
        }
        return specs;
    }

    public Optional<batteryModel>findByBattID(Integer battID){
        return battRep.findByBattID(battID);
    }

    public List<batteryModel>findByTypeAndBatteryCapacity(String type, Integer batteryCapacity){
        return battRep.findByTypeAndBatteryCapacity(type, batteryCapacity);
    }

    public List<battDTO>findAllDTO(){
        List<batteryModel> batts=battRep.findAll();
        return battMap.tobattDTOs(batts);
    }

    @Transactional
    public Integer deleteByBattID(Integer battID) {
        //returns the number of batteries delete (in this case 1 if success and 0 if not!)
        return battRep.deleteByBattID(battID);

    }

    @Transactional
    public batteryModel saveDTO(battDTO batt){

        batteryModel b = battMap.toBatteryModel(batt);

        return battRep.save(b);
    }

    
}
