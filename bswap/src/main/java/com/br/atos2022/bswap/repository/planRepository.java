package com.br.atos2022.bswap.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.br.atos2022.bswap.model.planModel;

@Repository
public interface planRepository extends CrudRepository<planModel,String> {
    
    Optional<planModel>findByName(String name);


    List<planModel>findAll();

    Integer deleteByName(String name);

}
