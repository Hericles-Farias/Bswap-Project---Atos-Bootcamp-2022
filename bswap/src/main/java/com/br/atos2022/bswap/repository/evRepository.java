package com.br.atos2022.bswap.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.br.atos2022.bswap.model.evModel;

@Repository
public interface evRepository extends CrudRepository<evModel,String> {
    
    Optional<evModel>findByPlate(String plate);

}
