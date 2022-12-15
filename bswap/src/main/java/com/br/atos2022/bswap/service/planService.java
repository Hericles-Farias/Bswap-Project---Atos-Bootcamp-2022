package com.br.atos2022.bswap.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.atos2022.bswap.dto.planDTO;
import com.br.atos2022.bswap.mapper.planMapper;
import com.br.atos2022.bswap.model.planModel;
import com.br.atos2022.bswap.repository.planRepository;

import jakarta.transaction.Transactional;

@Service
public class planService {
    
    @Autowired
    private planRepository planRep;

    @Autowired
    private planMapper planMap;

    @Transactional
    public planModel save(planModel roleModel){
        return planRep.save(roleModel);
    }

    public Optional<planModel> findByName(String name){
        return planRep.findByName(name);
    }


    public List<planDTO>findAllDTO(){
        
        return planMap.toPlanDTOs(planRep.findAll());

    }

    @Transactional
    public Integer deleteByName(String name){
        return planRep.deleteByName(name);
    }

}
