package com.br.atos2022.bswap.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.br.atos2022.bswap.model.userModel;

@Repository
public interface userRepository extends CrudRepository<userModel, Integer>{
    

    Optional<userModel> findByUsername(String username);

    Optional<userModel>findById(Integer id);

    List<userModel> findAll();

}
