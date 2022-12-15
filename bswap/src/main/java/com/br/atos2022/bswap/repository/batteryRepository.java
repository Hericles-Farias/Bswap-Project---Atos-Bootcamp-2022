package com.br.atos2022.bswap.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.br.atos2022.bswap.model.batteryModel;

@Repository
public interface batteryRepository extends CrudRepository<batteryModel,Integer>{

    Optional<batteryModel>findByBattID(Integer battID);
    List<batteryModel>findAll();
    
    List<batteryModel>findByTypeAndBatteryCapacity(String type, Integer batteryCapacity);
    Integer deleteByBattID(Integer battID);
    
}
