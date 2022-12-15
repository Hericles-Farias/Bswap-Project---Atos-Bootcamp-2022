package com.br.atos2022.bswap.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.br.atos2022.bswap.model.roleModel;

@Repository
public interface roleRepository extends CrudRepository<roleModel, Integer>{
    
    Optional<roleModel> findByRoleID(Integer id);
    

}
