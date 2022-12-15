package com.br.atos2022.bswap.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.atos2022.bswap.model.evModel;
import com.br.atos2022.bswap.repository.evRepository;

import jakarta.transaction.Transactional;

@Service
public class evService {
    

    @Autowired
    private evRepository evRep;

    @Transactional
    public evModel save(evModel evModel){
        return evRep.save(evModel);
    }

    public Optional<evModel> findByPlate(String plate){
        return evRep.findByPlate(plate);
    }

}
